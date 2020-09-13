import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths

const val KOTLIN_CODE_FILENAME = "_code.kt"
const val KOTLIN_CODE_EXECUTABLE_FILENAME = "_code.jar"

class KotlinCompiler(val workspace: String): ICompiler {
    init {
        Files.createDirectories(Paths.get(workspace))
    }

    override fun compile(code: String): String {
        val codeFilePath = workspace.appendPath(KOTLIN_CODE_FILENAME)
        val executableFilePath = workspace.appendPath(KOTLIN_CODE_EXECUTABLE_FILENAME)
        val codeFile = code.writeToFile(codeFilePath)

        val compileProcess = ProcessBuilder(
            "docker",
            "run",
            "--rm",
            "-v",
            "${System.getProperty("user.dir").appendPath(workspace)}:/$workspace",
            "zenika/kotlin",
            "kotlinc",
            "/$codeFilePath",
            "-include-runtime",
            "-d",
            "/$executableFilePath")
        compileProcess.redirectError(ProcessBuilder.Redirect.INHERIT)
        compileProcess.start().waitFor()

        codeFile.delete()
        return executableFilePath
    }
}