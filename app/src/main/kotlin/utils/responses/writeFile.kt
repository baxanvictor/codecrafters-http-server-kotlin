package utils.responses

import dto.HttpHeader
import dto.HttpVersion
import utils.Constants
import java.io.BufferedWriter
import java.io.OutputStream
import java.nio.file.Path
import kotlin.io.path.readBytes

fun BufferedWriter.writeFile(
    file: Path,
    outputStream: OutputStream,
    httpVersion: HttpVersion
) {
    val bytes = file.readBytes()

    writeOk(
        httpVersion = httpVersion,
        headers = mapOf(
            HttpHeader.CONTENT_TYPE to Constants.APPLICATION_OCTET_STREAM,
            HttpHeader.CONTENT_LENGTH to bytes.size.toString()
        ),
    )

    outputStream.write(bytes)
    outputStream.flush()
}