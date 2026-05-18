package utils.responses

import dto.HttpStatusCode
import dto.HttpVersion
import java.io.BufferedWriter
import java.io.OutputStream

fun BufferedWriter.writeOk(
    httpVersion: HttpVersion,
    message: String = "OK",
    requestHeaders: Map<String, String> = emptyMap(),
    responseHeaders: Map<String, String> = emptyMap(),
    body: String? = null
) {
    writeMessage(
        httpVersion = httpVersion,
        statusCode = HttpStatusCode.OK,
        message = message,
        requestHeaders = requestHeaders,
        responseHeaders = responseHeaders,
        body = body
    )
}

fun BufferedWriter.writeOkBytes(
    outputStream: OutputStream,
    httpVersion: HttpVersion,
    message: String = "OK",
    requestHeaders: Map<String, String> = emptyMap(),
    responseHeaders: Map<String, String> = emptyMap(),
    body: ByteArray
) {
    writeBytes(
        outputStream = outputStream,
        httpVersion = httpVersion,
        statusCode = HttpStatusCode.OK,
        message = message,
        requestHeaders = requestHeaders,
        responseHeaders = responseHeaders,
        body = body
    )
}

fun BufferedWriter.writeCreated(
    httpVersion: HttpVersion,
    message: String = "Created",
    requestHeaders: Map<String, String> = emptyMap(),
    responseHeaders: Map<String, String> = emptyMap(),
) {
    writeMessage(
        httpVersion = httpVersion,
        statusCode = HttpStatusCode.CREATED,
        message = message,
        requestHeaders = requestHeaders,
        responseHeaders = responseHeaders,
    )
}