import utils.readInputLines

/** [https://adventofcode.com/2015/day/2] */
class Presents : AdventOfCodeTask {
    override fun run(part2: Boolean): Any {
        val presents = readInputLines("2.txt").map { it.split("x").map(String::toInt).sorted() }
        return presents.sumBy { (a, b, c) ->
            if (part2) 2 * a + 2 * b + a * b * c else 3 * a * b + 2 * b * c + 2 * a * c
        }
    }
}

fun main() {
    print(Presents().run(part2 = true))
}