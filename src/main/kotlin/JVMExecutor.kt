import java.io.File

const val KOTLIN_INPUT_FILENAME = "input.txt"
const val KOTLIN_OUTPUT_FILENAME = "output.txt"

class JVMExecutor: IExecutor {
    override fun execute(executableFilename: String, input: String): String {
        val inputFile = input.writeToFile(KOTLIN_INPUT_FILENAME)
        val outputFile = File(KOTLIN_OUTPUT_FILENAME)

        val executeProcess = ProcessBuilder(
            "java",
            "-jar",
            executableFilename)
        executeProcess.redirectInput(inputFile)
        executeProcess.redirectOutput(outputFile)
        executeProcess.start().waitFor()

        val output = outputFile.readText()
        outputFile.delete()
        return output
    }
}