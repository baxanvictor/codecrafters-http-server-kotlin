package requestprocessors

import dto.ParsedArg
import dto.ParsedArgType
import exceptions.FilenameNotSpecifiedException
import java.nio.file.Path

fun filePathFromPathAndArgs(
    args: Set<ParsedArg>,
    path: String,
): Path? {
    val dir = args
        .find { it.type == ParsedArgType.DIRECTORY }
        ?.value
        ?: return null

    val filename = path.split('/').lastOrNull()
    if (filename.isNullOrEmpty()) {
        throw FilenameNotSpecifiedException()
    }

    return Path.of("$dir/$filename")
}