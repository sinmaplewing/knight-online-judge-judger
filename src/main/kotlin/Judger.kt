import java.io.File
import java.lang.Exception

const val NO_EXECUTED_TIME = -1.0

class Judger(val compiler: ICompiler, val executor: IExecutor) {
    enum class Result { Accepted, WrongAnswer, CompileError, RuntimeError, TimeLimitExceeded }
    data class ResultWithTime(val result: Result, val executedTime: Double)

    fun judge(submission: SubmissionData): ResultWithTime {
        var executableFilename: String? = null

        try {
            executableFilename = compiler.compile(submission.code)
        }
        catch(e: Exception) {
            return ResultWithTime(Result.CompileError, NO_EXECUTED_TIME)
        }

        if (executableFilename == null ||
            !File(executableFilename).exists()) {
            return ResultWithTime(Result.CompileError, NO_EXECUTED_TIME)
        }

        val resultWithTime = execute(executableFilename, submission.testCases)
        executableFilename.deleteFile()
        return resultWithTime
    }

    private fun execute(executableFilename: String, testCases: List<TestCaseData>): ResultWithTime {
        var isCorrect = true
        var totalExecutedTime = 0.0
        for (testCase in testCases) {
            var result: IExecutor.Result?
            try {
                result = executor.execute(executableFilename, testCase.input, testCase.timeOutSeconds)
            }
            catch (e: Exception) {
                return ResultWithTime(Result.RuntimeError, totalExecutedTime)
            }

            if (result == null) return ResultWithTime(Result.RuntimeError, totalExecutedTime)
            if (result.isTimeOut) return ResultWithTime(Result.TimeLimitExceeded, totalExecutedTime)
            if (result.isCorrupted) return ResultWithTime(Result.RuntimeError, totalExecutedTime)

            val output = result.output.trim()
            val expectedOutput = testCase.expectedOutput.trim()
            if (output != expectedOutput) {
                isCorrect = false
                break
            }

            totalExecutedTime += result.executedTime
        }

        return if (isCorrect) ResultWithTime(Result.Accepted, totalExecutedTime)
        else ResultWithTime(Result.WrongAnswer, totalExecutedTime)
    }
}