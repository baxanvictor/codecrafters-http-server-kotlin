package utils

import dto.HttpVersion

object Constants {
    const val CRLF = "\r\n"
    const val TEXT_PLAIN = "text/plain"
    const val APPLICATION_OCTET_STREAM = "application/octet-stream"

    val HttpVersion1_1 = HttpVersion(1, 1)

    const val DEFAULT_BAD_REQUEST_MESSAGE = "The request is invalid"

    const val ECHO_ENDPOINT = "/echo/"
    const val USER_AGENT_ENDPOINT = "/user-agent"
    const val FILES_ENDPOINT = "/files/"
}