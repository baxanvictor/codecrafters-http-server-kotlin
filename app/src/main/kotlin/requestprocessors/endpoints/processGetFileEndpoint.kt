package requestprocessors.endpoints

import dto.HttpVersion
import dto.ParsedArg
import requestprocessors.filePathFromPathAndArgs
import utils.Constants
import utils.responses.writeBadRequestError
import utils.responses.writeFile
import utils.responses.writeNotFoundError
import java.io.BufferedWriter
import java.io.OutputStream
import kotlin.io.path.exists

fun BufferedWriter.processGetFileEndpoint(
    args: Set<ParsedArg>,
    outputStream: OutputStream,
    path: String,
    httpVersion: HttpVersion,
) {
    val fsFile = runCatching {
        filePathFromPathAndArgs(args, path)
    }.getOrElse { exception ->
        writeBadRequestError(
            httpVersion = httpVersion,
            message = exception.message ?: Constants.DEFAULT_BAD_REQUEST_MESSAGE
        )
        null
    } ?: return

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