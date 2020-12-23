import utils.readInputLines
import kotlin.math.max

/** [https://adventofcode.com/2015/day/22] */
class Day22 : AdventOfCodeTask {
    data class Warrior(var hp: Int, val damage: Int) {
        fun attack(opponent: Wizard) {
            opponent.hp -= max(damage - opponent.armor, 1)
        }
    }

    data class Wizard(var hp: Int, var mana: Int, var damage: Int = 0, var armor: Int = 0) {
        private val activeSpells = mutableSetOf<Spell>()

        fun castSpell(opponent: Warrior, spell: Spell) {
            mana -= spell.cost
            println("Casting spell ${spell.name}!")

            if (spell.instantStart) {
                spell.start(this)
                opponent.hp -= damage
            }

            if (spell.timer > 0) {
                activeSpells.add(spell)
            } else {
                spell.end(this)
            }
        }

        fun spellEffects(opponent: Warrior) {
            activeSpells.toSet().forEach { spell ->
                println("Activating spell ${spell.name}!")
                spell.start(this)
                opponent.hp -= damage
                spell.timer--

                if (spell.timer == 0) {
                    spell.end(this)
                    println("Spell ${spell.name} expired!")
                    activeSpells.remove(spell)
                }
            }
        }
    }

    data class Spell(
        val name: String,
        val cost: Int,
        var timer: Int,
        val start: (Wizard) -> Unit,
        val end: (Wizard) -> Unit = {},
        val instantStart: Boolean = false
    ) {
        override fun equals(other: Any?) = name == (other as Spell).name
        override fun hashCode() = name.hashCode()
    }

    private val spells = listOf(
        Spell("Magic Missile", 53, 0, { it.damage = 4 }, { it.damage = 0 }, instantStart = true),
        Spell("Drain", 73, 0, { it.damage = 2; it.hp += 2 }, { it.damage = 0 }, instantStart = true),
        Spell("Shield", 113, 6, { it.armor = 7; it.damage = 0 }, { it.armor = 0 }, instantStart = true),
        Spell("Poison", 173, 6, { it.damage = 3 }, { it.damage = 0 }),
        Spell("Recharge", 229, 5, { it.mana += 101 })
    )

    override fun run(part2: Boolean): Any {
        val (hp, damage) = readInputLines("22.txt").map { it.substringAfter(": ").toInt() }
        val player = Wizard(50, 500)
        val boss = Warrior(hp, damage)

        return game(
            player.copy(),
            boss.copy(),
            mutableListOf(4, 2, 1, 3, 0)
        )
    }

    private fun game(player: Wizard, boss: Warrior, instructions: MutableList<Int>): Boolean {
        val spells = instructions.map { spells[it].copy() }.toMutableList()
        while (true) {
            println("-- Player turn --")
            println("Player: $player")
            println("Boss: $boss")
            player.spellEffects(boss)
            val spell = spells.removeFirstOrNull() ?: run {
                println("Out of spells!")
                return false
            }
            player.castSpell(boss, spell)
            if (player.mana < 0) {
                println("Out of mana!")
                return false
            }
            println()

            println("-- Boss turn --")
            println("Player: $player")
            println("Boss: $boss")
            player.spellEffects(boss)
            if (boss.hp <= 0) return true
            boss.attack(player)
            if (player.hp <= 0) return false
            println()
        }
    }
}

fun main() {
    println(Day22().run(part2 = false))
}