package utils.responses

import dto.HttpStatusCode
import dto.HttpVersion
import java.io.BufferedWriter

fun BufferedWriter.writeNotFoundError(
    httpVersion: HttpVersion,
    headers: Map<String, String> = emptyMap(),
    body: String? = null
) {
    writeMessage(
        httpVersion = httpVersion,
        statusCode = HttpStatusCode.NOT_FOUND,
        message = "Not Found",
        headers = headers,
        body = body
    )
}

fun BufferedWriter.writeBadRequestError(
    httpVersion: HttpVersion,
    headers: Map<String, String> = emptyMap(),
    message: String = "The request is invalid",
    body: String? = null
) {
    writeMessage(
        httpVersion = httpVersion,
        statusCode = HttpStatusCode.BAD_REQUEST,
        message = message,
        headers = headers,
        body = body
    )
}
