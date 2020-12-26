import utils.readInputLines

/** [https://adventofcode.com/2015/day/23] */
class Computer : AdventOfCodeTask {
    override fun run(part2: Boolean): Any {
        val instructions = readInputLines("23.txt").map {
            val (instruction, arguments) = it.split(" ", limit = 2)
            instruction to arguments.split(", ")
        }
        val registers = mutableMapOf("a" to if (part2) 1L else 0L, "b" to 0L)
        var pointer = 0

        while (pointer in instructions.indices) {
            val (instruction, arguments) = instructions[pointer]
            when (instruction) {
                "hlf" -> {
                    registers[arguments[0]] = registers[arguments[0]]!! / 2
                    pointer++
                }
                "tpl" -> {
                    registers[arguments[0]] = registers[arguments[0]]!! * 3
                    pointer++
                }
                "inc" -> {
                    registers[arguments[0]] = registers[arguments[0]]!! + 1
                    pointer++
                }
                "jmp" -> pointer += arguments[0].toInt()
                "jie" -> if (registers[arguments[0]]!! % 2 == 0L) pointer += arguments[1].toInt() else pointer++
                "jio" -> if (registers[arguments[0]]!! == 1L) pointer += arguments[1].toInt() else pointer++
            }
        }

        return registers["b"]!!
    }
}

fun main() {
    println(Computer().run(part2 = true))
}