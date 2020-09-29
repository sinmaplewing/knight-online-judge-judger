import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths

private const val GCC_CODE_FILENAME = "_code.c"
private const val GCC_CODE_EXECUTABLE_FILENAME = "_code"

class GCCCompiler(val workspace: String): ICompiler {
    init {
        Files.createDirectories(Paths.get(workspace))
    }

    override fun compile(code: String): String {
        val codeFilePath = workspace.appendPath(GCC_CODE_FILENAME)
        val executableFilePath = workspace.appendPath(GCC_CODE_EXECUTABLE_FILENAME)
        val codeFile = code.writeToFile(codeFilePath)

        val compileProcess = ProcessBuilder(
            "docker",
            "run",
            "--rm",
            "-v",
            "${System.getProperty("user.dir").appendPath(workspace)}:/$workspace",
            "gcc",
            "gcc",
            "/$codeFilePath",
            "-o",
            "/$executableFilePath")
        compileProcess.redirectError(ProcessBuilder.Redirect.INHERIT)
        compileProcess.start().waitFor()

        codeFile.delete()
        return executableFilePath
    }
}