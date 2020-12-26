import utils.readInputLines
import java.lang.Integer.min
import kotlin.math.max

/** [https://adventofcode.com/2015/day/22] */
class Wizards : AdventOfCodeTask {
    data class Warrior(var hp: Int, val damage: Int) {
        fun attack(opponent: Wizard) {
            opponent.hp -= max(damage - opponent.armor, 1)
        }
    }

    data class Wizard(var hp: Int, var mana: Int, var damage: Int = 0, var armor: Int = 0) {
        var activeSpells = mutableSetOf<Spell>()

        fun castSpell(opponent: Warrior, spell: Spell) {
            mana -= spell.cost

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
                spell.start(this)
                opponent.hp -= damage
                spell.timer--

                if (spell.timer == 0) {
                    spell.end(this)
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
        Spell("Recharge", 229, 5, { it.mana += 101; it.damage = 0 })
    )

    var leastCost = Int.MAX_VALUE

    override fun run(part2: Boolean): Any {
        leastCost = Int.MAX_VALUE
        val (hp, damage) = readInputLines("22.txt").map { it.substringAfter(": ").toInt() }
        val boss = Warrior(hp, damage)
        val player = Wizard(50, 500)

        return spells.map { initialSpell ->
            game(player.copy(), boss.copy(), initialSpell.copy(), hard = part2)
        }.minOrNull()!!
    }

    private fun game(player: Wizard, boss: Warrior, spell: Spell, cost: Int = spell.cost, hard: Boolean): Int {
        if (cost > leastCost) return Int.MAX_VALUE
        if (hard && --player.hp <= 0) return Int.MAX_VALUE

        player.spellEffects(boss)
        player.castSpell(boss, spell)
        if (player.mana < 0) return Int.MAX_VALUE

        player.spellEffects(boss)
        if (boss.hp <= 0) {
            leastCost = min(cost, leastCost)
            return cost
        }
        boss.attack(player)
        if (player.hp <= 0) return Int.MAX_VALUE

        return spells.map { next ->
            val playerCopy = player.copy().apply {
                activeSpells = player.activeSpells.map { spell -> spell.copy() }.toMutableSet()
            }
            game(playerCopy, boss.copy(), next.copy(), cost + next.cost, hard)
        }.minOrNull()!!
    }
}

fun main() {
    println(Wizards().run(part2 = true))
}