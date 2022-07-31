package k.studio.parsetiktokdata.vo


/**
 * User profile
 * @param username - tiktok username
 * @param avatarThumb - thumb url
 * @param followerCount - count of followers
 * @param followingCount - count of following
 * @param heartCount - count likes in account
 * @param videoCount - count of video
 * @param videos - videos list
 */
data class TikTokProfile(
    val username: String,
    val avatarThumb: String,
    val followerCount: Int,
    val followingCount: Int,
    val heartCount: Int,
    val videoCount: Int
    //,
    //val videos: List<TikTokProfileVideo>
)
