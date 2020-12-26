import utils.readInputBlock

/** [https://adventofcode.com/2015/day/19] */
class Reactions : AdventOfCodeTask {

    override fun run(part2: Boolean): Any {
        val (substitutionData, molecule) = readInputBlock("19.txt").split("\n\n")
        val substitutions = substitutionData.split("\n").map {
            val (previous, next) = it.split(" => ")
            previous to next
        }.toSet()

        return if (part2) {
            val reversedSubstitutions = substitutions.map { it.second to it.first }.toSet()
            generateSequence(molecule) { prev ->
                reversedSubstitutions.first { it.first in prev }.let { (from, to) ->
                    doSubstitution(prev, from, to).first()
                }
            }.takeWhile { it != "e" }.count()
        } else substitutions.flatMap { doSubstitution(molecule, it.first, it.second) }.distinct().size
    }

    private fun doSubstitution(input: String, from: String, to: String) = sequence {
        var index = input.indexOf(from)
        while (index != -1) {
            yield(input.substring(0, index) + to + input.substring(index + from.length))
            index = input.indexOf(from, startIndex = index + 1)
        }
    }
}

fun main() {
    println(Reactions().run(part2 = true))
}