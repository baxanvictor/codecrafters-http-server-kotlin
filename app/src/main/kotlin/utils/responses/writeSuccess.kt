package utils.responses

import dto.HttpStatusCode
import dto.HttpVersion
import java.io.BufferedWriter

fun BufferedWriter.writeOk(
    httpVersion: HttpVersion,
    message: String = "OK"
) {
    writeMessage(
        httpVersion = httpVersion,
        statusCode = HttpStatusCode.OK,
        message = message
    )
}