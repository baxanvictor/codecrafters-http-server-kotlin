package dto

import java.net.URI

sealed interface RequestTarget {
    val requestMethod: RequestMethod

    data class Origin(
        override val requestMethod: RequestMethod,
        val uri: URI
    ) : RequestTarget

    data class Absolute(
        override val requestMethod: RequestMethod,
        val uri: URI
    ) : RequestTarget

    data class Authority(
        val uriHost: String,
        val port: Int
    ) : RequestTarget {
        override val requestMethod: RequestMethod = RequestMethod.CONNECT
    }

    data object Asterisk : RequestTarget {
        override val requestMethod: RequestMethod = RequestMethod.OPTIONS
    }
}