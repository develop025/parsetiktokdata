package k.studio.parsetiktokdata.vo

/**
 * Current user videos
 * @param videoId - id from tiktok video url
 * @param videoIntentUrl -
 * @param videoDesc - video description
 * @param dynamicCover -
 * @param playAddr - video url for play video
 * @param downloadAddr - video url for download video
 */
data class TikTokProfileVideo(
    val videoId: String,
    val videoIntentUrl: String,
    val videoDesc: String,
    val dynamicCover: String,
    val playAddr: String,
    val downloadAddr: String
)
