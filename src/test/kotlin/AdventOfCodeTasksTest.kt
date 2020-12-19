import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class AdventOfCodeTasksTest {

    @Test
    fun day1() {
        runTaskTest(Floors(), 138, 1771)
    }

    @Test
    fun day2() {
        runTaskTest(Presents(), 1588178, 3783758)
    }

    @Test
    fun day3() {
        runTaskTest(Navigation(), 2565, 2639)
    }

    @Test
    fun day4() {
        runTaskTest(Hashes(), 254575, 1038736)
    }

    @Test
    fun day5() {
        runTaskTest(Words(), 238, 69)
    }

    @Test
    fun day6() {
        runTaskTest(Lights(), 569999, 17836115)
    }

    @Test
    fun day7() {
        runTaskTest(Wires(), 16076, 2797)
    }

    @Test
    fun day8() {
        runTaskTest(Encoding(), 1371, 2117)
    }

    @Test
    fun day9() {
        runTaskTest(Distances(), 251, 898)
    }

    @Test
    fun day10() {
        runTaskTest(MorrisSequence(), 492982, 6989950)
    }

    @Test
    fun day11() {
        runTaskTest(Passwords(), "hxbxxyzz", "hxcaabcc")
    }

    @Test
    fun day12() {
        runTaskTest(JsonSum(), 191164, 87842)
    }

    @Test
    fun day13() {
        runTaskTest(Seats(), 618, 601)
    }

    @Test
    fun day14() {
        runTaskTest(Race(), 2655, 1059)
    }

    private fun runTaskTest(task: AdventOfCodeTask, part1Result: Any, part2Result: Any) {
        assertEquals(part1Result, task.run())
        assertEquals(part2Result, task.run(part2 = true))
    }
}