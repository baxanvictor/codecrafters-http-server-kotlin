package utils

import dto.CompressionScheme
import java.io.ByteArrayOutputStream
import java.util.zip.GZIPOutputStream

fun String.compress(scheme: CompressionScheme): ByteArray {
    return when (scheme) {
        CompressionScheme.GZIP -> gzipCompress()
    }
}

private fun String.gzipCompress(): ByteArray {
    val bytes = toByteArray()

    return ByteArrayOutputStream().use { outputStream ->
        GZIPOutputStream(outputStream).use { gZipOutputStream ->
            gZipOutputStream.write(bytes)
        }

        outputStream.toByteArray()
    }
}