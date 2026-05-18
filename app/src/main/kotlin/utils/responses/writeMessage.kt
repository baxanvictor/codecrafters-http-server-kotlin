package utils.responses

import dto.HttpStatusCode
import dto.HttpVersion
import mappers.formatted
import utils.Constants
import java.io.BufferedWriter

fun BufferedWriter.writeMessage(
    httpVersion: HttpVersion,
    statusCode: HttpStatusCode,
    message: String,
    headers: Map<String, String>,
    body: String?
) {
    val statusLine = buildString {
        append(httpVersion.formatted())
        append(' ')
        append(statusCode.code)
        append(' ')
        append(message)
        append(Constants.CRLF)
    }

    write(statusLine)
    writeHeaders(headers)
    write(Constants.CRLF)
    body?.let { write(it) }
    flush()
}