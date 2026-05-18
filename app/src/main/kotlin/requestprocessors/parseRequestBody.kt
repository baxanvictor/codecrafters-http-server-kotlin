package requestprocessors

import dto.HttpHeader
import java.io.BufferedReader
import kotlin.text.toIntOrNull

fun BufferedReader.parseRequestBody(
    requestHeaders: Map<String, String>
): String? {
    val bodyLength = requestHeaders[HttpHeader.CONTENT_LENGTH]?.toIntOrNull() ?: return null

    val chars = CharArray(bodyLength)
    read(chars)

    return String(chars)
}