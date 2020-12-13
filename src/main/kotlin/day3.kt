import utils.Coordinate
import utils.readInputBlock

/** [https://adventofcode.com/2015/day/3] */
class Navigation : AdventOfCodeTask {
    override fun run(part2: Boolean): Any {
        var santa = Coordinate(0, 0)
        var robot = santa.copy()
        val visited = mutableSetOf(santa)
        readInputBlock("3.txt").forEachIndexed { index, command ->
            if (part2 && index % 2 == 1) robot = robot.move(command) else santa = santa.move(command)
            visited.add(santa)
            visited.add(robot)
        }

        return visited.size
    }

    private fun Coordinate.move(command: Char) = when (command) {
        '^' -> up()
        'v' -> down()
        '<' -> left()
        '>' -> right()
        else -> throw IllegalArgumentException("Unknown command $command")
    }
}

fun main() {
    print(Navigation().run(part2 = true))
}