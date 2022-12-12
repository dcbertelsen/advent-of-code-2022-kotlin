import java.io.File

fun main() {
    fun sightAlongAndMark(trees: List<Tree>) {
        var max = -1
        trees.forEach { t -> if (t.height > max) { t.canSee(); max = t.height }}
    }

    fun getVisibleTrees(trees: List<Tree>): Int {
        var count = 0
        var max = 0
        for (tree in trees.subList(1, trees.size)) {
            count++
            max = maxOf(max, tree.height)
            if (tree.height >= trees[0].height) {
                break
            }
        }
        return count
    }

    fun <T> getColumns(ts: List<List<T>>): List<List<T>> {
        return (0 until ts[0].size).map { i ->
            ts.map { r -> r[i] }.toList()
        }.toList()
    }

    fun getDirectionLists(trees: List<List<Tree>>, row: Int, col: Int): List<List<Tree>> {
        val directions = mutableListOf<List<Tree>>()

        directions.add(trees[row].subList(0, col+1).reversed()) // left
        directions.add(trees[row].subList(col, trees[row].size)) // right

        val column = getColumns(trees)[col]

        directions.add(column.subList(0, row+1).reversed()) // top
        directions.add(column.subList(row, column.size)) // bottom

        return directions.toList()
    }

    fun part1(input: List<String>): Int {
        val forest = input.map { r -> r.map { Tree(it.digitToInt()) }}

        forest.forEach { sightAlongAndMark(it) }
        forest.forEach { sightAlongAndMark(it.reversed()) }

        val columns = getColumns(forest)

        columns.forEach { sightAlongAndMark(it) }
        columns.forEach { sightAlongAndMark(it.reversed()) }

//        forest.forEach { r -> println(r.joinToString("") { t -> "${t}" }) }
        println()

        return forest.flatten().count { it.isVisible }
    }

    fun part2(input: List<String>): Int {
        val forest = input.map { r -> r.map { Tree(it.digitToInt()) }}

        forest.forEachIndexed { i, row -> row.forEachIndexed { j, tree ->
            val lists = getDirectionLists(forest, i, j)
            tree.scenicScore = lists.map {
                getVisibleTrees(it)
            }.fold(1) { acc, it -> it * acc }
        } }

//        forest.forEach { r -> println(r.joinToString(" ") { t -> "${t.scenicScore}" }) }
        println()

        return forest.flatten().maxOf { it.scenicScore }
    }

    val testInput = listOf<String>(
        "30373",
        "25512",
        "65332",
        "33549",
        "35390"
    )

    // test if implementation meets criteria from the description, like:
    check(part1(testInput) == 21)
    check(part2(testInput) == 8)

    val input = File("./src/resources/Day08.txt").readLines()
    println(part1(input))
    println(part2(input))
}

class Tree(val height: Int, var isVisible: Boolean = false) {
    fun canSee() { isVisible = true }
    override fun toString(): String = if (isVisible) "*" else " "
    var scenicScore = -1
}
