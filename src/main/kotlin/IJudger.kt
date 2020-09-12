interface IJudger {
    fun judge(output: String, expectedOutput: String): Boolean
}