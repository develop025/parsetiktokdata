package k.studio.parsetiktokdata

import android.util.Log
import k.studio.parsetiktokdata.adapters.TikTokProfileAdapter
import k.studio.parsetiktokdata.vo.TikTokProfile
import okhttp3.OkHttpClient
import retrofit2.Response
import retrofit2.Retrofit

class TikTokParser {

    companion object {
        private const val baseUrl = "https://www.tiktok.com/"
        private const val profileUrl = "$baseUrl/@"
    }

    private val service: TikTokService by lazy {
        val retrofit = Retrofit.Builder()
            .client(OkHttpClient.Builder().build())
            .addConverterFactory(TikTokProfileAdapter.FACTORY)
            .baseUrl(baseUrl)
            .build()

        retrofit.create(TikTokService::class.java)
    }

    suspend fun getProfile(username: String): Response<TikTokProfile> {
        return service.getTikTokProfile(username)
    }
}

fun String.logD() {
    Log.d("ParseTikTokData", this)
}