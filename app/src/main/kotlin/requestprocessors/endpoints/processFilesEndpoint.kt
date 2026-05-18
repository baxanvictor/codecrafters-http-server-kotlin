package requestprocessors.endpoints

import dto.HttpVersion
import dto.ParsedArg
import dto.ParsedArgType
import utils.responses.writeBadRequestError
import utils.responses.writeFile
import utils.responses.writeNotFoundError
import java.io.BufferedWriter
import java.io.OutputStream
import java.nio.file.Path
import kotlin.io.path.exists

fun BufferedWriter.processFilesEndpoint(
    args: Set<ParsedArg>,
    outputStream: OutputStream,
    path: String,
    httpVersion: HttpVersion,
) {
    val dir = args
        .find { it.type == ParsedArgType.DIRECTORY }
        ?.value
        ?: return

    val filename = path.split('/').lastOrNull()
    if (filename.isNullOrEmpty()) {
        writeBadRequestError(
            httpVersion = httpVersion,
            message = "No filename specified"
        )
        return
    }

    val fsFile = Path.of("$dir/$filename")
    if (!fsFile.exists()) {
        writeNotFoundError(httpVersion = httpVersion)
        return
    }

    writeFile(
        file = fsFile,
        outputStream = outputStream,
        httpVersion = httpVersion
    )
}