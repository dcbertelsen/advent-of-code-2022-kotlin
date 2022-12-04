import java.io.File

fun main() {
    fun stringToPair(data: String) : IntRange {
        val nums = data.split("-")
        return IntRange(nums[0].toInt(), nums[1].toInt())
    }

    fun processInput(pair: String): Pair<IntRange, IntRange> {
        val areas = pair.split(",")
            .map { stringToPair(it) }
        return Pair(areas.first(), areas.last())
    }

    fun part1(input: List<String>): Int {
        return input.map { processInput(it) }
            .count { pair ->
                pair.first.subsumes(pair.second) || pair.second.subsumes(pair.first) }
    }

    fun part2(input: List<String>): Int {
        return input.map { processInput(it) }
            .count { pair ->
                pair.first.intersect(pair.second).any() }
    }

    val testInput = listOf(
        "2-4,6-8",
        "2-3,4-5",
        "5-7,7-9",
        "2-8,3-7",
        "6-6,4-6",
        "2-6,4-8"
    )

    // test if implementation meets criteria from the description, like:
    check(part1(testInput) == 2)
    check(part2(testInput) == 4)

    val input = File("./src/resources/Day04.txt").readLines()
    println(part1(input))
    println(part2(input))
}

fun IntRange.subsumes(other: IntRange): Boolean =
    contains(other.first) && contains(other.last)
