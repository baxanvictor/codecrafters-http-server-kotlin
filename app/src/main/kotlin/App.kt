import java.net.ServerSocket;

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
                    val writer = socket.getOutputStream().bufferedWriter()
                    val message = "HTTP/1.1 200 OK\r\n\r\n"

                    writer.run {
                        write(message)
                        flush()
                    }
                }
            }.start()
        }
    }
}
