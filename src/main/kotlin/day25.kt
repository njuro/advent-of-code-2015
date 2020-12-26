import utils.readInputBlock

/** [https://adventofcode.com/2015/day/25] */
class Day25 : AdventOfCodeTask {
    override fun run(part2: Boolean): Any {
        val pattern =
            Regex("To continue, please consult the code grid in the manual\\.  Enter the code at row (\\d+), column (\\d+)\\.")
        val (row, column) = pattern.matchEntire(readInputBlock("25.txt"))!!.groupValues.drop(1).map { it.toInt() }
        
        var position = 1
        var deltaX = 2
        repeat(column - 1) {
            position += deltaX
            deltaX++
        }
        var deltaY = column
        repeat(row - 1) {
            position += deltaY
            deltaY++
        }

        return generateSequence(20151125L) { prev -> (prev * 252533) % 33554393 }.drop(position - 1).first()
    }
}

fun main() {
    println(Day25().run(part2 = false))
}