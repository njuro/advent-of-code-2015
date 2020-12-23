import utils.readInputLines
import kotlin.math.max

/** [https://adventofcode.com/2015/day/21] */
class Warriors : AdventOfCodeTask {

    data class Warrior(var hp: Int, val damage: Int, val armor: Int) {
        fun attack(other: Warrior) {
            other.hp -= max(damage - other.armor, 1)
        }
    }

    data class Item(val cost: Int, val damage: Int, val armor: Int) {
        fun merge(other: Item) = Item(cost + other.cost, damage + other.damage, armor + other.armor)
    }

    override fun run(part2: Boolean): Any {
        val (hp, damage, armor) = readInputLines("21.txt").map { it.substringAfter(": ").toInt() }
        val boss = Warrior(hp, damage, armor)
        return if (part2)
            equipments().sortedByDescending { it.cost }.first { !game(it, boss.copy()) }.cost
        else
            equipments().sortedBy { it.cost }.first { game(it, boss.copy()) }.cost
    }

    private fun game(equipment: Item, boss: Warrior): Boolean {
        val player = Warrior(100, equipment.damage, equipment.armor)
        while (true) {
            player.attack(boss)
            if (boss.hp <= 0) return true
            boss.attack(player)
            if (player.hp <= 0) return false
        }
    }

    private fun equipments(): List<Item> {
        val weapons = listOf(
            Item(8, 4, 0),
            Item(10, 5, 0),
            Item(25, 6, 0),
            Item(40, 7, 0),
            Item(74, 8, 0)

        )

        val armors = listOf(
            Item(13, 0, 1),
            Item(31, 0, 2),
            Item(53, 0, 3),
            Item(75, 0, 4),
            Item(102, 0, 5),
        )

        val rings = listOf(
            Item(25, 1, 0),
            Item(50, 2, 0),
            Item(100, 3, 0),
            Item(20, 0, 1),
            Item(40, 0, 2),
            Item(80, 0, 3)
        )

        val oneItem = weapons.map { weapon -> listOf(weapon) }
        val twoItems = oneItem.flatMap { set -> armors.map { armor -> set + armor } } + oneItem
        val threeItems = twoItems.flatMap { set -> rings.map { ring -> set + ring } }
        val fourItems =
            threeItems.flatMap { set -> rings.filter { it.cost != set.last().cost }.map { ring -> set + ring } }

        return (oneItem + twoItems + threeItems + fourItems)
            .map { set -> set.reduce { first, second -> first.merge(second) } }
    }
}

fun main() {
    println(Warriors().run(part2 = true))
}