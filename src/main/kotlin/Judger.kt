class Judger(val compiler: ICompiler, val executor: IExecutor) {
    enum class Result { Accepted, WrongAnswer }

    fun judge(submission: SubmissionData): Result {
        val executableFilename = compiler.compile(submission.code)

        var isCorrect = true
        for (testCase in submission.testCases) {
            val output = executor.execute(executableFilename, testCase.input).trim()
            val expectedOutput = testCase.expectedOutput.trim()
            if (output != expectedOutput) {
                isCorrect = false
                break
            }
        }
        executableFilename.deleteFile()

        return if (isCorrect) Result.Accepted else Result.WrongAnswer
    }
}