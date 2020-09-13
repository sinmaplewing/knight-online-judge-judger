fun main() {
    val submissionSource: ISubmissionSource = DatabaseSubmissionSource() // FileSubmissionSource()

    while (true) {
        var submission = submissionSource.getNextSubmissionData()
        while (submission != null) {
            val judger = Judger(KotlinCompiler(), JVMExecutor())

            val result = judger.judge(submission)
            submissionSource.setResult(submission.id, result)
            submission = submissionSource.getNextSubmissionData()
        }

        Thread.sleep(1000)
    }
}