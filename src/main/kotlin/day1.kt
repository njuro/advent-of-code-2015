import utils.readInputBlock

/** [https://adventofcode.com/2015/day/1] */
class Floors : AdventOfCodeTask {
    override fun run(part2: Boolean): Any {
        val floors = readInputBlock("1.txt").map { if (it == '(') 1 else -1 }
        return if (part2) {
            floors.scanIndexed(-1 to 0) { index, sum, next -> (index to (sum.second + next)) }
                .first { it.second < 0 }.first + 1
        } else floors.sum()
    }
}

fun main() {
    print(Floors().run(part2 = true))
}