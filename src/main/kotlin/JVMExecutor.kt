import java.io.File
import java.util.concurrent.TimeUnit

const val KOTLIN_INPUT_FILENAME = "input.txt"
const val KOTLIN_OUTPUT_FILENAME = "output.txt"

class JVMExecutor: IExecutor {
    override fun execute(executableFilename: String, input: String, timeOutSeconds: Double): IExecutor.Result {
        val inputFile = input.writeToFile(KOTLIN_INPUT_FILENAME)
        val outputFile = File(KOTLIN_OUTPUT_FILENAME)

        val startTime = System.currentTimeMillis()
        val executeProcess = ProcessBuilder(
            "java",
            "-jar",
            executableFilename)
        executeProcess.redirectInput(inputFile)
        executeProcess.redirectOutput(outputFile)
        val process = executeProcess.start()
        val isFinished = process.waitFor(
            (timeOutSeconds * 1000).toLong(),
            TimeUnit.MILLISECONDS
        )
        process.destroy()
        process.waitFor() // Wait for process terminated

        val isCorrupted = process.exitValue() != 0
        val executedTime = System.currentTimeMillis() - startTime

        val output = outputFile.readText()
        inputFile.delete()
        outputFile.delete()
        return IExecutor.Result(
            !isFinished,
            isCorrupted,
            output
        )
    }
}