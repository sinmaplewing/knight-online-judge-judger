import com.fasterxml.jackson.module.kotlin.*
import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.transaction
import org.jetbrains.exposed.sql.update
import redis.clients.jedis.Jedis

const val SUPPORTED_LANGUAGE = "kotlin"

object DatabaseSubmissionSource: ISubmissionSource {
    val jedis: Jedis

    init {
        val config = HikariConfig("/hikari.properties")
        config.schema = "public"
        val dataSource = HikariDataSource(config)
        Database.connect(dataSource)

        transaction {
            SchemaUtils.create(ProblemTable, TestCaseTable, SubmissionTable)
        }

        jedis = Jedis()
    }

    override fun getNextSubmissionData(): SubmissionData? {
        val isDataAvailable = jedis.exists(SUPPORTED_LANGUAGE)
        if (!isDataAvailable) return null

        val data = jedis.lpop(SUPPORTED_LANGUAGE)
        return jacksonObjectMapper().readValue(data)
    }

    override fun setResult(id: Int, result: Judger.Result, executedTime: Double, score: Int) {
        transaction {
            SubmissionTable.update({
                SubmissionTable.id.eq(id)
            }) {
                it[SubmissionTable.result] = "$result ($score)"
                it[SubmissionTable.executedTime] = executedTime
            }
        }

        println("Submission $id: $result - Score: $score ($executedTime)")
    }
}