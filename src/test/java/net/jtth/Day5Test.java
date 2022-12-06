package net.jtth;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class Day5Test {

  private final Day5 day5 = new Day5("src/test/resources/day5test.dat");

  @Test
  @DisplayName("5-1 example data")
  void example() {
    assertEquals("CMZ", day5.printStackTops(), "should read CMZ");

  }

}
