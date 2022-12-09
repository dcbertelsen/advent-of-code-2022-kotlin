import java.io.File
import java.util.Dictionary

fun main() {
    fun buildFilesystem(data: List<String>): Directory {
        val root = Directory("/", null)
        var cwd = root

        data.forEachIndexed { i, line ->
            val curr = line.split(" ")
            when {
                curr[1] == "cd" -> cwd = if (curr[2] == "/") root
                                        else if (curr[2] == "..") cwd.parent ?: cwd
                                        else cwd.contents.firstOrNull { it.name == curr[2] } as? Directory ?: Directory(curr[2], cwd).also { cwd.contents.add(it) }
                curr[0] == "dir" -> cwd.contents.firstOrNull { it.name == curr[1] } ?: cwd.contents.add(Directory(curr[1], cwd))
                curr[1] == "ls" -> {}
                curr[0].matches(Regex("\\d+")) -> cwd.contents.firstOrNull { it.name == curr[1] } ?: cwd.contents.add(Data(curr[1], curr[0].toInt(), cwd))
            }
        }
        return root
    }

    fun part1(input: List<String>): Int {
        val fs = buildFilesystem(input)
//        fs.filetree().forEach { println(it) }

        val bigdirs = fs.listAll().filter { it is Directory && it.size <= 100000 }
        return bigdirs.sumOf { it.size }
    }

    fun part2(input: List<String>): Int {
        val fs = buildFilesystem(input)

        val dirs = fs.listAll().filterIsInstance<Directory>()
        val target = fs.size - 4e7
        return dirs.filter { it.size >= target }.minBy { it.size }.size
    }

    val testInput = listOf<String>(
        "$ cd /",
        "$ ls",
        "dir a",
        "14848514 b.txt",
        "8504156 c.dat",
        "dir d",
        "$ cd a",
        "$ ls",
        "dir e",
        "29116 f",
        "2557 g",
        "62596 h.lst",
        "$ cd e",
        "$ ls",
        "584 i",
        "$ cd ..",
        "$ cd ..",
        "$ cd d",
        "$ ls",
        "4060174 j",
        "8033020 d.log",
        "5626152 d.ext",
        "7214296 k",
    )

    // test if implementation meets criteria from the description, like:
    check(part1(testInput) == 95437)
//    check(part2(testInput) == 42)

    val input = File("./src/resources/Day07.txt").readLines()
    println(part1(input))
    println(part2(input))
}

sealed class FsNode(val name: String, val parent: Directory?) {
    abstract val size: Int
    abstract fun listAll() : List<FsNode>
}

class Directory(
    name: String,
    parent: Directory?,
    val contents: MutableList<FsNode> = mutableListOf()
) : FsNode(name, parent) {
    override val size
        get() = contents.sumOf { it.size }
    override fun toString(): String {
        return "- $name (dir, size = $size)" //\n  " + contents.sortedBy { it.name }.joinToString("\n  ")
    }

    fun filetree() : List<String> {
        return listOf("$this") + contents.flatMap { (it as? Directory)?.filetree() ?: listOf("$it") }.map { "  $it" }
    }

    override fun listAll() : List<FsNode> = listOf(this) + contents.flatMap { it.listAll() }
}

class Data(name: String, override val size: Int, parent: Directory) : FsNode(name, parent) {
    override fun toString(): String {
        return "- $name (file, size = $size)"
    }

    override fun listAll() : List<FsNode> = listOf(this)
}
