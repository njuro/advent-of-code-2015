import utils.readInputLines

/** [https://adventofcode.com/2015/day/16] */
class Day16 : AdventOfCodeTask {
    override fun run(part2: Boolean): Any {
        val data = """
            children: 3
            cats: 7
            samoyeds: 2
            pomeranians: 3
            akitas: 0
            vizslas: 0
            goldfish: 5
            trees: 3
            cars: 2
            perfumes: 1
        """.trimIndent().split("\n").map { it.parseProperty() }.toMap()

        val aunts = readInputLines("16.txt").map { it.substringAfter(": ") }.mapIndexed { index, data ->
            val properties = data.split(", ").map { it.parseProperty() }.toMap()
            index + 1 to properties
        }

        return aunts.first { (_, properties) ->
            properties.all { (property, value) ->
                val realValue = data[property]!!
                when {
                    part2 && property in setOf("trees", "cats") -> realValue < value
                    part2 && property in setOf("pomeranians", "goldfish") -> realValue > value
                    else -> realValue == value
                }
            }
        }.first
    }

    private fun String.parseProperty(): Pair<String, Int> {
        val (property, value) = split(": ")
        return property to value.toInt()
    }
}

fun main() {
    println(Day16().run(part2 = true))
}