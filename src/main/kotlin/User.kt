import org.jetbrains.exposed.sql.Table

object UserTable: Table() {
    val id = integer("UserId").autoIncrement().primaryKey()
}