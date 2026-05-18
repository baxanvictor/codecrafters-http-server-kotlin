package utils.responses

import dto.HttpHeader
import dto.HttpStatusCode
import dto.HttpVersion
import utils.Constants
import java.io.BufferedWriter
import java.io.OutputStream

fun BufferedWriter.writeMessage(
    httpVersion: HttpVersion,
    statusCode: HttpStatusCode,
    message: String,
    requestHeaders: Map<String, String> = emptyMap(),
    responseHeaders: Map<String, String> = emptyMap(),
    body: String? = null
) {
    write(buildResponseStatusLine(httpVersion, statusCode, message))
    writeHeaders(buildResponseHeaders(requestHeaders, responseHeaders))
    write(Constants.CRLF)
    body?.let { write(it) }
    flush()
}

fun BufferedWriter.writeBytes(
    outputStream: OutputStream,
    httpVersion: HttpVersion,
    statusCode: HttpStatusCode,
    message: String,
    requestHeaders: Map<String, String> = emptyMap(),
    responseHeaders: Map<String, String> = emptyMap(),
    body: ByteArray
) {
    write(buildResponseStatusLine(httpVersion, statusCode, message))
    writeHeaders(buildResponseHeaders(requestHeaders, responseHeaders))
    write(Constants.CRLF)
    flush()

    outputStream.write(body)
    outputStream.flush()
}

private fun buildResponseHeaders(
    requestHeaders: Map<String, String>,
    responseHeaders: Map<String, String>
): Map<String, String> {
    val closeConnectionHeader = requestHeaders[HttpHeader.CONNECTION]?.takeIf { it == Constants.CLOSE }

    return buildMap {
        putAll(responseHeaders)
        closeConnectionHeader?.let {
            putIfAbsent(HttpHeader.CONNECTION, it)
        }
    }
}