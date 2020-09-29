data class SubmissionData (
    val id: Int,
    val language: String,
    val code: String,
    val testCases: List<TestCaseData>
)

data class TestCaseData (
   val input: String,
   val expectedOutput: String,
   val score: Int,
   val timeOutSeconds: Double
)