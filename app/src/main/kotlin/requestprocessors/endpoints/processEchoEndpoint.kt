package requestprocessors.endpoints

import dto.CompressionScheme
import dto.HttpHeader
import dto.HttpVersion
import utils.Constants
import utils.compress
import utils.responses.writeNotFoundError
import utils.responses.writeOkBytes
import java.io.BufferedWriter
import java.io.OutputStream

fun BufferedWriter.processEchoEndpoint(
    outputStream: OutputStream,
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

        val compressionScheme = acceptEncoding
            ?.split(",")
            ?.firstNotNullOfOrNull { encoding ->
                CompressionScheme.findByScheme(encoding.trim())
            }

        val compressedMessage = compressionScheme?.let {
            pathSuffix.compress(it)
        } ?: pathSuffix.toByteArray()

        val responseHeaders = buildMap {
            put(HttpHeader.CONTENT_TYPE, Constants.TEXT_PLAIN)
            put(HttpHeader.CONTENT_LENGTH, compressedMessage.size.toString())
            compressionScheme?.let {
                put(HttpHeader.CONTENT_ENCODING, compressionScheme.scheme)
            }
        }

        writeOkBytes(
            outputStream = outputStream,
            httpVersion = httpVersion,
            headers = responseHeaders,
            body = compressedMessage
        )
    }
}