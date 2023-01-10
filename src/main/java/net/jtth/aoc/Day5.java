package net.jtth.aoc;

import java.io.FileReader;
import java.util.ArrayList;
import java.util.EmptyStackException;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;
import java.util.Stack;
import java.util.stream.IntStream;
import lombok.Getter;
import lombok.extern.java.Log;

@Log
public class Day5 {

  private List<Pile> piles;
  private String stackTops;

  public Day5(String dataFile) {
    parseFile(dataFile);
  }

  // https://stackoverflow.com/a/45490275/1576325
  // Don't write transpositions yourself, who cares, this should be in a library.
  // I cleaned this up but really why isn't this in Commons it's in every stdlib.
  // There's probably a way to do this more efficiently with Vectors.
  public static <T> List<List<T>> transposeListOfLists(List<List<T>> list) {
    final int N = list.stream().mapToInt(List::size).max().orElse(-1);
    List<Iterator<T>> iterList = list.stream().map(List::iterator).toList();
    return IntStream.range(0, N)
        .mapToObj(n -> iterList.stream()
            .filter(Iterator::hasNext)
            .map(Iterator::next)
            .toList())
        .toList();
  }

  private List<Pile> transposeRowsIntoPiles(List<List<String>> rows) {
    List<List<String>> transposedList = transposeListOfLists(rows);

    List<Pile> tempPiles = new ArrayList<>();
    for (List<String> column : transposedList) {
      tempPiles.add(new Pile(column));
    }

    return tempPiles;
  }

  private void moveCrates(int count, int fromPileIdx, int toPileIdx)
      throws EmptyStackException {
    Pile fromPile = piles.get(fromPileIdx);
    Pile toPile = piles.get(toPileIdx);
    log.info("fromPile " + fromPile.getStack() + " toPile " + toPile.getStack());
    for (int c = count; c >= 1; c--) {
      toPile.push(fromPile.pop());
    }
  }

  private String readStackTops(List<Pile> piles) {
    StringBuilder stringBuilder = new StringBuilder();
    for (Pile pile : piles) {
      stringBuilder.append(pile.peek());
    }

    return stringBuilder.toString();
  }

  private void parseFile(String dataFile) {
    try {
      Scanner scanner = new Scanner(new FileReader(dataFile));

      // assemble rows, ignoring numerical column enumeration
      // FIXME: this is where the bug is. I'm not reading in the top parts of
      //   the stacks correctly. It's not aligned right so it gets transposed poorly.
      //   I need to scan for blanks or something.
      scanner.useDelimiter("[^\\n|\\w]+|\\d");
      List<List<String>> rows = new ArrayList<>();
      List<String> row = new ArrayList<>();
      while (scanner.hasNext()
          && scanner.hasNextLine()
          && !scanner.hasNext("")) {
        String nextVal = scanner.next();
        if (Objects.equals(nextVal, "\n")) {
          rows.add(row);
          row = new ArrayList<>();
        } else {
          row.add(nextVal);
        }
      }

      scanner.nextLine(); // skip column enumeration

      piles = transposeRowsIntoPiles(rows);

      // parse instructions
      scanner.useDelimiter("\\D+");
      while (scanner.hasNext() && scanner.hasNextLine()) {
        int count = scanner.nextInt();
        int fromPileIdx = scanner.nextInt();
        int toPileIdx = scanner.nextInt();
        log.info("move " + count + " from " + fromPileIdx + " to " + toPileIdx);
        // piles are 1-indexed
        moveCrates(count, fromPileIdx - 1, toPileIdx - 1);
      }
      stackTops = readStackTops(piles);
      scanner.close();
    } catch (Exception e) {
      e.printStackTrace();
    }

  }

  public String printStackTops() {
    return stackTops;
  }

  private static final class Pile {

    @Getter
    private Stack<String> stack;

    public Pile(List<String> listOfElements) {
      stack = new Stack<>();
      listOfElements.forEach(stack::push);
    }

    public String pop() {
      return stack.pop();
    }

    public void push(String s) {
      stack.push(s);
    }

    public String peek() {
      return stack.peek();
    }

  }

}
