package utils.responses

import utils.Constants
import java.io.BufferedWriter

fun BufferedWriter.writeHeaders(
    headers: Map<String, String>
) {
    headers.entries.forEach { (key, value) ->
        writeHeader(key, value)
    }
}

fun BufferedWriter.writeHeader(
    key: String,
    value: String
) {
    val output = buildString {
        append(key)
        append(": ")
        append(value)
        append(Constants.CRLF)
    }

    write(output)
}