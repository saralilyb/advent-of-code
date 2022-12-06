package net.jtth;

import java.io.FileReader;
import java.util.Scanner;
import lombok.Getter;
import org.apache.commons.lang3.Range;

public class Day4 {

  private Long sumOfFullyContainedAssigmentPairs = 0L;
  private Long sumOfOverlappingAssignmentPairs = 0L;

  public Day4(String dataFile) {
    parseData(dataFile);
  }

  private void parseData(String dataFile) {
    try {
      // read by group of digits
      Scanner scanner = new Scanner(new FileReader(dataFile));
      scanner.useDelimiter("\\D+");
      while (scanner.hasNext() && scanner.hasNextLine()) {
        // break down structure into objects

        long start1 = scanner.nextLong();
        long end1 = scanner.nextLong();
        long start2 = scanner.nextLong();
        long end2 = scanner.nextLong();

        AssignmentRange elf1range = new AssignmentRange(start1, end1);
        AssignmentRange elf2Range = new AssignmentRange(start2, end2);

        if (elf1range.containsRange(elf2Range.getRange())
            || elf2Range.containsRange(elf1range.getRange())) {
          sumOfFullyContainedAssigmentPairs += 1L;
        }
        if (elf1range.overlapsRange(elf2Range.getRange())
            || elf2Range.overlapsRange(elf1range.getRange())) {
          sumOfOverlappingAssignmentPairs += 1L;
        }

      }
      scanner.close();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public String printSumOfFullyContainedAssignmentPairs() {
    return "" + sumOfFullyContainedAssigmentPairs;
  }

  public String printSumOfOverlappingAssignmentPairs() {
    return "" + sumOfOverlappingAssignmentPairs;
  }

  public static final class AssignmentRange {

    @Getter
    private final Range<Long> range;

    public AssignmentRange(Long start, Long end) {
      this.range = Range.between(start, end);
    }

    public boolean containsRange(Range<Long> comparisonRange) {
      return range.containsRange(comparisonRange);
    }

    public boolean overlapsRange(Range<Long> comparisonRange) {
      return range.isOverlappedBy(comparisonRange);
    }
  }
}