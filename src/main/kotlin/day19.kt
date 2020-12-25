import utils.readInputBlock

/** [https://adventofcode.com/2015/day/19] */
class Day19 : AdventOfCodeTask {

    override fun run(part2: Boolean): Any {
        val (substitutionData, molecule) = readInputBlock("19.txt").split("\n\n")
        val substitutions = substitutionData.split("\n").map {
            val (previous, next) = it.split(" => ")
            previous to next
        }.toSet()

        return if (part2) {
            findPath(
                molecule,
                substitutions.map { it.second to it.first }.sortedByDescending { it.first.length }.toSet()
            )
        } else substitutions.flatMap { doSubstitution(molecule, it.first, it.second) }.distinct().size
    }

    private fun findPath(
        starting: String,
        substitutions: Set<Pair<String, String>>
    ): Int {
        return -1
    }

    private fun doSubstitution(input: String, from: String, to: String): MutableSet<String> {
        val results = mutableSetOf<String>()

        var index = input.indexOf(from)
        while (index != -1) {
            results.add(input.substring(0, index) + to + input.substring(index + from.length))
            index = input.indexOf(from, startIndex = index + 1)
        }

        return results
    }
}

fun main() {
    println(Day19().run(part2 = false))
}