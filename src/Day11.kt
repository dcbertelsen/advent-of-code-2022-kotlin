import java.io.File
import kotlin.collections.ArrayDeque

fun main() {
    fun readMonkeys(input: List<String>) =
        input.map { data ->
            val lines = data.split("\n")
            Monkey(
                ArrayDeque(lines[1].split(": ")[1].split(", ").map { it -> it.toLong() }),
                lines[2].split("=")[1].toFunction(),
                lines[3].split(" ").last().toInt(),
                lines[4].split(" ").last().toInt(),
                lines[5].split(" ").last().toInt()
            )
        }

    fun part1(input: List<String>): Int {
        val monkeys = readMonkeys(input)

        repeat(20) {
            monkeys.forEach { monkey ->
                while (monkey.hasItems()) {
                    val (newItem, target) = monkey.inspectNextPt1()
                    monkeys[target].catch(newItem)
                }
            }
        }

        return monkeys.sortedBy { it.inspectionCount }.takeLast(2).fold(1) { acc, m -> acc * m.inspectionCount}
    }

    fun part2(input: List<String>): Long {
        val monkeys = readMonkeys(input)

        val checkProduct = monkeys.fold(1) { acc, m -> acc * m.testDiv }

        repeat(10000) {
            monkeys.forEach { monkey ->
                while (monkey.hasItems()) {
                    val (newItem, target) = monkey.inspectNextPt2()
                    monkeys[target].catch(newItem % checkProduct)
                }
            }
        }

        val topTwo = monkeys.map { it.inspectionCount }.sorted().takeLast(2)
        return 1L * topTwo[0] * topTwo[1]
    }

    val testInput =
"""Monkey 0:
  Starting items: 79, 98
  Operation: new = old * 19
  Test: divisible by 23
    If true: throw to monkey 2
    If false: throw to monkey 3

Monkey 1:
  Starting items: 54, 65, 75, 74
  Operation: new = old + 6
  Test: divisible by 19
    If true: throw to monkey 2
    If false: throw to monkey 0

Monkey 2:
  Starting items: 79, 60, 97
  Operation: new = old * old
  Test: divisible by 13
    If true: throw to monkey 1
    If false: throw to monkey 3

Monkey 3:
  Starting items: 74
  Operation: new = old + 3
  Test: divisible by 17
    If true: throw to monkey 0
    If false: throw to monkey 1
""".split("\n\n")

    // test if implementation meets criteria from the description, like:
    check(part1(testInput) == 10605)
    check(part2(testInput) == 2713310158L)

    val input = File("./src/resources/Day11.txt").readText().split("\n\n")
    println(part1(input))
    println(part2(input))
}

class Monkey(val items: ArrayDeque<Long>, val op: (Long) -> Long, val testDiv: Int, val ifTrue: Int, val ifFalse: Int) {
    var inspectionCount = 0
        private set

    fun catch(item: Long) {
        items.add(item)
    }

    fun hasItems() = items.any()

    private fun inspectNext(divisor: Int): Pair<Long, Int> {
        inspectionCount++
        val newWorry = op(items.removeFirst()) / divisor
        return newWorry to if (newWorry % testDiv == 0L ) ifTrue else ifFalse
    }

    fun inspectNextPt1(): Pair<Long, Int> = inspectNext(3)
    fun inspectNextPt2(): Pair<Long, Int> = inspectNext(1)
}

fun String.toFunction(): (Long) -> Long {
    if (this.trim() == "old * old")
        return { old -> old * old }

    val tokens = this.trim().split(" ")

    if (tokens[1] == "+")
        return { old: Long -> old + tokens[2].toInt() }

    if (tokens[1] == "*")
        return { old: Long -> old * tokens[2].toInt() }

    return { old: Long -> old * tokens[2].toInt() }
}
