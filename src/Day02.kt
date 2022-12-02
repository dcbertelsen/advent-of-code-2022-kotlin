import java.io.File

fun main() {
    fun part1(input: List<String>): Int {
        return input.sumOf { getScore(it) }
    }

    fun part2(input: List<String>): Int {
        return input.sumOf { getScore(getGame(it)) }
    }

    val testInput = listOf(
        "A Y",
        "B X",
        "C Z"
    )

    // test if implementation meets criteria from the description, like:
    check(part1(testInput) == 15)
    check(part2(testInput) == 12)

    val input = File("./src/resources/Day02a.txt").readLines()
    println(part1(input))
    println(part2(input))
}

fun getScore(game: String) : Int =
    game[2] - 'W' +
            when {
                game[0] == (game[2] - 23) -> 3
                wins.contains(game) -> 6
                else -> 0
            }

fun getGame(code: String) : String =
    when (code[2]) {
        'Y' -> ties
        'X' -> losses
        'Z' -> wins
        else -> listOf()
    }.first { code[0] == it[0] }

val wins = listOf("A Y", "B Z", "C X")
val losses = listOf("A Z", "B X", "C Y")
val ties = listOf("A X", "B Y", "C Z")


