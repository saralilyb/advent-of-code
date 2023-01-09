package net.jtth.aoc

import org.apache.commons.lang3.Range
import java.io.FileReader
import java.util.*

class Day4Kotlin(dataFile: String) {
    private var sumOfFullyContainedAssigmentPairs = 0L
    private var sumOfOverlappingAssignmentPairs = 0L

    init {
        parseData(dataFile)
    }

    private fun parseData(dataFile: String) {
        try {
            // read by group of digits
            val scanner = Scanner(FileReader(dataFile))
            scanner.useDelimiter("\\D+")
            while (scanner.hasNext() && scanner.hasNextLine()) {
                // break down structure into objects
                val start1 = scanner.nextLong()
                val end1 = scanner.nextLong()
                val start2 = scanner.nextLong()
                val end2 = scanner.nextLong()
                val elf1range = AssignmentRange(start1, end1)
                val elf2Range = AssignmentRange(start2, end2)
                if (elf1range.containsRange(elf2Range.range)
                        || elf2Range.containsRange(elf1range.range)) {
                    sumOfFullyContainedAssigmentPairs += 1L
                }
                if (elf1range.overlapsRange(elf2Range.range)
                        || elf2Range.overlapsRange(elf1range.range)) {
                    sumOfOverlappingAssignmentPairs += 1L
                }
            }
            scanner.close()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun printSumOfFullyContainedAssignmentPairs(): String {
        return "" + sumOfFullyContainedAssigmentPairs
    }

    fun printSumOfOverlappingAssignmentPairs(): String {
        return "" + sumOfOverlappingAssignmentPairs
    }

    class AssignmentRange(start: Long, end: Long) {
        val range: Range<Long>

        init {
            range = Range.between(start, end)
        }

        fun containsRange(comparisonRange: Range<Long>?): Boolean {
            return range.containsRange(comparisonRange)
        }

        fun overlapsRange(comparisonRange: Range<Long>?): Boolean {
            return range.isOverlappedBy(comparisonRange)
        }
    }
}
