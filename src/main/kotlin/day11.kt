import utils.readInputBlock

/** [https://adventofcode.com/2015/day/11] */
class Passwords : AdventOfCodeTask {
    private val triples = ('a'..'x').map { listOf(it, it + 1, it + 2).joinToString("") }

    override fun run(part2: Boolean): Any {
        val nextPassword = readInputBlock("11.txt").next()
        return if (part2) nextPassword.next() else nextPassword
    }

    private fun String.next() = generateSequence(this.increment()) {
        it.increment()
    }.dropWhile { !it.isValid() }.first()

    private fun String.isValid(): Boolean {
        val increasing = triples.any { contains(it) }
        val allowed = all { it !in setOf('i', 'o', 'l') }
        val repeating = zipWithNext().mapNotNull { (a, b) -> if (a == b) a else null }.toSet().size >= 2

        return increasing && allowed && repeating
    }

    private fun String.increment(): String {
        val increased = toMutableList()
        var index = lastIndex
        while (index >= 0) {
            if (get(index) < 'z') {
                increased[index] = get(index).inc()
                break
            }
            increased[index] = 'a'
            index--
        }

        return increased.joinToString("")
    }
}

fun main() {
    print(Passwords().run(part2 = true))
}