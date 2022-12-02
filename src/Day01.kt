import java.io.File

fun main() {
    fun getInts(list: List<String>): List<Int> =
        list.map {
            it.split("\n")
        }
            .map {
                it.sumOf { v -> v.toInt() }
            }

    fun part1(input: List<String>): Int {
        return getInts(input)
            .maxOf { it }
    }

    fun part2(input: List<String>): Int {
        return getInts(input)
            .sorted()
            .takeLast(3)
            .sum()
    }

    // test if implementation meets criteria from the description, like:
    // val testInput = readInput("Day01_test")
    // check(part1(testInput) == 1)

    val input = File("./src/resources/Day01a.txt").readText().split("\n\n")
    println(part1(input))
    println(part2(input))
}