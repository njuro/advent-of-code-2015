import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonArray
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.JsonPrimitive
import utils.readInputBlock

/** [https://adventofcode.com/2015/day/12] */
class JsonSum : AdventOfCodeTask {
    override fun run(part2: Boolean): Any {
        return Json.parseToJsonElement(readInputBlock("12.txt")).sum(ignoreRed = part2)
    }

    private fun JsonElement.sum(ignoreRed: Boolean): Int = when (this) {
        is JsonObject -> if (ignoreRed && containsValue(JsonPrimitive("red"))) 0 else values.sumBy { it.sum(ignoreRed) }
        is JsonArray -> sumBy { it.sum(ignoreRed) }
        is JsonPrimitive -> content.toIntOrNull() ?: 0
        else -> throw IllegalArgumentException()
    }
}

fun main() {
    print(JsonSum().run(part2 = true))
}