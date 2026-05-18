package requestprocessors.endpoints

import dto.CompressionScheme
import dto.HttpHeader
import dto.HttpVersion
import utils.Constants
import utils.responses.writeNotFoundError
import utils.responses.writeOk
import java.io.BufferedWriter

fun BufferedWriter.processEchoEndpoint(
    path: String,
    httpVersion: HttpVersion,
    requestHeaders: Map<String, String>
) {
    val pathSuffix = path.split('/').last()
    if (pathSuffix.isEmpty()) {
        writeNotFoundError(
            httpVersion = httpVersion
        )
    } else {
        val acceptEncoding = requestHeaders[HttpHeader.ACCEPT_ENCODING]

        val contentEncodingHeader = acceptEncoding
            ?.split(",")
            ?.mapNotNull { encoding ->
                CompressionScheme
                    .findByScheme(encoding.trim())
                    ?.scheme
            }
            ?.joinToString(", ")

        val responseHeaders = buildMap {
            put(HttpHeader.CONTENT_TYPE, Constants.TEXT_PLAIN)
            put(HttpHeader.CONTENT_LENGTH, pathSuffix.length.toString())
            contentEncodingHeader?.let {
                put(HttpHeader.CONTENT_ENCODING, it)
            }
        }

        writeOk(
            httpVersion = httpVersion,
            headers = responseHeaders,
            body = pathSuffix
        )
    }
}