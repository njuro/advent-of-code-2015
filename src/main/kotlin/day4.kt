import utils.readInputBlock
import java.security.MessageDigest

/** [https://adventofcode.com/2015/day/4] */
class Hashes : AdventOfCodeTask {

    override fun run(part2: Boolean): Any {

        val hash = readInputBlock("4.txt")
        return generateSequence(0) { it + 1 }.first { md5(hash + it).startsWith("0".repeat(if (part2) 6 else 5)) }
    }

    private fun md5(str: String) = MessageDigest.getInstance("MD5")
        .digest(str.toByteArray()).joinToString("") { "%02x".format(it) }
}

fun main() {
    print(Hashes().run(part2 = true))
}