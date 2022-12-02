package net.jtth;

import java.io.IOException;

public class Main {

  public static void main(String[] args) throws IOException {
    Day1 day1 = new Day1("src/main/resources/day1.dat");
    System.out.println(day1.getBestSum());
    System.out.println(day1.getTop3SumsSummed());
  }
}