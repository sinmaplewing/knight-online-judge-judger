const val DOCKER_WORKSPACE = "workspace"

fun main() {
    val submissionSource: ISubmissionSource = DatabaseSubmissionSource // FileSubmissionSource()

    while (true) {
        var submission = submissionSource.getNextSubmissionData()
        while (submission != null) {
            val judger = getJudger(submission.language)

            val resultState = judger.judge(submission)
            submissionSource.setResult(submission.id, resultState.result, resultState.executedTime, resultState.totalScore)
            submission = submissionSource.getNextSubmissionData()
        }

        Thread.sleep(5000)
    }
}

fun getJudger(language: String): Judger =
    when(language) {
        "kotlin" -> Judger(KotlinCompiler(DOCKER_WORKSPACE), JVMExecutor(DOCKER_WORKSPACE))
        "c" -> Judger(GCCCompiler(DOCKER_WORKSPACE), GCCExecutor(DOCKER_WORKSPACE))
        "java" -> Judger(JavaCompiler(DOCKER_WORKSPACE), JVMExecutor(DOCKER_WORKSPACE))
        "python" -> Judger(PassThroughCompiler(DOCKER_WORKSPACE), PythonExecutor(DOCKER_WORKSPACE))
        else -> throw NotImplementedError()
    }