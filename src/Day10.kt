import java.io.File
import java.util.*
import kotlin.math.abs

fun main() {
    fun part1(input: List<String>): Int {
        val machine = Machine()
        var signal = 0

        machine.load(input)

        while (machine.state != Machine.State.HALTED) {
            machine.tick()
            println("${machine.cycle} - X:${machine.x} (${machine.state})")
            if (machine.cycle % 40 == 20) {
                val s = machine.cycle * machine.x
                println("*** ${machine.cycle} - X:${machine.x} (${machine.state}) - Signal: ${machine.x * machine.cycle}")
                signal += s
            }
        }
        println(signal)
        return signal
    }

    fun part2(input: List<String>): Int {
        val machine = Machine()

        machine.load(input)

        while (machine.state != Machine.State.HALTED) {
            print(if (abs((machine.x % 40) - (machine.cycle % 40)) < 2) "#" else ".")
            machine.tick()
            if (machine.cycle % 40 == 0)
                println()
        }
        return 42
    }

    // test if implementation meets criteria from the description, like:
//    check(part1(testInput) == 13140)
    check(part2(testInput) == 42)

    val input = File("./src/resources/Day10.txt").readLines()
    println(part1(input))
    println(part2(input))
    // ZFBFHGUP
}

class Machine {
    var x = 1
        private set
    var cycle = 0
        private set
    var state = State.IDLE
        private set
    private var cache = 0
    private val program = Stack<String>()

    fun load(program: List<String>) {
        this.program.addAll(program.reversed())
    }

    fun tick() {
        cycle++
        when (state) {
            State.IDLE -> {
                if (program.empty()) {
                    state = State.HALTED
                } else {
                    val command = program.pop()
                    if (command.startsWith("addx")) {
                        cache = command.split(" ")[1].toInt()
                        state = State.ADDING
                    }
                }
            }
            State.ADDING -> {
                x += cache
                cache = 0
                state = State.IDLE
            }
            State.HALTED -> {}
        }
    }

    enum class State {
        HALTED, IDLE, ADDING
    }
}


    val testInput = listOf(
        "addx 15",
        "addx -11",
        "addx 6",
        "addx -3",
        "addx 5",
        "addx -1",
        "addx -8",
        "addx 13",
        "addx 4",
        "noop",
        "addx -1",
        "addx 5",
        "addx -1",
        "addx 5",
        "addx -1",
        "addx 5",
        "addx -1",
        "addx 5",
        "addx -1",
        "addx -35",
        "addx 1",
        "addx 24",
        "addx -19",
        "addx 1",
        "addx 16",
        "addx -11",
        "noop",
        "noop",
        "addx 21",
        "addx -15",
        "noop",
        "noop",
        "addx -3",
        "addx 9",
        "addx 1",
        "addx -3",
        "addx 8",
        "addx 1",
        "addx 5",
        "noop",
        "noop",
        "noop",
        "noop",
        "noop",
        "addx -36",
        "noop",
        "addx 1",
        "addx 7",
        "noop",
        "noop",
        "noop",
        "addx 2",
        "addx 6",
        "noop",
        "noop",
        "noop",
        "noop",
        "noop",
        "addx 1",
        "noop",
        "noop",
        "addx 7",
        "addx 1",
        "noop",
        "addx -13",
        "addx 13",
        "addx 7",
        "noop",
        "addx 1",
        "addx -33",
        "noop",
        "noop",
        "noop",
        "addx 2",
        "noop",
        "noop",
        "noop",
        "addx 8",
        "noop",
        "addx -1",
        "addx 2",
        "addx 1",
        "noop",
        "addx 17",
        "addx -9",
        "addx 1",
        "addx 1",
        "addx -3",
        "addx 11",
        "noop",
        "noop",
        "addx 1",
        "noop",
        "addx 1",
        "noop",
        "noop",
        "addx -13",
        "addx -19",
        "addx 1",
        "addx 3",
        "addx 26",
        "addx -30",
        "addx 12",
        "addx -1",
        "addx 3",
        "addx 1",
        "noop",
        "noop",
        "noop",
        "addx -9",
        "addx 18",
        "addx 1",
        "addx 2",
        "noop",
        "noop",
        "addx 9",
        "noop",
        "noop",
        "noop",
        "addx -1",
        "addx 2",
        "addx -37",
        "addx 1",
        "addx 3",
        "noop",
        "addx 15",
        "addx -21",
        "addx 22",
        "addx -6",
        "addx 1",
        "noop",
        "addx 2",
        "addx 1",
        "noop",
        "addx -10",
        "noop",
        "noop",
        "addx 20",
        "addx 1",
        "addx 2",
        "addx 2",
        "addx -6",
        "addx -11",
        "noop",
        "noop",
        "noop",
    )
