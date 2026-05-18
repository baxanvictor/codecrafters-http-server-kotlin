package requestprocessors

import dto.HttpHeader
import dto.HttpVersion
import dto.RequestStartLine
import dto.RequestTarget
import utils.Constants
import utils.responses.writeBadRequestError
import utils.responses.writeNotFoundError
import utils.responses.writeOk
import java.io.BufferedWriter

fun BufferedWriter.processParsedRequest(
    requestStartLine: RequestStartLine?,
    requestHeaders: Map<String, String>
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
            processEndpoint(path, httpVersion, requestHeaders)
        }
    }
}

fun BufferedWriter.processEndpoint(
    path: String,
    httpVersion: HttpVersion,
    requestHeaders: Map<String, String>
) {
    when {
        path.startsWith("/echo/") -> processEchoPath(path, httpVersion)
        path == "/user-agent" -> processUserAgentEndpoint(httpVersion, requestHeaders)
        else -> {
            writeNotFoundError(
                httpVersion = httpVersion
            )
        }
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

fun BufferedWriter.processUserAgentEndpoint(
    httpVersion: HttpVersion,
    requestHeaders: Map<String, String>
) {
    val userAgent = requestHeaders[HttpHeader.USER_AGENT]
    if (userAgent == null) {
        writeBadRequestError(
            httpVersion = httpVersion,
            message = "The User-Agent header is missing"
        )
        return
    }

    writeOk(
        httpVersion = httpVersion,
        headers = mapOf(
            HttpHeader.CONTENT_TYPE to Constants.TEXT_PLAIN,
            HttpHeader.CONTENT_LENGTH to userAgent.length.toString()
        ),
        body = userAgent
    )
}