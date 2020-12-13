import utils.readInputLines

/** [https://adventofcode.com/2015/day/5] */
class Words : AdventOfCodeTask {
    override fun run(part2: Boolean): Any {
        return readInputLines("5.txt").count {
            if (part2) {
                val repeating = it.zipWithNext().any { (first, second) ->
                    val sub = first.toString() + second
                    it.indexOf(sub, startIndex = it.indexOf(sub) + 2) >= 0
                }
                val divided = it.contains(Regex("(\\w)\\w\\1"))
                repeating && divided
            } else {
                val banned = setOf("ab", "cd", "pq", "xy")
                val vowels = it.count { c -> c in setOf('a', 'e', 'i', 'o', 'u') } > 2
                val repeating = it.zipWithNext().any { (first, second) -> first == second }
                val clean = banned.none { naughty -> naughty in it }
                vowels && repeating && clean
            }
        }
    }
}

fun main() {
    print(Words().run(part2 = true))
}