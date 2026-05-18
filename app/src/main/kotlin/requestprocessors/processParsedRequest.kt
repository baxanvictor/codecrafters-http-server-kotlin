package requestprocessors

import dto.HttpResponseHeader
import dto.HttpVersion
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

    val httpVersion = requestStartLine.httpVersion

    val uriPath = when (val requestTarget = requestStartLine.requestTarget) {
        is RequestTarget.Origin -> requestTarget.uri.path
        is RequestTarget.Absolute -> requestTarget.uri.path
        else -> null
    }

    uriPath?.let { path ->
        if (path == "/") {
            writeOk(
                httpVersion = httpVersion,
            )
        } else {
            processUriPath(path, httpVersion)
        }
    }
}

fun BufferedWriter.processUriPath(
    path: String,
    httpVersion: HttpVersion
) {
    if (path.startsWith("/echo/")) {
        processEchoPath(path, httpVersion)

    } else {
        writeNotFoundError(
            httpVersion = httpVersion
        )
    }
}

fun BufferedWriter.processEchoPath(
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
            HttpResponseHeader.CONTENT_TYPE to Constants.TEXT_PLAIN,
            HttpResponseHeader.CONTENT_LENGTH to pathSuffix.length.toString()
        )

        writeOk(
            httpVersion = httpVersion,
            headers = headers,
            body = pathSuffix
        )
    }
}