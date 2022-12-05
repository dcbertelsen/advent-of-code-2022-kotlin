import java.io.File

fun main() {
    fun dataToParts(input: String): Pair<String, String> {
        val parts = input.split("\n\n")
        return parts[0] to parts[1]
    }

    fun initStacks(input: String): Map<Int, MutableList<Char>>{
        val lines = input.split("\n").reversed()
        val stacks = lines[0].filter { it != ' ' }.map { c -> c.digitToInt() to mutableListOf<Char>() }.toMap()
        lines.drop(1).forEach { line -> line.chunked(4).forEachIndexed { i, s ->
            if (s[1] != ' ') stacks[i+1]?.add(s[1])
        } }
        return stacks
    }

    fun readMoves(input: String): List<Triple<Int, Int, Int>> {
        return input.lines().map { line -> line.split(" ")}
            .map { Triple(it[1].toInt(), it[3].toInt(), it[5].toInt())}
    }

    fun part1(input: String): String {
        val (stackData, moveData) = dataToParts(input)
        val stacks = initStacks(stackData)
        val moves = readMoves(moveData)

        moves.forEach { move ->
            repeat (move.first) {
                stacks[move.third]?.add(stacks[move.second]?.removeLast() ?: ' ')
            }
        }
        return stacks.map {
            it.value.last() }.joinToString("")
    }

    fun part2(input: String): String {
        val (stackData, moveData) = dataToParts(input)
        val stacks = initStacks(stackData)
        val moves = readMoves(moveData)
        moves.forEach { move ->
            val crates = stacks[move.second]?.takeLast(move.first) ?: listOf()
            repeat(move.first) { stacks[move.second]?.removeLast() }
            stacks[move.third]?.addAll(crates)
        }
        return stacks.map {
            it.value.last() }.joinToString("")
    }

    val testInput = listOf<String>(
        "    [D]",
        "[N] [C]",
        "[Z] [M] [P]",
        " 1   2   3",
        "",
        "move 1 from 2 to 1",
        "move 3 from 1 to 3",
        "move 2 from 2 to 1",
        "move 1 from 1 to 2"
    ).joinToString("\n")

    // test if implementation meets criteria from the description, like:
    val test = part1(testInput)
    println(test)
    check(test == "CMZ")
//    check(part2(testInput) == 42)

    val input = File("./src/resources/Day05.txt").readText()
    println(part1(input))
    println(part2(input))
}
