import utils.readInputLines

/** [https://adventofcode.com/2015/day/8] */
class Encoding : AdventOfCodeTask {
    override fun run(part2: Boolean): Any {
        val hexPattern = Regex("""\\x[a-f0-9]{2}""")
        return readInputLines("8.txt").sumBy {
            if (part2) {
                val encoded = """"\"""" + it.substring(1, it.length - 1)
                    .replace("""\\""", """\\\\""")
                    .replace("""\"""", """\\\"""")
                    .replace(hexPattern, """\\\\xXX""") + """\"""""
                encoded.length - it.length
            } else {
                val decoded = it.substring(1, it.length - 1)
                    .replace("""\\""", """\""")
                    .replace("""\"""", """"""")
                    .replace(hexPattern, "X")
                it.length - decoded.length
            }
        }
    }
}

fun main() {
    print(Encoding().run(part2 = true))
}