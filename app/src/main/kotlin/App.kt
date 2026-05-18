import kotlinx.cli.ArgParser
import requestprocessors.*
import utils.parseCommandLineArgs
import java.net.ServerSocket

fun main(args: Array<String>) {
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
                    val argsParser = ArgParser("codecrafters-http-server")
                    val parsedArgs = argsParser.parseCommandLineArgs(args)

                    val reader = socket.getInputStream().bufferedReader()

                    val requestStartLine = parseRequestStartLine(
                        startLine = reader.readRequestStartLine()
                    )
                    val requestHeaders = parseRequestHeaders(
                        headerLines = reader.readRequestHeaderLines()
                    )

                    val body = reader.parseRequestBody(requestHeaders)

                    val outputStream = socket.getOutputStream()
                    val writer = outputStream.bufferedWriter()

                    writer.processParsedRequest(
                        args = parsedArgs,
                        outputStream = outputStream,
                        requestStartLine = requestStartLine,
                        requestHeaders = requestHeaders,
                        requestBody = body
                    )
                }
            }.start()
        }
    }
}
