import java.io.File
import kotlin.math.abs
import kotlin.math.round
import kotlin.math.roundToInt

fun main() {
    fun part1(input: List<String>): Int {
        val rope = Rope()

        input.forEach { moveData ->
            val move = moveData.split(" ")
            repeat(move[1].toInt()) {
                rope.move(Direction.valueOf(move[0]))
            }
        }

        return rope.tailPositions.size
    }

    fun part2(input: List<String>): Int {
        val rope = Rope(10)

        input.forEach { moveData ->
            val move = moveData.split(" ")
            repeat(move[1].toInt()) {
                rope.move(Direction.valueOf(move[0]))
            }
        }

        return rope.tailPositions.size
    }

    val testInput = listOf<String>(
        "R 4",
        "U 4",
        "L 3",
        "D 1",
        "R 4",
        "D 1",
        "L 5",
        "R 2",
    )
    
    val testInput2 = listOf(
        "R 5",
        "U 8",
        "L 8",
        "D 3",
        "R 17",
        "D 10",
        "L 25",
        "U 20",
    )

    // test if implementation meets criteria from the description, like:
    println(part1(testInput))
    check(part1(testInput) == 13)
    println(part2(testInput2))
    check(part2(testInput2) == 36)

    val input = File("./src/resources/Day09.txt").readLines()
    println(part1(input))
    println(part2(input))
}

class Rope(val length: Int = 2) {
    val knots = List(length) { _ -> Knot() }
    val tailPositions = mutableSetOf("0,0")

    private val head = knots[0]

    private fun isTouchingPrevious(index: Int) = abs(knots[index].x-knots[index-1].x) < 2
            && abs(knots[index].y-knots[index-1].y) < 2

    infix fun move(direction: Direction) {
        when (direction) {
            Direction.U -> head.y++
            Direction.D -> head.y--
            Direction.L -> head.x--
            Direction.R -> head.x++
        }

        (1 until knots.size).forEach { i ->
            if (!isTouchingPrevious(i)) {
                if (knots[i-1].x == knots[i].x || knots[i-1].y == knots[i].y) {
                    knots[i].x = (knots[i-1].x + knots[i].x) / 2
                    knots[i].y = (knots[i-1].y + knots[i].y) / 2
                } else {
                    knots[i].x += if (knots[i-1].x > knots[i].x) 1 else -1
                    knots[i].y += if (knots[i-1].y > knots[i].y) 1 else -1
                }
            }
        }
        tailPositions.add("${knots.last().x},${knots.last().y}")

//        println ("H -> ($headX, $headY), T -> ($tailX, $tailY)")
//        (0 .. 5).map { r ->
//            (0 .. 5).joinToString("") { c ->
//                if (headX == c && headY == r) "H" else if (tailX == c && tailY == r) "T" else "."
//            }
//        }.reversed().forEach { println(it) }
//        println()
    }
}

enum class Direction {
    U,D,L,R
}

data class Knot(var x: Int = 0, var y: Int = 0)