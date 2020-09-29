import kotlin.random.Random

object RandomStringGenerator {
    private val charPool = ('a'..'z') + ('A'..'Z') + ('0'..'9')

    fun Generate(length: Int): String =
        (0 until length)
            .map { Random.nextInt(0, charPool.size) }
            .map { charPool.get(it) }
            .joinToString("")
}