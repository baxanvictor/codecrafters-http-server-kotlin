import requestprocessors.parseRequestStartLine
import requestprocessors.processParsedRequest
import java.net.ServerSocket

fun main() {
    // You can use print statements as follows for debugging, they'll be visible when running tests.
    println("Logs from your program will appear here!")

    ServerSocket(4221).use { server ->
        // Since the tester restarts your program quite often, setting SO_REUSEADDR
        // ensures that we don't run into 'Address already in use' errors
        server.reuseAddress = true

        while (true) {
            val client = server.accept()

            Thread {
                client.use { socket ->
                    val reader = socket.getInputStream().bufferedReader()
                    val requestStartLine = parseRequestStartLine(
                        startLine = reader.readLine().trim()
                    )

                    val writer = socket.getOutputStream().bufferedWriter()
                    writer.processParsedRequest(
                        requestStartLine = requestStartLine
                    )
                }
            }.start()
        }
    }
}
