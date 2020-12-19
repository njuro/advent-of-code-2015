import utils.readInputLines

/** [https://adventofcode.com/2015/day/15] */
class Cookies : AdventOfCodeTask {

    data class Ingredient(val name: String, val properties: Map<String, Int>)

    override fun run(part2: Boolean): Any {
        val propertiesNames = mutableSetOf<String>()
        val ingredients = readInputLines("15.txt").map {
            val (ingredient, propertiesData) = it.split(": ")
            val properties = propertiesData.split(", ").map { property ->
                val (name, value) = property.split(" ")
                propertiesNames.add(name)
                name to value.toInt()
            }.toMap()
            Ingredient(ingredient, properties)
        }

        var maxScore = -1
        for (i in 0..100) {
            for (j in 0..100) {
                for (k in 0..100) {
                    for (l in 0..100) {
                        if (i + j + k + l != 100) continue
                        val amounts = setOf(i, j, k, l)
                        val totalProperties = propertiesNames.associateWith {
                            val sum = amounts.mapIndexed { index, amount ->
                                ingredients[index].properties[it]!! * amount
                            }.sum()
                            maxOf(sum, 0)
                        }
                        if (!part2 || totalProperties["calories"] == 500) {
                            val score = totalProperties.filterKeys { it != "calories" }.values.reduce { a, b -> a * b }
                            maxScore = maxOf(score, maxScore)
                        }
                    }
                }
            }
        }

        return maxScore
    }
}

fun main() {
    print(Cookies().run(part2 = true))
}