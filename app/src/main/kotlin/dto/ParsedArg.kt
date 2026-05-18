package dto

data class ParsedArg(
    val type: ParsedArgType,
    val value: String?
)

enum class ParsedArgType {
    DIRECTORY
}
