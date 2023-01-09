package net.jtth.aoc2022;

import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.LongStream;

public final class Day3 {

  Map<Character, Long> scoreTable;
  List<Character> itemsInCommon = new ArrayList<>();

  List<Character> groupItems = new ArrayList<>();

  Long score = 0L;
  Long groupScore = 0L;

  public Day3(String dataFile) {
    this.scoreTable = this.initScores();
    parseDataFile(dataFile);
  }

  @Override
  public String toString() {
    return "score = " + score;
  }

  public Long printScore() {
    return this.score;
  }

  public Long printGroupScore() {
    return this.groupScore;
  }

  private Long scoreChar(Character c) {
    return scoreTable.get(c);
  }

  private Character characterInBothCompartments(String compartment1, String compartment2)
      throws Exception {
    Set<Character> c1Set = stringToCharacterSet(compartment1);
    Set<Character> c2Set = stringToCharacterSet(compartment2);
    Set<Character> intersection = new HashSet<>(c1Set);
    intersection.retainAll(c2Set);
    if ((long) intersection.size() == 1) {
      return intersection.iterator().next();
    } else {
      throw new Exception("Rucksack compartments share more than one item:" + intersection);
    }
  }

  private List<String> splitRucksackIntoCompartments(String rucksack) throws Exception {
    if (rucksack.length() % 2 == 0) {
      int splitPoint = rucksack.length() / 2;
      return List.of(rucksack.substring(0, splitPoint), rucksack.substring(splitPoint));
    } else {
      throw new Exception("Rucksack does not have even number of items!");
    }
  }

  private Long scoreItemsInCommon(List<Character> items) {
    return items.stream()
        .mapToLong(this::scoreChar)
        .reduce(0L, Long::sum);
  }

  private Set<Character> stringToCharacterSet(String s) {
    return s.chars().mapToObj((v) -> (char) v).collect(Collectors.toSet());
  }

  private Character sharedItemInElfGroup(List<String> elfGroupItems) throws Exception {
    Set<Character> itemSet = new HashSet<>(stringToCharacterSet(elfGroupItems.get(0)));
    for (String elfItems : elfGroupItems) {
      itemSet.retainAll(stringToCharacterSet(elfItems));
    }
    if (itemSet.size() == 1) {
      return itemSet.iterator().next();
    } else {
      throw new Exception("No shared item between elf groups, or too many! " + elfGroupItems);
    }
  }

  private void parseDataFile(String dataFile) {
    try {
      Scanner scanner = new Scanner(new FileReader(dataFile));
      List<String> elfGroup = new ArrayList<>();
      while (scanner.hasNext()) {
        // Item scores, 3-1
        String data = scanner.nextLine();
        List<String> compartments = splitRucksackIntoCompartments(data);
        Character itemInCommon = characterInBothCompartments(compartments.get(0),
            compartments.get(1));
        this.itemsInCommon.add(itemInCommon);

        // Badges, 3-2
        elfGroup.add(data);
        if (elfGroup.size() == 3) {
          Character sharedGroupItem = sharedItemInElfGroup(elfGroup);
          this.groupItems.add(sharedGroupItem);
          elfGroup.clear();
        }
      }
      this.score = scoreItemsInCommon(this.itemsInCommon);
      this.groupScore = scoreItemsInCommon(this.groupItems);
      scanner.close();
    } catch (Exception e) {
      e.printStackTrace();
    }

  }

  private List<Character> makeAlphabet() {
    List<Character> lowercase = IntStream.rangeClosed('a', 'z')
        .boxed()
        .map((v) -> (char) (int) v)
        .toList();
    List<Character> uppercase = IntStream.rangeClosed('A', 'Z')
        .boxed()
        .map((v) -> (char) (int) v)
        .toList();
    List<Character> union = new ArrayList<>();
    union.addAll(lowercase);
    union.addAll(uppercase);
    return union;
  }

  private Map<Character, Long> initScores() {
    List<Character> alphabet = makeAlphabet();
//    keep these separate in case we have to change how the scores work
    List<Long> scores = LongStream.rangeClosed(1, alphabet.size()).boxed().toList();
    return IntStream.range(0, Math.min(alphabet.size(), scores.size()))
        .boxed()
        .collect(Collectors.toMap(alphabet::get, scores::get));
  }
}
