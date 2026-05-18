package utils

import dto.ParsedArg
import dto.ParsedArgType
import kotlinx.cli.ArgParser
import kotlinx.cli.ArgType

fun ArgParser.parseCommandLineArgs(args: Array<String>): Set<ParsedArg> {
    val directory by option(
        type = ArgType.String,
        fullName = "directory"
    )

    parse(args)

    return setOf(
        ParsedArg(
            type = ParsedArgType.DIRECTORY,
            value = directory
        )
    )
}