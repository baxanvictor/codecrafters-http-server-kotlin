package requestprocessors.endpoints

import dto.HttpVersion
import dto.ParsedArg
import utils.Constants
import utils.responses.writeNotFoundError
import java.io.BufferedWriter
import java.io.OutputStream

fun BufferedWriter.processGetEndpoint(
    args: Set<ParsedArg>,
    outputStream: OutputStream,
    path: String,
    httpVersion: HttpVersion,
    requestHeaders: Map<String, String>
) {
    when {
        path.startsWith(Constants.ECHO_ENDPOINT) -> processEchoEndpoint(outputStream, path, httpVersion, requestHeaders)
        path == Constants.USER_AGENT_ENDPOINT -> processUserAgentEndpoint(httpVersion, requestHeaders)
        path.startsWith(Constants.FILES_ENDPOINT) -> processGetFileEndpoint(
            args = args,
            outputStream = outputStream,
            path = path,
            httpVersion = httpVersion
        )
        else -> {
            writeNotFoundError(
                httpVersion = httpVersion
            )
        }
    }
}