package utils.responses

import dto.HttpStatusCode
import dto.HttpVersion
import utils.Constants
import java.io.BufferedWriter

fun BufferedWriter.writeMessage(
    httpVersion: HttpVersion,
    statusCode: HttpStatusCode,
    message: String,
    headers: Map<String, String>,
    body: String?
) {
    write(buildResponseStatusLine(httpVersion, statusCode, message))
    writeHeaders(headers)
    write(Constants.CRLF)
    body?.let { write(it) }
    flush()
}