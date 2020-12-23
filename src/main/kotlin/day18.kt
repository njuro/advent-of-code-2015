import utils.Coordinate
import utils.readInputLines

/** [https://adventofcode.com/2015/day/18] */
class AnimatedLights : AdventOfCodeTask {
    override fun run(part2: Boolean): Any {
        var lights = readInputLines("18.txt").flatMapIndexed { y, row ->
            row.mapIndexed { x, c ->
                Coordinate(x, y) to c
            }
        }.toMap()

        val corners = setOf(Coordinate(0, 0), Coordinate(0, 99), Coordinate(99, 0), Coordinate(99, 99))
        val neighbours = lights.keys.associateWith { it.neighbours8() }

        repeat(100) {
            val updated = lights.toMutableMap()

            lights.filterKeys { !part2 || it !in corners }
                .forEach { (coordinate, c) ->
                    val count = neighbours[coordinate]!!.count { lights.getOrDefault(it, '.') == '#' }
                    if (c == '#' && count !in setOf(2, 3)) {
                        updated[coordinate] = '.'
                    }
                    if (c == '.' && count == 3) {
                        updated[coordinate] = '#'
                    }
                }

            lights = updated
        }

        return lights.values.count { it == '#' }
    }
}

fun main() {
    println(AnimatedLights().run(part2 = true))
}