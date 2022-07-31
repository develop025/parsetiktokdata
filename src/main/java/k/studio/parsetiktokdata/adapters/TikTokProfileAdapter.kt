package k.studio.parsetiktokdata.adapters

import android.text.Html
import android.util.Log
import k.studio.parsetiktokdata.logD
import k.studio.parsetiktokdata.vo.TikTokProfile
import k.studio.parsetiktokdata.vo.TikTokProfileVideo
import okhttp3.ResponseBody
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import retrofit2.Converter
import retrofit2.Retrofit
import java.io.IOException
import java.lang.reflect.Type

/**
 * Adapter parse web page and fill data TikTokProfilePage
 */

class TikTokProfileAdapter : Converter<ResponseBody, TikTokProfile> {

    @Throws(IOException::class)
    override fun convert(responseBody: ResponseBody): TikTokProfile {
//        val document: Document = Jsoup.parse(responseBody.string())
//        val allContent: String = document.html()

        val videos = mutableListOf<TikTokProfileVideo>()
//        val profile = TikTokProfile()
        try {
            val doc: Document = Jsoup.parse(responseBody.string())
            val app = doc.getElementById("app")
            val nodes = app?.childNodes()
            "nodes:${nodes?.size}".logD()
            try {
                val htmlApp = app?.html()
                "htmlApp:$htmlApp".logD()
            } catch (e: Exception) {
                "htmlApp error:${e.message}, ${e.javaClass.name}, ${e.cause?.message}".logD()
            }
            Html.fromHtml(responseBody.string()).forEach {span->
                "span:$span".logD()
            }

           /* doc.getElementById("user-page")?.html()?.let { html ->
                "html:$html".logD()
                JSONObject(html).let { json ->
                    "json:$json".logD()
                }
            } ?: run {
                "user-page not exist".logD()
            }*/
            /* val doc: Document = Jsoup.parse(responseBody.string())
             val html = doc.getElementById("user-page")?.html()
             html?.let { html -> //doc.getElementById("__NEXT_DATA__")
                 JSONObject(html).let { json ->
                     "json".logD()
                     (json.get("user-title") as JSONObject).let { username ->
                         "username JSONObject".logD()
                     }

                     (json.get("user-title") as String).let { username ->
                         "username String".logD()
                     }
                     *//* user-title username
                     user-subtitle
                     following-count
                     followers-count
                     likes-count*//*
                    (json.get("user-post-item-list") as JSONObject).let { items ->

                    }*/
            /*  (props.get("pageProps") as JSONObject).let { pageProps ->
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
                          "USer uniqueId:$uniqueId, avatarThumb:$avatarThumb".logD()
                      }
                      (userInfo.get("stats") as JSONObject).let { stats ->
                          followerCount = stats.get("followerCount") as Int
                          followingCount = stats.get("followingCount") as Int
                          heartCount = stats.get("heartCount") as Int
                          videoCount = stats.get("videoCount") as Int
                          "Stats followerCount:$followerCount, followingCount:$followingCount, heartCount:$heartCount, videoCount:$videoCount".logD()
                      }
                  }

                  val tikTokProfile = TikTokProfile(
                      uniqueId,
                      avatarThumb,
                      followerCount,
                      followingCount,
                      heartCount,
                      videoCount
                  )

                  tikTokProfileDao.insert(tikTokProfile)

                  val tikTokProfileVideos = mutableListOf<TikTokProfileVideo>()

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
          }*/
            /*  }*/
            /*}*/
        } catch (e: Exception) {
            "video parse error:${e.message}, class:${e.javaClass.name}, cause:${e.cause?.message}".logD()
        }

        return TikTokProfile("", "", 0, 0, 0, 0)//, emptyList()
    }

    companion object {
        val FACTORY: Converter.Factory = object : Converter.Factory() {
            override fun responseBodyConverter(
                type: Type,
                annotations: Array<out Annotation>,
                retrofit: Retrofit
            ): Converter<ResponseBody, *>? {
                return if (type === TikTokProfile::class.java) TikTokProfileAdapter() else null
            }
        }
    }
}

private fun String.logD() {
    Log.d("ParseTikTokData", this)
}
