import java.io.File

fun String.writeToFile(filename: String, isDeleteOnExit: Boolean = true): File {
    val file = File(filename)
    if (file.exists()) file.delete()
    file.writeText(this)
    if (isDeleteOnExit) file.deleteOnExit()

    return file
}

fun String.deleteFile() = File(this).delete()