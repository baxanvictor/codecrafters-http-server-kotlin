package requestprocessors.endpoints

import dto.HttpVersion
import dto.ParsedArg
import utils.Constants
import utils.responses.writeBadRequestError
import utils.responses.writeNotFoundError
import java.io.BufferedWriter

fun BufferedWriter.processPostEndpoint(
    args: Set<ParsedArg>,
    path: String,
    httpVersion: HttpVersion,
    requestHeaders: Map<String, String>,
    body: String?
) {
    if (body == null) {
        writeBadRequestError(
            httpVersion = httpVersion,
            message = "POST request body missing"
        )
        return
    }

    when {
        path.startsWith(Constants.FILES_ENDPOINT) -> processPostFileEndpoint(
            args = args,
            path = path,
            httpVersion = httpVersion,
            requestHeaders = requestHeaders,
            body = body
        )
        else -> {
            writeNotFoundError(
                httpVersion = httpVersion
            )
        }
    }
}