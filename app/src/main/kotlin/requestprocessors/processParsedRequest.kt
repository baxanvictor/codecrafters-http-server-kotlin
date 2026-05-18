package requestprocessors

import dto.HttpVersion
import dto.ParsedArg
import dto.RequestStartLine
import dto.RequestTarget
import requestprocessors.endpoints.processEchoEndpoint
import requestprocessors.endpoints.processFilesEndpoint
import requestprocessors.endpoints.processUserAgentEndpoint
import utils.Constants
import utils.responses.writeBadRequestError
import utils.responses.writeNotFoundError
import utils.responses.writeOk
import java.io.BufferedWriter
import java.io.OutputStream

fun BufferedWriter.processParsedRequest(
    args: Set<ParsedArg>,
    outputStream: OutputStream,
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
            processEndpoint(
                args = args,
                outputStream = outputStream,
                path = path,
                httpVersion = httpVersion,
                requestHeaders = requestHeaders
            )
        }
    }
}

fun BufferedWriter.processEndpoint(
    args: Set<ParsedArg>,
    outputStream: OutputStream,
    path: String,
    httpVersion: HttpVersion,
    requestHeaders: Map<String, String>
) {
    when {
        path.startsWith("/echo/") -> processEchoEndpoint(path, httpVersion)
        path == "/user-agent" -> processUserAgentEndpoint(httpVersion, requestHeaders)
        path.startsWith("/files/") -> processFilesEndpoint(
            args = args,
            outputStream = outputStream,
            path = path,
            httpVersion = httpVersion
        )
        else -> {
            writeNotFoundError(
                httpVersion = httpVersion
            )
        }
    }
}

