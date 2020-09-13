import java.io.File

fun String.writeToFile(filename: String): File {
    val file = File(filename)
    if (file.exists()) file.delete()
    file.writeText(this)

    return file
}

fun String.deleteFile() = File(this).delete()

fun String.appendPath(nextSegment: String) =
    if (this.endsWith('/')) this + nextSegment
        else "$this/$nextSegment"