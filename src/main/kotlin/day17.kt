import utils.readInputLines

/** [https://adventofcode.com/2015/day/17] */
class Containers : AdventOfCodeTask {
    override fun run(part2: Boolean): Any {
        val containers = readInputLines("17.txt").mapIndexed { index, volume -> index to volume.toInt() }.toSet()
        val combinations = findCombinations(containers)

        return if (part2) {
            val minSize = combinations.minByOrNull { it.size }!!.size
            combinations.count { it.size == minSize }
        } else combinations.size
    }

    private fun findCombinations(
        containers: Set<Pair<Int, Int>>,
        used: Set<Int> = setOf(),
        remaining: Int = 150
    ): Set<Set<Int>> {
        if (remaining == 0) {
            return setOf(used)
        }

        return containers.filter { it.second <= remaining }.fold(setOf()) { result, candidate ->
            result + findCombinations(
                containers - candidate,
                used + candidate.first,
                remaining - candidate.second
            )
        }
    }
}

fun main() {
    println(Containers().run(part2 = true))
}