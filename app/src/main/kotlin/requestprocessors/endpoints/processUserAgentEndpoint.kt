package requestprocessors.endpoints

import dto.HttpHeader
import dto.HttpVersion
import utils.Constants
import utils.responses.writeBadRequestError
import utils.responses.writeOk
import java.io.BufferedWriter

fun BufferedWriter.processUserAgentEndpoint(
    httpVersion: HttpVersion,
    requestHeaders: Map<String, String>,
) {
    val userAgent = requestHeaders[HttpHeader.USER_AGENT]
    if (userAgent == null) {
        writeBadRequestError(
            httpVersion = httpVersion,
            requestHeaders = requestHeaders,
            message = "The User-Agent header is missing"
        )
        return
    }

    writeOk(
        httpVersion = httpVersion,
        requestHeaders = requestHeaders,
        responseHeaders = mapOf(
            HttpHeader.CONTENT_TYPE to Constants.TEXT_PLAIN,
            HttpHeader.CONTENT_LENGTH to userAgent.length.toString()
        ),
        body = userAgent
    )
}