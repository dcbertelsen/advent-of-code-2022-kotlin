import java.io.File
import kotlin.math.min

fun main() {
    val c0 = '`'
    fun getNodes(input: List<String>): List<List<Node>> =
        input.map { row ->
            row.map { c ->
                val height = when (c) { 'S' -> 0; 'E' -> 'z' - c0; else -> c - c0 }
                Node(height, isStart = c == 'S', isEnd = c == 'E')
            }
        }

    fun buildNeighbors(nodes: List<List<Node>>) {
        nodes.forEachIndexed { y, row ->
            row.forEachIndexed { x, node ->
                listOf(x to y-1, x to y+1, x+1 to y, x-1 to y).forEach {
                    if (it.first in row.indices && it.second in nodes.indices && nodes[it.second][it.first].height <= node.height + 1)
                        node.neighbors.add(nodes[it.second][it.first])
                }
            }
        }
    }

    fun dijkstra(data: MutableList<Node>) {
        while(data.any()) {
            val v = data.removeFirst()
            v.neighbors.forEach {
                if (v.distance + 1 < it.distance) {
                    it.distance = v.distance + 1
                    data.add(it)
                }
                data.sortBy { n -> n.distance }
            }
        }
    }

    fun part1(input: List<String>): Int {
        val nodes = getNodes(input)
        val start = nodes.flatten().first { it.isStart }

        buildNeighbors(nodes)

        start.distance = 0
        val toProcess = mutableListOf(start)

        dijkstra(toProcess)

//        nodes.forEach {row ->
//            row.forEach { print(if (it.distance == Int.MAX_VALUE) "  . " else String.format("%3d", it.distance) + if (it.isEnd) "*" else " ")}
//            println()
//        }
//        println()

        return nodes.flatten().firstOrNull { it.isEnd }?.distance ?: 0
    }

    fun part2(input: List<String>): Int {
        val nodes = getNodes(input)
        val start = nodes.flatten().first { it.isStart }
        val end = nodes.flatten().first { it.isEnd }

        buildNeighbors(nodes)

        start.distance = 0
        val toProcess = mutableListOf(start)

        dijkstra(toProcess)

        val starts = nodes.flatten().filter { it.height == 'a' - c0 && it.distance < Int.MAX_VALUE }.sortedByDescending { it.distance }.toMutableList()

        var shortest = Int.MAX_VALUE

        starts.forEach { newStart ->
            nodes.flatten().forEach { it.distance = Int.MAX_VALUE }
            newStart.distance = 0
            toProcess.add(newStart)

            dijkstra(toProcess)

            shortest = min(shortest, end.distance)
        }

        return shortest
    }

    val testInput = listOf(
        "Sabqponm",
        "abcryxxl",
        "accszExk",
        "acctuvwj",
        "abdefghi"
    )

    // test if implementation meets criteria from the description, like:
    check(part1(testInput) == 31)
    check(part2(testInput) == 29)

    val input = File("./src/resources/Day12.txt").readLines()
    println(part1(input))
    println(part2(input))
}

data class Node(
    val height: Int,
    var distance: Int = Int.MAX_VALUE,
    val neighbors: MutableList<Node> = mutableListOf(),
    val isStart: Boolean = false,
    val isEnd: Boolean = false)
