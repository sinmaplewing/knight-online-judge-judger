fun main() {
    val submissionSource: ISubmissionSource = FileSubmissionSource()
    val judger = Judger(KotlinCompiler(), JVMExecutor())

    var submission = submissionSource.getNextSubmissionData()
    while (submission != null) {
        submissionSource.setResult(submission.id, judger.judge(submission))
        submission = submissionSource.getNextSubmissionData()
    }
}