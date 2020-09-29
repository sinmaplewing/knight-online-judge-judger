import java.io.File
import java.nio.file.Files
import java.nio.file.Paths
import java.util.concurrent.TimeUnit

private const val PYTHON_INPUT_FILENAME = "input.txt"
private const val PYTHON_OUTPUT_FILENAME = "output.txt"
private const val PYTHON_DOCKER_CONTAINER_NAME = "python-docker"

class PythonExecutor(val workspace: String): IExecutor {
    init {
        Files.createDirectories(Paths.get(workspace))
    }

    override fun execute(executableFilename: String, input: String, timeOutSeconds: Double): IExecutor.Result {
        val inputFilePath = workspace.appendPath(PYTHON_INPUT_FILENAME)
        val outputFilePath = workspace.appendPath(PYTHON_OUTPUT_FILENAME)
        val inputFile = input.writeToFile(inputFilePath)
        val dockerContainerName = PYTHON_DOCKER_CONTAINER_NAME + RandomStringGenerator.Generate(32)

        val startTime = System.currentTimeMillis()
        val executeProcess = ProcessBuilder(
            "docker",
            "run",
            "--rm",
            "--name",
            dockerContainerName,
            "-v",
            "${System.getProperty("user.dir").appendPath(workspace)}:/$workspace",
            "python",
            "sh",
            "-c",
            "python3 /$executableFilename < /$inputFilePath > /$outputFilePath")
        executeProcess.redirectError(ProcessBuilder.Redirect.INHERIT)
        val process = executeProcess.start()
        val isFinished = process.waitFor(
            (timeOutSeconds * 1000).toLong(),
            TimeUnit.MILLISECONDS
        )
        if (!isFinished) {
            ProcessBuilder("docker", "kill", dockerContainerName).start().waitFor()
        }
        process.destroy()
        process.waitFor() // Wait for process terminated

        val isCorrupted = process.exitValue() != 0
        val executedTime = System.currentTimeMillis() - startTime

        val outputFile = File(outputFilePath)
        var output: String? = null
        if (outputFile.exists()) {
            output = outputFile.readText()
        }
        inputFile.delete()
        outputFile.delete()
        return IExecutor.Result(
            !isFinished,
            isCorrupted,
            executedTime.toDouble() / 1000.0,
            output
        )
    }
}