package utils.responses

import dto.HttpStatusCode
import dto.HttpVersion
import mappers.formatted
import utils.Constants
import java.io.BufferedWriter

fun BufferedWriter.writeMessage(
    httpVersion: HttpVersion,
    statusCode: HttpStatusCode,
    message: String
) {
    val output = buildString {
        append(httpVersion.formatted())
        append(' ')
        append(statusCode.code)
        append(' ')
        append(message)
        append(Constants.CRLF)
        append(Constants.CRLF)
    }

    write(output)
    flush()
}