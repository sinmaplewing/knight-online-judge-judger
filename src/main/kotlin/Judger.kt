import java.io.File
import java.lang.Exception

class Judger(val compiler: ICompiler, val executor: IExecutor) {
    enum class Result { Accepted, WrongAnswer, CompileError, RuntimeError, TimeLimitExceeded }

    fun judge(submission: SubmissionData): Result {
        var executableFilename: String? = null

        try {
            executableFilename = compiler.compile(submission.code)
        }
        catch(e: Exception) {
            return Result.CompileError
        }

        if (executableFilename == null ||
            !File(executableFilename).exists()) {
            return Result.CompileError
        }

        var isCorrect = true
        for (testCase in submission.testCases) {
            var result: IExecutor.Result?
            try {
                result = executor.execute(executableFilename, testCase.input, testCase.timeOutSeconds)
            }
            catch (e: Exception) {
                executableFilename.deleteFile()
                return Result.RuntimeError
            }
            if (result == null) {
                executableFilename.deleteFile()
                return Result.RuntimeError
            }
            if (result.isTimeOut) {
                executableFilename.deleteFile()
                return Result.TimeLimitExceeded
            }
            if (result.isCorrupted) {
                executableFilename.deleteFile()
                return Result.RuntimeError
            }

            val output = result.output.trim()
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