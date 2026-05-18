package dto

data class RequestStartLine(
    val requestTarget: RequestTarget,
    val httpVersion: HttpVersion
)
