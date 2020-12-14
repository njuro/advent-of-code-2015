import utils.readInputLines
import kotlin.math.pow

/** [https://adventofcode.com/2015/day/7] */
class Wires : AdventOfCodeTask {

    val signals = mutableMapOf<String, Int>()

    inner class Instruction(
        val command: String,
        val sources: Pair<String, String>,
        val argument: Int,
        val destination: String
    ) {
        fun evaluate(): Int {
            val first = signals.getOrDefault(sources.first, sources.first.toIntOrNull() ?: 0)
            val second = signals.getOrDefault(sources.second, sources.second.toIntOrNull() ?: 0)
            return when (command) {
                "AND" -> first and second
                "OR" -> first or second
                "LSHIFT" -> first shl argument
                "RSHIFT" -> first shr argument
                "NOT" -> first.inv() + 2.0.pow(16).toInt()
                "SET" -> argument
                "COPY" -> first
                else -> throw IllegalArgumentException("Unknown command $command")
            }
        }

        fun canBeEvaluated(): Boolean {
            return (sources.first.isBlank() || sources.first.toIntOrNull() != null || signals.containsKey(sources.first))
                && (sources.second.isBlank() || sources.second.toIntOrNull() != null || signals.containsKey(sources.second))
        }
    }

    override fun run(part2: Boolean): Any {
        val instructions = readInputLines("7.txt").map {
            val tokens = it.split(" ")
            val destinaton = tokens.last()
            when {
                "AND" in tokens -> Instruction("AND", tokens[0] to tokens[2], 0, destinaton)
                "OR" in tokens -> Instruction("OR", tokens[0] to tokens[2], 0, destinaton)
                "LSHIFT" in tokens -> Instruction("LSHIFT", tokens[0] to "", tokens[2].toInt(), destinaton)
                "RSHIFT" in tokens -> Instruction("RSHIFT", tokens[0] to "", tokens[2].toInt(), destinaton)
                "NOT" in tokens -> Instruction("NOT", tokens[1] to "", 0, destinaton)
                else -> tokens[0].toIntOrNull()?.let { argument ->
                    Instruction("SET", "" to "", argument, destinaton)
                } ?: Instruction("COPY", tokens[0] to "", 0, destinaton)
            }
        }.toMutableSet()

        val first = evaluate(instructions.toMutableSet())
        return if (part2) {
            signals.clear()
            instructions.removeIf { it.destination == "b" }
            instructions.add(Instruction("SET", "" to "", first, "b"))
            evaluate(instructions.toMutableSet())
        } else first
    }

    private fun evaluate(instructions: MutableSet<Instruction>): Int {
        while (instructions.isNotEmpty()) {
            val instruction = instructions.first(Instruction::canBeEvaluated)
            signals[instruction.destination] = instruction.evaluate()
            instructions.remove(instruction)
        }

        return signals["a"]!!
    }
}

fun main() {
    print(Wires().run(part2 = true))
}