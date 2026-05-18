package utils.responses

import dto.HttpStatusCode
import dto.HttpVersion
import mappers.formatted
import utils.Constants

fun buildResponseStatusLine(
    httpVersion: HttpVersion,
    statusCode: HttpStatusCode,
    message: String,
): String {
    return buildString {
        append(httpVersion.formatted())
        append(' ')
        append(statusCode.code)
        append(' ')
        append(message)
        append(Constants.CRLF)
    }
}