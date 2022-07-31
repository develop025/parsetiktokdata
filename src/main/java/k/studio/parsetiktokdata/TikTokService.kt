package k.studio.parsetiktokdata

import k.studio.parsetiktokdata.vo.TikTokProfile
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

internal interface TikTokService {

    @GET("/@{username}")
    fun getTikTokProfile(@Path("username") username: String): Response<TikTokProfile>
}