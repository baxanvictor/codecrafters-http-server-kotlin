package dto

enum class RequestMethod {
    GET, POST, PUT, PATCH, CONNECT, OPTIONS;

    companion object {
        fun safeValueOf(value: String): RequestMethod? {
            return runCatching {
                RequestMethod.valueOf(value)
            }.getOrNull()
        }
    }
}