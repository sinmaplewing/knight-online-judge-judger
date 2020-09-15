fun main() {
    val submissionSource: ISubmissionSource = DatabaseSubmissionSource // FileSubmissionSource()

    while (true) {
        var submission = submissionSource.getNextSubmissionData()
        while (submission != null) {
            val judger = Judger(KotlinCompiler(), JVMExecutor())

            val resultState = judger.judge(submission)
            submissionSource.setResult(submission.id, resultState.result, resultState.executedTime, resultState.totalScore)
            submission = submissionSource.getNextSubmissionData()
        }

        Thread.sleep(5000)
    }
}