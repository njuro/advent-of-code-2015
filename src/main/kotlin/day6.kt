import utils.Coordinate
import utils.readInputLines

/** [https://adventofcode.com/2015/day/6] */
class Lights : AdventOfCodeTask {

    private val lights = mutableMapOf<Coordinate, Int>().withDefault { 0 }

    override fun run(part2: Boolean): Any {
        lights.clear()
        val pattern = Regex("(.+) (\\d+),(\\d+) through (\\d+),(\\d+)")

        readInputLines("6.txt").forEach {
            val (command, x1, y1, x2, y2) = pattern.matchEntire(it)!!.destructured
            for (x in x1.toInt()..x2.toInt()) {
                for (y in y1.toInt()..y2.toInt()) {
                    val position = Coordinate(x, y)
                    val previous = lights.getValue(position)
                    lights[position] = when (command.trim()) {
                        "toggle" -> if (part2) previous + 2 else 1 - previous
                        "turn on" -> if (part2) previous + 1 else 1
                        "turn off" -> if (part2 && previous >= 1) previous - 1 else 0
                        else -> throw IllegalArgumentException("Unknown command $command")
                    }
                }
            }
        }

        return if (part2) lights.values.sum() else lights.values.count { it == 1 }
    }
}

fun main() {
    print(Lights().run(part2 = true))
}