import java.io.File

const val FILE_SUBMISSION_CODE_FILENAME = "src/main/resources/file/code.txt"
const val FILE_SUBMISSION_INPUT_FILENAME = "src/main/resources/file/input.txt"
const val FILE_SUBMISSION_OUTPUT_FILENAME = "src/main/resources/file/output.txt"

class FileSubmissionSource: ISubmissionSource {
    var isGet = false

    override fun getNextSubmissionData(): SubmissionData? {
        if (isGet) return null

        val codeFile = File(FILE_SUBMISSION_CODE_FILENAME)
        val inputFile = File(FILE_SUBMISSION_INPUT_FILENAME)
        val outputFile = File(FILE_SUBMISSION_OUTPUT_FILENAME)

        isGet = true
        return SubmissionData(
            1,
            "kotlin",
            codeFile.readText(),
            listOf(TestCaseData(
                inputFile.readText(),
                outputFile.readText(),
                100,
                10.0
            ))
        )
    }

    override fun setResult(id: Int, result: Judger.Result, executedTime: Double, score: Int) {
        println("Submission $id: $result - Score: $score ($executedTime)")
    }
}