package requestprocessors.endpoints

import dto.HttpHeader
import dto.HttpVersion
import utils.Constants
import utils.responses.writeNotFoundError
import utils.responses.writeOk
import java.io.BufferedWriter

fun BufferedWriter.processEchoEndpoint(
    path: String,
    httpVersion: HttpVersion
) {
    val pathSuffix = path.split('/').last()
    if (pathSuffix.isEmpty()) {
        writeNotFoundError(
            httpVersion = httpVersion
        )
    } else {
        val headers = mapOf(
            HttpHeader.CONTENT_TYPE to Constants.TEXT_PLAIN,
            HttpHeader.CONTENT_LENGTH to pathSuffix.length.toString()
        )

        writeOk(
            httpVersion = httpVersion,
            headers = headers,
            body = pathSuffix
        )
    }
}