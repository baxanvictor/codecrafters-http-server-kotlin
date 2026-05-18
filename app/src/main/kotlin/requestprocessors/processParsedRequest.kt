package requestprocessors

import dto.RequestStartLine
import dto.RequestTarget
import utils.Constants
import utils.responses.writeBadRequestError
import utils.responses.writeNotFoundError
import utils.responses.writeOk
import java.io.BufferedWriter

fun BufferedWriter.processParsedRequest(
    requestStartLine: RequestStartLine?
) {
    if (requestStartLine == null) {
        writeBadRequestError(
            httpVersion = Constants.HttpVersion1_1
        )
        return
    }

    val uriPath = when (val requestTarget = requestStartLine.requestTarget) {
        is RequestTarget.Origin -> requestTarget.uri.path
        is RequestTarget.Absolute -> requestTarget.uri.path
        else -> null
    }

    uriPath?.let { path ->
        if (path == "/") {
            writeOk(
                httpVersion = requestStartLine.httpVersion
            )
        } else {
            writeNotFoundError(
                httpVersion = requestStartLine.httpVersion
            )
        }
    }
}