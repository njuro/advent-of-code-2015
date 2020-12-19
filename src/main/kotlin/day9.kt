import utils.readInputLines

/** [https://adventofcode.com/2015/day/9] */
class Distances : AdventOfCodeTask {
    private val cities = mutableSetOf<String>()
    private val distances = mutableMapOf<Pair<String, String>, Int>()

    override fun run(part2: Boolean): Any {
        val pattern = Regex("(\\w+) to (\\w+) = (\\d+)")
        readInputLines("9.txt").forEach {
            val (source, destination, distance) = pattern.matchEntire(it)!!.destructured
            cities.add(source)
            cities.add(destination)
            distances[(source to destination)] = distance.toInt()
            distances[(destination to source)] = distance.toInt()
        }

        val aggregate: (List<Int>) -> Int = if (part2) {
            { it.maxOrNull()!! }
        } else {
            { it.minOrNull()!! }
        }
        return aggregate(cities.map { start -> travel(start, aggregate) })
    }

    private fun travel(
        current: String,
        aggregate: (List<Int>) -> Int,
        distance: Int = 0,
        route: List<String> = listOf(current)
    ): Int {
        if (route.size == cities.size) {
            return distance
        }
        return aggregate(
            distances.filterKeys { it.first == current && it.second !in route }.map { (destinations, newDistance) ->
                travel(destinations.second, aggregate, distance + newDistance, route + destinations.second)
            }
        )
    }
}

fun main() {
    print(Distances().run(part2 = true))
}