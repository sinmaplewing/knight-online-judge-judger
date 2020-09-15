import java.io.File
import java.lang.Exception

const val NO_EXECUTED_TIME = -1.0
const val NO_SCORE = 0

class Judger(val compiler: ICompiler, val executor: IExecutor) {
    enum class Result { Accepted, WrongAnswer, CompileError, RuntimeError, TimeLimitExceeded }
    data class ResultState(val result: Result, val executedTime: Double, val totalScore: Int)

    fun judge(submission: SubmissionData): ResultState {
        var executableFilename: String? = null

        try {
            executableFilename = compiler.compile(submission.code)
        }
        catch(e: Exception) {
            return ResultState(Result.CompileError, NO_EXECUTED_TIME, NO_SCORE)
        }

        if (executableFilename == null ||
            !File(executableFilename).exists()) {
            return ResultState(Result.CompileError, NO_EXECUTED_TIME, NO_SCORE)
        }

        val resultWithTime = execute(executableFilename, submission.testCases)
        executableFilename.deleteFile()
        return resultWithTime
    }

    private fun execute(executableFilename: String, testCases: List<TestCaseData>): ResultState {
        var isCorrect = true
        var totalExecutedTime = 0.0
        var totalScore = 0
        for (testCase in testCases) {
            var result: IExecutor.Result?
            try {
                result = executor.execute(executableFilename, testCase.input, testCase.timeOutSeconds)
            }
            catch (e: Exception) {
                return ResultState(Result.RuntimeError, NO_EXECUTED_TIME, NO_SCORE)
            }

            if (result == null) return ResultState(Result.RuntimeError, NO_EXECUTED_TIME, NO_SCORE)
            if (result.isTimeOut) return ResultState(Result.TimeLimitExceeded, NO_EXECUTED_TIME, NO_SCORE)
            if (result.isCorrupted) return ResultState(Result.RuntimeError, NO_EXECUTED_TIME, NO_SCORE)

            val output = result.output.trim()
            val expectedOutput = testCase.expectedOutput.trim()
            if (output == expectedOutput) {
                totalScore += testCase.score
            } else {
                isCorrect = false
            }

            totalExecutedTime += result.executedTime
        }

        return if (isCorrect) ResultState(Result.Accepted, totalExecutedTime, totalScore)
        else ResultState(Result.WrongAnswer, totalExecutedTime, totalScore)
    }
}