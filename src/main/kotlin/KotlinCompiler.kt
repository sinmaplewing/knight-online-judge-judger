import kotlin.Exception

const val KOTLIN_CODE_FILENAME = "_code.kt"
const val KOTLIN_CODE_EXECUTABLE_FILENAME = "_code.jar"

class KotlinCompiler: ICompiler {
    override fun compile(code: String): String {
        code.writeToFile(KOTLIN_CODE_FILENAME)

        val compileProcess = ProcessBuilder(
            "kotlinc",
            KOTLIN_CODE_FILENAME,
            "-include-runtime",
            "-d",
            KOTLIN_CODE_EXECUTABLE_FILENAME)
        compileProcess.start().waitFor()

        return KOTLIN_CODE_EXECUTABLE_FILENAME
    }
}