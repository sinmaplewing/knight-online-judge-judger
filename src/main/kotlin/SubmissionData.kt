data class SubmissionData (
    val id: Int,
    val lang: String,
    val code: String,
    val testCases: List<TestCaseData>
)

data class TestCaseData (
   val input: String,
   val expectedOutput: String,
   val timeOutSeconds: Double
)