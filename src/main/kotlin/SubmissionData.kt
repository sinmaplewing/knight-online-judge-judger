data class SubmissionData (
    val id: Int,
    val langauge: String,
    val code: String,
    val testCases: List<TestCaseData>
)

data class TestCaseData (
   val input: String,
   val expectedOutput: String,
   val score: Int,
   val timeOutSeconds: Double
)