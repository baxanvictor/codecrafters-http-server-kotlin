package utils.responses

import dto.HttpStatusCode
import dto.HttpVersion
import java.io.BufferedWriter

fun BufferedWriter.writeOk(
    httpVersion: HttpVersion,
    message: String = "OK",
    headers: Map<String, String> = emptyMap(),
    body: String? = null
) {
    writeMessage(
        httpVersion = httpVersion,
        statusCode = HttpStatusCode.OK,
        message = message,
        headers = headers,
        body = body
    )
}

fun BufferedWriter.writeCreated(
    httpVersion: HttpVersion,
    message: String = "Created",
    headers: Map<String, String> = emptyMap(),
    body: String? = null
) {
    writeMessage(
        httpVersion = httpVersion,
        statusCode = HttpStatusCode.CREATED,
        message = message,
        headers = headers,
        body = body
    )
}