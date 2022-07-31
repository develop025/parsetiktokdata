package k.studio.parsetiktokdata

import android.content.Context
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import k.studio.parsetiktokdata.vo.TikTokProfile
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class NetworkService(val context: Context) {

    companion object {
        private const val TAG = "ParseTikTokData"
    }

    private val requestQueue: RequestQueue?

    init {
        requestQueue = Volley.newRequestQueue(context)
    }

    fun addQueue(stringRequest: StringRequest, tag: String) {
        stringRequest.tag = tag
        requestQueue?.add(stringRequest)
    }

    fun cancel(tag: String) {
        requestQueue?.cancelAll(tag)
    }

    suspend fun getProfile(username: String): Result<TikTokProfile> {
        return suspendCoroutine { continuation ->
            val url = "https://www.tiktok.com/@$username"

            val stringRequest = StringRequest(
                Request.Method.GET, url,
                { response ->
                    "response"
                    if (response.isNullOrEmpty())
                        continuation.resume(Result.failure(ParseUserDataException()))
                    else {
                        parsePageData(response, username)
                        continuation.resume(Result.success(TikTokProfile("", "", 0, 0, 0, 0)))
                    }
                },
                {
                    "That didn't work!".logD()
                    continuation.resume(Result.failure(UsernameNotExistException()))
                })

            this.addQueue(stringRequest, TAG)
        }
    }

    class UsernameNotExistException : Exception()
    class ParseUserDataException : Exception()

    private fun parsePageData(response: String, username: String): Result<Boolean> {
        try {
            val doc: Document = Jsoup.parse(response)
            val nodes = doc.getElementById("app")?.childNodes()//html()
            "nodes:${nodes?.size}".logD()
            /*JSONObject(html).let { json ->
                (json.get("props") as JSONObject).let { props ->
                    (props.get("pageProps") as JSONObject).let { pageProps ->
                        val uniqueId: String
                        val avatarThumb: String
                        val followerCount: Int
                        val followingCount: Int
                        val heartCount: Int
                        val videoCount: Int

                        (pageProps.get("userInfo") as JSONObject).let { userInfo ->
                            (userInfo.get("user") as JSONObject).let { user ->
                                uniqueId = user.get("uniqueId") as String
                                avatarThumb = user.get("avatarThumb") as String
                                "USer uniqueId:$uniqueId, avatarThumb:$avatarThumb".log()
                            }
                            (userInfo.get("stats") as JSONObject).let { stats ->
                                followerCount = stats.get("followerCount") as Int
                                followingCount = stats.get("followingCount") as Int
                                heartCount = stats.get("heartCount") as Int
                                videoCount = stats.get("videoCount") as Int
                                "Stats followerCount:$followerCount, followingCount:$followingCount, heartCount:$heartCount, videoCount:$videoCount".log()
                            }
                        }

                        val account = Account(
                            uniqueId,
                            avatarThumb,
                            followerCount,
                            followingCount,
                            heartCount,
                            videoCount
                        )

                        accountDao.insert(account)

                        val videos = mutableListOf<Video>()

                        (pageProps.get("videoData") as JSONArray).let { videoData ->
                            repeat(videoData.length()) { n ->
                                (videoData[n] as JSONObject).let { video ->
                                    val videoId: String = video.get("id") as String
                                    val videoIntentUrl =
                                        "https://www.tiktok.com/@$username/video/$videoId"
                                    val dynamicCover: String
                                    val playAddr: String
                                    val downloadAddr: String
                                    val videoDesc: String = video.get("desc") as String
                                    "Video videoId:$videoId, videoDesc:$videoDesc".log()
                                    (video.get("video") as JSONObject).let { videoData ->
                                        dynamicCover = videoData.get("dynamicCover") as String
                                        playAddr = videoData.get("playAddr") as String
                                        downloadAddr = videoData.get("downloadAddr") as String
                                        "Video data. dynamicCover:$dynamicCover, playAddr:$playAddr, downloadAddr:$downloadAddr, videoIntentUrl:$videoIntentUrl".log()
                                    }
                                    val v = Video(
                                        videoId,
                                        videoIntentUrl,
                                        videoDesc,
                                        dynamicCover,
                                        playAddr,
                                        downloadAddr
                                    )
                                    videos.add(v)
                                }
                            }
                        }

                        videos.forEach { video ->
                            video.accountId = account.uniqueId
                        }

                        accountDao.insertVideos(videos)
                    }
                }
            }*/
            return Result.success(true)
        } catch (e: Exception) {
            "Parse error: ${e.message}".logD()
            return Result.failure(ParseUserDataException())
        }
    }
}