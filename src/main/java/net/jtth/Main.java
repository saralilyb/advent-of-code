package net.jtth;

import java.io.IOException;

public class Main {

  public static void main(String[] args) throws IOException {
    Day1 day1 = new Day1("src/main/resources/day1.dat");
    System.out.println("1-1\n " + day1.getBestSum());
    System.out.println("\n1-2 " + day1.getTop3SumsSummed());

    Day2 day21 = new Day2("src/main/resources/day2.dat", true);
    System.out.println("\n2-1 " + day21.getMyHighScore());
    Day2 day22 = new Day2("src/main/resources/day2.dat", false);
    System.out.println("\n2-2 " + day22.getMyHighScore());

    Day3 day3 = new Day3("src/main/resources/day3.dat");
    System.out.println("\n3-1 " + day3.printScore());
    System.out.println("\n3-2 " + day3.printGroupScore());

    Day4 day4 = new Day4("src/main/resources/day4.dat");
    System.out.println("\n4-1 " + day4.printSumOfFullyContainedAssignmentPairs());
    System.out.println("\n4-2 " + day4.printSumOfOverlappingAssignmentPairs());

    Day4Kotlin day4Kotlin = new Day4Kotlin("src/main/resources/day4.dat");
    System.out.println("\n4-1 " + day4Kotlin.printSumOfFullyContainedAssignmentPairs());
    System.out.println("\n4-2 " + day4Kotlin.printSumOfOverlappingAssignmentPairs());

    Day5 day5 = new Day5("src/main/resources/day5.dat");
    System.out.println(("\n5-1 " + day5.printStackTops()));
  }
}