package requestprocessors

import java.io.BufferedReader

fun BufferedReader.readRequestHeaderLines(): List<String> {
    val headerLines = mutableListOf<String>()

    while (true) {
        val line = readLine().trim()
        if (line.isEmpty()) {
            break
        }

        headerLines.add(line)
    }

    return headerLines
}