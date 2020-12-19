import utils.readInputBlock

/** [https://adventofcode.com/2015/day/10] */
class MorrisSequence : AdventOfCodeTask {
    override fun run(part2: Boolean): Any {
        val seed = readInputBlock("10.txt")
        return generateSequence(seed) { previous ->
            var counter = 1
            var current = previous.first()
            val result = StringBuilder()
            previous.drop(1).forEach { c ->
                if (c == current) {
                    counter++
                } else {
                    result.append("$counter$current")
                    counter = 1
                    current = c
                }
            }
            result.append("$counter$current").toString()
        }.take(if (part2) 51 else 41).last().length
    }
}

fun main() {
    print(MorrisSequence().run(part2 = true))
}