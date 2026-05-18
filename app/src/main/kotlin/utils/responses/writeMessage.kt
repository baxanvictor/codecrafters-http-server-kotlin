package utils.responses

import dto.HttpStatusCode
import dto.HttpVersion
import utils.Constants
import java.io.BufferedWriter
import java.io.OutputStream

fun BufferedWriter.writeMessage(
    httpVersion: HttpVersion,
    statusCode: HttpStatusCode,
    message: String,
    headers: Map<String, String> = emptyMap(),
    body: String? = null
) {
    write(buildResponseStatusLine(httpVersion, statusCode, message))
    writeHeaders(headers)
    write(Constants.CRLF)
    body?.let { write(it) }
    flush()
}

fun BufferedWriter.writeBytes(
    outputStream: OutputStream,
    httpVersion: HttpVersion,
    statusCode: HttpStatusCode,
    message: String,
    headers: Map<String, String> = emptyMap(),
    body: ByteArray
) {
    write(buildResponseStatusLine(httpVersion, statusCode, message))
    writeHeaders(headers)
    write(Constants.CRLF)
    flush()

    outputStream.write(body)
    outputStream.flush()
}