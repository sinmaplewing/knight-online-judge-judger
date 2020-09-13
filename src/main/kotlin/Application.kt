const val DOCKER_WORKSPACE = "workspace"

fun main() {
    val submissionSource: ISubmissionSource = DatabaseSubmissionSource() // FileSubmissionSource()

    while (true) {
        var submission = submissionSource.getNextSubmissionData()
        while (submission != null) {
            val judger = Judger(KotlinCompiler(DOCKER_WORKSPACE), JVMExecutor(DOCKER_WORKSPACE))

            val resultWithTime = judger.judge(submission)
            submissionSource.setResult(submission.id, resultWithTime.result, resultWithTime.executedTime)
            submission = submissionSource.getNextSubmissionData()
        }

        Thread.sleep(1000)
    }
}