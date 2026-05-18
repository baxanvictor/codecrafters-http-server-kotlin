package requestprocessors

import java.io.BufferedReader

fun BufferedReader.readRequestStartLine(): String? = readLine()?.trim()