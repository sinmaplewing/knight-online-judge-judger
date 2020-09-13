import org.jetbrains.exposed.sql.Table

object TestCaseTable : Table() {
    val id = integer("TestCaseId").autoIncrement().primaryKey()
    val input = text("TestInput")
    val expectedOutput = text("ExpectedOutput")
    val timeOutSeconds = double("TimeOutSeconds")

    val problemId = integer("ProblemId") references ProblemTable.id
}