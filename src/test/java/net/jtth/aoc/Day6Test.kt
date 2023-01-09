package net.jtth.aoc

import org.junit.jupiter.api.Test
import java.io.StringReader
import kotlin.test.assertEquals

class Day6Test {
    @Test
    fun `6-1 test example input`() {
        val ex1 = Pair("bvwbjplbgvbhsrlpgdmjqwftvncz", 5L)
        val ex2 = Pair("nppdvjthqldpwncqszvftbrmjlhg", 6L)
        val ex3 = Pair("nznrnfrfntjfmvfwmzdfjlvtqnbhcprsg", 10L)
        val ex4 = Pair("zcfzfwzzqfrljwzlrfnpqdbhtmscgvjw", 11L)
        val examples = listOf(ex1, ex2, ex3, ex4)

        for (example in examples) {
            assertEquals(example.second, Day6(wrapInReader(example.first)).getPosition(), "should be ${example.second}")
        }
    }

    @Test
    fun `6-2 test example input with bufferSize`() {
        val bufferSize = 14
        val ex1 = Pair("mjqjpqmgbljsphdztnvjfqwrcgsmlb", 19L)
        val ex2 = Pair("bvwbjplbgvbhsrlpgdmjqwftvncz", 23L)
        val ex3 = Pair("nznrnfrfntjfmvfwmzdfjlvtqnbhcprsg", 29L)
        val ex4 = Pair("zcfzfwzzqfrljwzlrfnpqdbhtmscgvjw", 26L)
        val examples = listOf(ex1, ex2, ex3, ex4)

        for (example in examples) {
            assertEquals(example.second, Day6(wrapInReader(example.first), bufferSize).getPosition(), "should be ${example.second}")
        }
    }
}

fun wrapInReader(s: String) = StringReader(s)
