interface IExecutor {
    data class Result(
        val isTimeOut: Boolean,
        val isCorrupted: Boolean,
        val output: String
    )

    fun execute(executableFilename: String, input: String, timeOutSeconds: Double): Result
}