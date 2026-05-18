package requestprocessors

fun parseRequestHeaders(headerLines: List<String>): Map<String, String> {
    val headers = mutableMapOf<String, String>()

    for (line in headerLines) {
        val pieces = line.split(": ")
        if (pieces.size != 2) {
            continue
        }

        headers.putIfAbsent(pieces[0], pieces[1])
    }

    return headers
}