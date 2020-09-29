import java.io.File
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths

private const val PYTHON_CODE_FILENAME = "_code.py"

class PassThroughCompiler(val workspace: String): ICompiler {
    init {
        Files.createDirectories(Paths.get(workspace))
    }

    override fun compile(code: String): String {
        val codeFilePath = workspace.appendPath(PYTHON_CODE_FILENAME)
        val codeFile = code.writeToFile(codeFilePath)
        return codeFilePath
    }
}