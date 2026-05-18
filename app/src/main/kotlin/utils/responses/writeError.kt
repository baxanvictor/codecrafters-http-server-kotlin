package utils.responses

import dto.HttpStatusCode
import dto.HttpVersion
import java.io.BufferedWriter

fun BufferedWriter.writeNotFoundError(httpVersion: HttpVersion) {
    writeMessage(
        httpVersion = httpVersion,
        statusCode = HttpStatusCode.NOT_FOUND,
        message = "Not Found"
    )
}

fun BufferedWriter.writeBadRequestError(httpVersion: HttpVersion) {
    writeMessage(
        httpVersion = httpVersion,
        statusCode = HttpStatusCode.BAD_REQUEST,
        message = "The request is invalid"
    )
}
