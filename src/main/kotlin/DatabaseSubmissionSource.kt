import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.transactions.transaction
import org.jetbrains.exposed.sql.update

object DatabaseSubmissionSource: ISubmissionSource {
    init {
        val config = HikariConfig("/hikari.properties")
        config.schema = "public"
        val dataSource = HikariDataSource(config)
        Database.connect(dataSource)

        transaction {
            SchemaUtils.create(ProblemTable, TestCaseTable,  SubmissionTable)
        }
    }

    override fun getNextSubmissionData(): SubmissionData? {
        var submissionData: SubmissionData? = null
        transaction {
            val submission = SubmissionTable.select {
                SubmissionTable.result.eq("-")
            }.firstOrNull()

            if (submission != null) {
                val testCases = TestCaseTable.select {
                    TestCaseTable.problemId.eq(submission[SubmissionTable.problemId])
                }.map {
                    TestCaseData(
                        it[TestCaseTable.input],
                        it[TestCaseTable.expectedOutput],
                        it[TestCaseTable.timeOutSeconds]
                    )
                }

                submissionData = SubmissionData(
                    submission[SubmissionTable.id],
                    submission[SubmissionTable.language],
                    submission[SubmissionTable.code],
                    testCases
                )
            }
        }

        return submissionData
    }

    override fun setResult(id: Int, result: Judger.Result) {
        transaction {
            SubmissionTable.update({
                SubmissionTable.id.eq(id)
            }) {
                it[SubmissionTable.result] = result.toString()
            }
        }

        println("Submission $id: $result")
    }
}