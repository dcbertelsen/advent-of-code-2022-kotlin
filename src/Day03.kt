import java.io.File

fun main() {
    fun part1(input: List<String>): Int {
        return input.map { sack -> sack.bisect() }
            .map { parts -> parts.first.intersect(parts.second).first() }
            .sumOf { it.toScore() }
    }

    fun part2(input: List<String>): Int {
        return input.chunked(3)
            .map { sacks ->
                sacks[0].intersect(sacks[1]).toString().intersect(sacks[2]).firstOrNull()
            }
            .sumOf { it?.toScore() ?: 0 }
    }

    val testInput = listOf<String>(
        "vJrwpWtwJgWrhcsFMMfFFhFp",
        "jqHRNqRjqzjGDLGLrsFMfFZSrLrFZsSL",
        "PmmdzqPrVvPwwTWBwg",
        "wMqvLMZHhHMvwLHjbvcjnnSBnvTQFn",
        "ttgJtRGJQctTZtZT",
        "CrZsJsPPZsGzwwsLwLmpwMDw"
    )

    // test if implementation meets criteria from the description, like:
    println(part1(testInput))
    check(part1(testInput) == 157)
    check(part2(testInput) == 70)

    val input = File("./src/resources/Day03a.txt").readLines()
    println(part1(input))
    println(part2(input))
}

fun Char.toScore() : Int =
    (if (this.isLowerCase()) this - 'a' else this - 'A' + 26) + 1

fun CharSequence.intersect(list2: CharSequence) : List<Char> {
    return this.filter { list2.contains(it) }.toList()
}

fun String.bisect() : Pair<String, String> =
    Pair(this.substring(0, this.length/2), this.substring(this.length/2, this.length))
