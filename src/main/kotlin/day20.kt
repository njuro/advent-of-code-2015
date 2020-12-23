import utils.readInputBlock
import kotlin.math.sqrt

/** [https://adventofcode.com/2015/day/20] */
class Houses : AdventOfCodeTask {
    override fun run(part2: Boolean): Any {
        val input = readInputBlock("20.txt").toInt()
        var house = 0
        do {
            house++
            val presents = calculatePresents(house, infinite = !part2)
        } while (presents < input)

        return house
    }

    private fun calculatePresents(house: Int, infinite: Boolean) = (1..sqrt(house.toDouble()).toInt())
        .filter { house % it == 0 }
        .flatMap { setOf(it, house / it) }
        .filter { infinite || house <= it * 50 }.sum() * (if (infinite) 10 else 11)
}

fun main() {
    println(Houses().run(part2 = true))
}