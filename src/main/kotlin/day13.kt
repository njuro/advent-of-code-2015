import utils.permutations
import utils.readInputLines

/** [https://adventofcode.com/2015/day/13] */
class Seats : AdventOfCodeTask {
    override fun run(part2: Boolean): Any {
        val pattern = Regex("(\\w+) would (\\w+) (\\d+) happiness units by sitting next to (\\w+)\\.")
        val guests = mutableSetOf<String>()
        val rules = readInputLines("13.txt").map {
            val (current, effect, value, neighbour) = pattern.matchEntire(it)!!.destructured
            guests.add(current)
            (current to neighbour) to value.toInt() * (if (effect == "gain") 1 else -1)
        }.toMap().withDefault { 0 }

        if (part2) {
            guests.add("You")
        }

        return guests.toList().permutations().map { arrangement ->
            arrangement.mapIndexed { index, guest ->
                val right = rules.getValue(guest to arrangement[if (index == arrangement.lastIndex) 0 else index + 1])
                val left = rules.getValue(guest to arrangement[if (index == 0) arrangement.lastIndex else index - 1])
                right + left
            }.sum()
        }.maxOrNull()!!
    }
}

fun main() {
    print(Seats().run(part2 = true))
}