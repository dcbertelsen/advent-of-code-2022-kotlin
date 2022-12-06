import java.io.File

fun main() {
    fun String.firstUniqueSequence(len: Int): Int =
        this.toList().windowed(len) { cs -> cs.toSet() }.indexOfFirst { it.size == len } + len

    fun part1(input: String): Int {
        return input.firstUniqueSequence(4)
    }

    fun part2(input: String): Int {
        return input.firstUniqueSequence(14)
    }

    val testInput = listOf<String>(
        "mjqjpqmgbljsphdztnvjfqwrcgsmlb",
        "bvwbjplbgvbhsrlpgdmjqwftvncz",
        "nppdvjthqldpwncqszvftbrmjlhg",
        "nznrnfrfntjfmvfwmzdfjlvtqnbhcprsg",
        "zcfzfwzzqfrljwzlrfnpqdbhtmscgvjw"
    )

    // test if implementation meets criteria from the description, like:
//    check(part1(testInput) == 42)
    println(testInput.map { part1(it) })
    println(testInput.map { part2(it) })
//    check(part2(testInput) == 42)

    val input = File("./src/resources/Day06.txt").readText()
    println(part1(input))
    println(part2(input))
}
