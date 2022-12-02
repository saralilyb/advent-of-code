package net.jtth;

import java.io.FileReader;
import java.io.IOException;
import java.io.StreamTokenizer;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import lombok.Getter;

public final class Day1 {

  private final List<Elf> elves;

  public Day1(String data) throws IOException {
    this.elves = this.parseElfData(data)
        .stream()
        .sorted(Comparator.comparingLong(Elf::getSum).reversed())
        .collect(Collectors.toList());
  }

  public Long getBestSum() {
    return this.elves.get(0).sum;
  }

  public List<Long> getTop3Sums() {
    return this.elves.stream()
        .limit(3)
        .map(Elf::getSum)
        .collect(Collectors.toList());
  }

  public Long getTop3SumsSummed() {
    return this.getTop3Sums().stream().mapToLong(Long::longValue).sum();
  }

  private List<Elf> parseElfData(String file) throws IOException {
    FileReader reader = new FileReader(file);
    StreamTokenizer tokenizer = new StreamTokenizer(reader);
    tokenizer.eolIsSignificant(true);

    List<Elf> elves = new ArrayList<>();

    int currentToken = tokenizer.nextToken();

    Elf elf = new Elf();
    boolean lastLineWasEOL = false;
    while (currentToken != StreamTokenizer.TT_EOF) {

      if (tokenizer.ttype == StreamTokenizer.TT_NUMBER) {
        lastLineWasEOL = false;
        elf.data.add((long) tokenizer.nval);
      } else if (tokenizer.ttype == StreamTokenizer.TT_EOL) {
        if (lastLineWasEOL) {
          elf.sumData();
          elves.add(elf);
          elf = new Elf();
          lastLineWasEOL = false;
        } else {
          lastLineWasEOL = true;
        }
      }

      currentToken = tokenizer.nextToken();
    }

    return elves;
  }

  private static final class Elf {

    @Getter
    public Long sum;
    public List<Long> data = new ArrayList<>();

    public void sumData() {
      this.sum = this.data.stream().mapToLong(Long::longValue).sum();
    }

    @Override
    public String toString() {
      return "Elf{" +
          "sum=" + sum +
          ", data=" + data +
          '}';
    }
  }

}
