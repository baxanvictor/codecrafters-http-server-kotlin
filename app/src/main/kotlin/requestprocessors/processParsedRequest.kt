package requestprocessors

import dto.HttpVersion
import dto.ParsedArg
import dto.RequestMethod
import dto.RequestStartLine
import dto.RequestTarget
import requestprocessors.endpoints.processGetEndpoint
import requestprocessors.endpoints.processPostEndpoint
import utils.Constants
import utils.responses.writeBadRequestError
import utils.responses.writeOk
import java.io.BufferedWriter
import java.io.OutputStream

fun BufferedWriter.processParsedRequest(
    args: Set<ParsedArg>,
    outputStream: OutputStream,
    requestStartLine: RequestStartLine?,
    requestHeaders: Map<String, String>,
    requestBody: String?
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
                method = requestStartLine.requestTarget.requestMethod,
                httpVersion = httpVersion,
                requestHeaders = requestHeaders,
                body = requestBody
            )
        }
    }
}

fun BufferedWriter.processEndpoint(
    args: Set<ParsedArg>,
    outputStream: OutputStream,
    path: String,
    method: RequestMethod,
    httpVersion: HttpVersion,
    requestHeaders: Map<String, String>,
    body: String?
) {
    when (method) {
        RequestMethod.GET -> processGetEndpoint(
            args = args,
            outputStream = outputStream,
            path = path,
            httpVersion = httpVersion,
            requestHeaders = requestHeaders
        )

        RequestMethod.POST -> processPostEndpoint(
            args = args,
            path = path,
            httpVersion = httpVersion,
            requestHeaders = requestHeaders,
            body = body
        )

        else -> Unit
    }

}

