import java.io.File
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths

const val JAVA_CODE_FILENAME = "Main.java"
const val JAVA_CLASS_FILENAME = "Main.class"
const val JAVA_CODE_EXECUTABLE_FILENAME = "_code.jar"
const val JAVA_MANIFEST_FILENAME = "MANIFEST.MF"

class JavaCompiler(val workspace: String): ICompiler {
    init {
        Files.createDirectories(Paths.get(workspace))
    }

    override fun compile(code: String): String {
        val codeFilePath = workspace.appendPath(JAVA_CODE_FILENAME)
        val classFilePath = workspace.appendPath(JAVA_CLASS_FILENAME)
        val manifestFilePath = workspace.appendPath(JAVA_MANIFEST_FILENAME)
        val codeFile = code.writeToFile(codeFilePath)
        val manifestFile = "Main-Class: Main\n\n\n".writeToFile(manifestFilePath)

        val compileProcess = ProcessBuilder(
            "docker",
            "run",
            "--rm",
            "-v",
            "${System.getProperty("user.dir").appendPath(workspace)}:/$workspace",
            "zenika/kotlin",
            "sh",
            "-c",
            "cd /$workspace; javac $JAVA_CODE_FILENAME; jar -cvfm $JAVA_CODE_EXECUTABLE_FILENAME $JAVA_MANIFEST_FILENAME $JAVA_CLASS_FILENAME")
        compileProcess.redirectError(ProcessBuilder.Redirect.INHERIT)
        compileProcess.start().waitFor()

        codeFile.delete()
        manifestFile.delete()
        File(classFilePath).delete()
        return workspace.appendPath(JAVA_CODE_EXECUTABLE_FILENAME)
    }
}