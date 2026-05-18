package requestprocessors

import dto.HttpVersion
import dto.RequestMethod
import dto.RequestStartLine
import dto.RequestTarget
import utils.toUri

fun parseRequestStartLine(startLine: String): RequestStartLine? {
    val pieces = startLine.split(' ')
    if (pieces.size != 3) {
        return null
    }

    val method = RequestMethod.safeValueOf(pieces[0]) ?: return null

    val target = parseRequestTarget(
        rawTarget = pieces[1],
        requestMethod = method
    ) ?: return null

    val httpVersion = parseHttpVersion(pieces[2]) ?: return null

    return RequestStartLine(
        requestTarget = target,
        httpVersion = httpVersion
    )
}

private fun parseRequestTarget(
    rawTarget: String,
    requestMethod: RequestMethod
): RequestTarget? {
    return when (requestMethod) {
        RequestMethod.OPTIONS -> RequestTarget.Asterisk.takeIf { rawTarget == "*" }
        RequestMethod.CONNECT -> parseAuthorityRequestTarget(rawTarget)
        else -> parseOriginOrAbsoluteRequestTarget(rawTarget, requestMethod)
    }
}

private fun parseAuthorityRequestTarget(rawTarget: String): RequestTarget.Authority? {
    if (rawTarget.toUri()?.isAbsolute != true) {
        return null
    }

    val pieces = rawTarget.split(':')
    if (pieces.size != 2) {
        return null
    }

    return RequestTarget.Authority(
        uriHost = pieces[0],
        port = pieces[1].toIntOrNull() ?: return null
    )
}

private fun parseOriginOrAbsoluteRequestTarget(
    rawTarget: String,
    requestMethod: RequestMethod
): RequestTarget? {
    val uri = rawTarget.toUri() ?: return null

    return if (uri.isAbsolute) {
        RequestTarget.Absolute(
            requestMethod = requestMethod,
            uri = uri
        )
    } else {
        RequestTarget.Origin(
            requestMethod = requestMethod,
            uri = uri
        )
    }
}

private fun parseHttpVersion(rawHttpVersion: String): HttpVersion? {
    val pieces = rawHttpVersion.split('/', '.')
    if (pieces.size != 3){
        return null
    }

    if (pieces[0] != "HTTP") {
        return null
    }

    return HttpVersion(
        major = pieces[1].toIntOrNull() ?: return null,
        minor = pieces[2].toIntOrNull() ?: return null
    )
}