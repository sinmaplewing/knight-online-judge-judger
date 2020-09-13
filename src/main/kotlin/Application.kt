fun main() {
    val submissionSource: ISubmissionSource = FileSubmissionSource()

    var submission = submissionSource.getNextSubmissionData()
    while (submission != null) {
        val judger = Judger(KotlinCompiler(), JVMExecutor())

        val result = judger.judge(submission)
        submissionSource.setResult(submission.id, result)
        submission = submissionSource.getNextSubmissionData()
    }
}