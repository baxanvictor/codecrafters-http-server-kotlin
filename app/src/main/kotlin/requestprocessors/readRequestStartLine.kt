package requestprocessors

import java.io.BufferedReader

fun BufferedReader.readRequestStartLine() = readLine().trim()