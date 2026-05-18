package dto

enum class CompressionScheme(val scheme: String) {
    GZIP("gzip");

    companion object {
        fun findByScheme(value: String): CompressionScheme? {
            return CompressionScheme.entries.firstOrNull { it.scheme == value }
        }
    }
}