package requestprocessors.endpoints

import dto.HttpHeader
import dto.HttpVersion
import dto.ParsedArg
import requestprocessors.filePathFromPathAndArgs
import utils.Constants
import utils.responses.writeBadRequestError
import utils.responses.writeCreated
import java.io.BufferedWriter
import java.nio.file.Files

fun BufferedWriter.processPostFileEndpoint(
    args: Set<ParsedArg>,
    path: String,
    httpVersion: HttpVersion,
    requestHeaders: Map<String, String>,
    body: String
) {
    val contentLength = requestHeaders[HttpHeader.CONTENT_LENGTH]?.toIntOrNull()
    if (contentLength == null) {
        writeBadRequestError(
            httpVersion = httpVersion,
            message = "${HttpHeader.CONTENT_LENGTH} header is missing or has an invalid value"
        )
        return
    }

    val fsFile = runCatching {
        filePathFromPathAndArgs(args, path)
    }.getOrElse { exception ->
        writeBadRequestError(
            httpVersion = httpVersion,
            message = exception.message ?: Constants.DEFAULT_BAD_REQUEST_MESSAGE
        )
        null
    } ?: return

    Files.write(fsFile, body.toByteArray())

    writeCreated(httpVersion = httpVersion)
}