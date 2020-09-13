import org.jetbrains.exposed.sql.Table

object SubmissionTable: Table() {
    val id = integer("SubmissionId").autoIncrement().primaryKey()
    val language = varchar("Language", 255)
    val code = text("Code")
    val executedTime = double("ExecutedTime")
    val result = varchar("Result", 255)

    val problemId = integer("ProblemId") references ProblemTable.id
    val userId = integer("UserId") references UserTable.id
}