import dto.HttpHeader
import dto.ParsedArg
import kotlinx.cli.ArgParser
import requestprocessors.*
import utils.Constants
import utils.parseCommandLineArgs
import java.io.BufferedReader
import java.io.BufferedWriter
import java.io.OutputStream
import java.net.ServerSocket
import java.net.Socket

fun main(args: Array<String>) {
    // You can use print statements as follows for debugging, they'll be visible when running tests.
    println("Logs from your program will appear here!")

    startServer(args)
}

private fun startServer(args: Array<String>) {
    ServerSocket(4221).use { server ->
        // Since the tester restarts your program quite often, setting SO_REUSEADDR
        // ensures that we don't run into 'Address already in use' errors
        server.reuseAddress = true

        val argsParser = ArgParser("codecrafters-http-server")
        val parsedArgs = argsParser.parseCommandLineArgs(args)

        while (true) {
            val client = server.accept()
            processClientConnection(client, parsedArgs)
        }
    }
}

private fun processClientConnection(
    client: Socket,
    args: Set<ParsedArg>
) {
    Thread {
        client.use { socket ->
            val reader = socket.getInputStream().bufferedReader()

            val outputStream = socket.getOutputStream()
            val writer = outputStream.bufferedWriter()

            while (true) {
                client.handleClientRequest(
                    reader = reader,
                    outputStream = outputStream,
                    writer = writer,
                    args = args
                )
            }
        }
    }.start()
}

private fun Socket.handleClientRequest(
    reader: BufferedReader,
    outputStream: OutputStream,
    writer: BufferedWriter,
    args: Set<ParsedArg>
) {
    runCatching {
        val startLine = reader.readRequestStartLine() ?: return@runCatching

        val requestStartLine = parseRequestStartLine(
            startLine = startLine
        )
        val requestHeaders = parseRequestHeaders(
            headerLines = reader.readRequestHeaderLines()
        )

        val body = reader.parseRequestBody(requestHeaders)

        writer.processParsedRequest(
            args = args,
            outputStream = outputStream,
            requestStartLine = requestStartLine,
            requestHeaders = requestHeaders,
            requestBody = body
        )

        maybeCloseConnection(requestHeaders)
    }
}

private fun Socket.maybeCloseConnection(requestHeaders: Map<String, String>) {
    val connectionHeader = requestHeaders[HttpHeader.CONNECTION] ?: return

    when (connectionHeader) {
        Constants.CLOSE -> close()
    }
}
