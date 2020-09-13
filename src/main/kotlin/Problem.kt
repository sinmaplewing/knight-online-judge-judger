import org.jetbrains.exposed.sql.*

object ProblemTable : Table() {
    val id = integer("ProblemId").autoIncrement().primaryKey()
}
