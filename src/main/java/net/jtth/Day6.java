package net.jtth;

import java.io.Reader;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import lombok.Getter;

public class Day6 {

  @Getter
  public long position = 0L;

  public Day6(Reader data) {
    parseData(data, 4);
  }

  public Day6(Reader data, int bufferSize) {
    parseData(data, bufferSize);
  }

  public String toString() {
    return "" + position;
  }

  private void parseData(Reader data, int bufferSize) {
    try (Scanner scanner = new Scanner(data)) {
      scanner.useDelimiter("");
      BlockingQueue<String> queue = new ArrayBlockingQueue<>(bufferSize);
      long counter = 0L;
      while (scanner.hasNext()) {
        String nextCharacter = scanner.next();
        if (!queue.offer(nextCharacter)) {
          if (isUniqueString(queue.stream().toList())) {
            position = counter;
            break;
          }
          queue.take();
          queue.put(nextCharacter);
        }
        counter += 1;
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  private boolean isUniqueString(List<String> buffer) {
    List<String> unique = buffer.stream().distinct().toList();
    return Objects.equals(unique, buffer);
  }

}
