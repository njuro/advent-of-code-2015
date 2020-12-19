import utils.readInputLines
import kotlin.math.min

/** [https://adventofcode.com/2015/day/14] */
class Race : AdventOfCodeTask {

    data class Reindeer(val name: String, val velocity: Int, val endurance: Int, val rest: Int) {

        fun calculateDistance(seconds: Int): Int {
            val cycle = endurance + rest
            val cycles = seconds / cycle
            val rest = seconds - cycle * cycles
            return velocity * (cycles * endurance + min(rest, endurance))
        }
    }

    override fun run(part2: Boolean): Any {
        val pattern = Regex("(\\w+) can fly (\\d+) km/s for (\\d+) seconds, but then must rest for (\\d+) seconds\\.")
        val reindeers = readInputLines("14.txt").map {
            val (name, velocity, endurance, rest) = pattern.matchEntire(it)!!.destructured
            Reindeer(name, velocity.toInt(), endurance.toInt(), rest.toInt()) to 0
        }.toMap().toMutableMap()
        val rounds = 2503

        return if (part2) {
            (1..rounds).forEach { second ->
                val winning = reindeers.keys.maxByOrNull { it.calculateDistance(second) }!!
                reindeers[winning] = reindeers[winning]!! + 1
            }
            reindeers.values.maxOrNull()!!
        } else {
            reindeers.keys.map { it.calculateDistance(rounds) }.maxOrNull()!!
        }
    }
}

fun main() {
    print(Race().run(part2 = true))
}