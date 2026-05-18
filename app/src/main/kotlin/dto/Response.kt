package dto

data class Response(
    val httpVersion: HttpVersion,
    val statusCode: HttpStatusCode,
    val message: String
)