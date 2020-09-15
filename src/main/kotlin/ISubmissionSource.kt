interface ISubmissionSource {
    fun getNextSubmissionData(): SubmissionData?
    fun setResult(id: Int, result: Judger.Result, executedTime: Double, score: Int)
}