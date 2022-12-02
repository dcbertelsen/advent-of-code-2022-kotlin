fun main() {
    fun part1(input: List<String>): Int {
        return input.map{
            it.split("\n")
        }
            .map {
                it.sumOf { v -> v.toInt() }
            }
            .maxOf { it }
    }

    fun part2(input: List<String>): Int {
        return input.map{
                it.split("\n")
            }
            .map {
                it.sumOf { v -> v.toInt() }
            }
            .sorted()
            .takeLast(3)
            .sum()
    }

    // test if implementation meets criteria from the description, like:
    // val testInput = readInput("Day01_test")
    // check(part1(testInput) == 1)

    val input = File("./src/main/resources/twentytwo/Day01a.txt").readText().split("\n\n")
    println(part1(input))
    println(part2(input))
}
