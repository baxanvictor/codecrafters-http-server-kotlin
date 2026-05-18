package utils.responses

import dto.HttpStatusCode
import dto.HttpVersion
import utils.Constants
import java.io.BufferedWriter

fun BufferedWriter.writeNotFoundError(
    httpVersion: HttpVersion,
    requestHeaders: Map<String, String> = emptyMap(),
    responseHeaders: Map<String, String> = emptyMap(),
    body: String? = null
) {
    writeMessage(
        httpVersion = httpVersion,
        statusCode = HttpStatusCode.NOT_FOUND,
        message = "Not Found",
        requestHeaders = requestHeaders,
        responseHeaders = responseHeaders,
        body = body
    )
}

fun BufferedWriter.writeBadRequestError(
    httpVersion: HttpVersion,
    requestHeaders: Map<String, String> = emptyMap(),
    responseHeaders: Map<String, String> = emptyMap(),
    message: String = Constants.DEFAULT_BAD_REQUEST_MESSAGE,
    body: String? = null
) {
    writeMessage(
        httpVersion = httpVersion,
        statusCode = HttpStatusCode.BAD_REQUEST,
        message = message,
        requestHeaders = requestHeaders,
        responseHeaders = responseHeaders,
        body = body
    )
}

fun BufferedWriter.writeInternalServerError(
    httpVersion: HttpVersion,
    requestHeaders: Map<String, String> = emptyMap()
) {
    writeMessage(
        httpVersion = httpVersion,
        statusCode = HttpStatusCode.INTERNAL_SERVER_ERROR,
        requestHeaders = requestHeaders,
        message = "Internal server error"
    )
}
