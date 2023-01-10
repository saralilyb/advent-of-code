package net.jtth.aoc;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class Day5Test {

  private final Day5 day5 = new Day5("src/test/resources/day5test.dat");

  @Test
  @DisplayName("5-1 example data")
  void exampleData() {

    assertEquals("CMZ", day5.printStackTops(), "should read CMZ");

  }

  @Test
  @DisplayName("transposing lists for rectangular 2d lists")
  void transposeList() {
    List<List<Integer>> testList = List.of(List.of(1, 2), List.of(1, 2, 3), List.of(1, 2, 3));
    List<List<Integer>> transposedList = List.of(List.of(1, 1, 1), List.of(2, 2, 2), List.of(3, 3));
    assertEquals(transposedList, Day5.transposeListOfLists(testList), "list should transpose");

    List<List<String>> transposedExampleList = List.of(
        List.of("", "N", "Z"),
        List.of("D", "C", "M"),
        List.of("", "", "P")
    );
    List<List<String>> exampleList = List.of(
        List.of("", "D", ""),
        List.of("N", "C", ""),
        List.of("Z", "M", "P")
    );
    assertEquals(transposedExampleList, Day5.transposeListOfLists(exampleList),
        "example list should transpose");
  }

}
