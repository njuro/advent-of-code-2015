import utils.readInputLines
import kotlin.math.min

/** [https://adventofcode.com/2015/day/24] */
class Packages : AdventOfCodeTask {
    private val packages = readInputLines("24.txt").map(String::toInt).sortedDescending()
    private var smallestGroupSize = Int.MAX_VALUE

    override fun run(part2: Boolean): Any {
        val weightPerGroup = packages.sum() / if (part2) 4 else 3
        return packages.flatMap { findCombinations(mutableSetOf(it), weightPerGroup) }
            .filter { it.isNotEmpty() }.map { it.product() }.minOrNull()!!
    }

    private fun findCombinations(group: Set<Int>, weightPerGroup: Int): Set<Set<Int>> {
        if (group.size > smallestGroupSize || group.sum() > weightPerGroup) {
            return setOf(emptySet())
        }

        if (group.sum() == weightPerGroup) {
            smallestGroupSize = min(smallestGroupSize, group.size)
            return setOf(group)
        }

        return packages.filter { it < group.last() }.flatMap {
            findCombinations(group + it, weightPerGroup)
        }.toSet()
    }

    private fun Set<Int>.product() = fold(1L) { a, b -> a * b }
}

fun main() {
    println(Packages().run(part2 = false))
}