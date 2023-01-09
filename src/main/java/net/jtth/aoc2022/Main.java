package net.jtth.aoc2022;

import clojure.java.api.Clojure;
import clojure.lang.IFn;
import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.util.concurrent.Callable;
import lombok.SneakyThrows;

public class Main {

  public static Object callClojure(String ns, String fn) throws Exception {
    // load Clojure lib. See https://clojure.github.io/clojure/javadoc/clojure/java/api/Clojure.html
    IFn require = Clojure.var("clojure.core", "require");
    require.invoke(Clojure.read(ns));

    return ((Callable) Clojure.var(ns, fn)).call();
  }

  @SneakyThrows
  public static void main(String[] args) throws IOException {
    // Clojure fns are callable
    Callable fn = (Callable) callClojure("net.jtth.aoc2022.impl", "create-hello-fn");
    System.out.println("fn says " + fn.call());

    // Clojure can implement interfaces
    FileFilter filter = (FileFilter) callClojure("net.jtth.aoc22.impl", "create-never-filter");
    System.out.println("file filter returns " + filter.accept(new File("canttouchthis")));

    // Clojure can extend classes
    Object o = callClojure("net.jtth.aoc22.impl", "create-timestamped-object");
    System.out.println("object toString returns " + o);

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
