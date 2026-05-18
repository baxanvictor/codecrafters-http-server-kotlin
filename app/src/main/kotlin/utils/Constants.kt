package utils

import dto.HttpVersion

object Constants {
    const val CRLF = "\r\n"
    const val HOST = "Host"
    const val USER_AGENT = "User-Agent"
    const val ACCEPT = "Accept"

    val HttpVersion1_1 = HttpVersion(1, 1)
}