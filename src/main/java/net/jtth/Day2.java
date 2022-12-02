package net.jtth;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class Day2 {

  private final Map<String, Move> encoding = new HashMap<>();
  private final Map<Move, Long> myMoveScores = new HashMap<>();
  private final Map<GameResult, Long> scoringSystem = new HashMap<>();
  private final Map<String, GameResult> desiredResultsEncoding = new HashMap<>();
  private final Map<Move, Move> moveBeatsMove = new HashMap<>();
  private final List<GameRecord> gameRecords;

  public Day2(String dataFile, boolean question) {
    this.initEncoding();
    this.initMyMoveScores();
    this.initScoringSystem();
    this.initMoveBeatsMove();
    this.initDesiredResults();
    this.gameRecords = parseDataIntoGames(dataFile, question);
  }

  public Long getMyHighScore() {
    return this.gameRecords.stream()
        .map(GameRecord::score)
        .mapToLong(Long::longValue)
        .sum();
  }

  public GameRecord playRpsGame21(Move m1, Move m2) throws InvalidGameStateException {
    Move moveILoseWith = this.moveBeatsMove.get(m1);
    Move moveIWinTo = this.moveBeatsMove.get(m2);
    GameResult result;
    if (m1 == m2) {
      result = GameResult.DRAW;
    } else if (m2 == moveILoseWith) {
      result = GameResult.LOST;
    } else if (m1 == moveIWinTo) {
      result = GameResult.WIN;
    } else {
      throw new InvalidGameStateException("move is not legal! moves are m1: " + m1 + " m2: " + m2);
    }
    Long score = this.myMoveScores.get(m2) + this.scoringSystem.get(result);
    return new GameRecord(m1, m2, result, score);
  }

  private GameRecord playRpsGame22(Move m1, GameResult desiredResult)
      throws InvalidGameStateException {

    Move myMove = m1;
    switch (desiredResult) {
      case WIN -> myMove = this.moveBeatsMove.entrySet().stream()
          .filter(x -> x.getValue() == m1)
          .map(Map.Entry::getKey).toList().get(0);
      case DRAW -> {
      }
      case LOST -> myMove = this.moveBeatsMove.get(m1);
    }
    return this.playRpsGame21(m1, myMove);
  }

  private List<GameRecord> parseDataIntoGames(String dataFile, boolean question1) {
    List<GameRecord> _gameRecords = new ArrayList<>();

    try {
      Scanner scanner = new Scanner(new FileReader(dataFile));
      while (scanner.hasNextLine()) {
        List<String> data = List.of(scanner.nextLine().split(" "));
        GameRecord gameRecord;
        Move m1 = this.encoding.get(data.get(0));
        if (question1) {
          Move m2 = this.encoding.get(data.get(1));
          gameRecord = playRpsGame21(m1, m2);
          _gameRecords.add(gameRecord);
        } else {
          GameResult desiredResult = this.desiredResultsEncoding.get(data.get(1));
          gameRecord = playRpsGame22(m1, desiredResult);
        }
        _gameRecords.add(gameRecord);
      }
      scanner.close();
    } catch (FileNotFoundException | InvalidGameStateException err) {
      err.printStackTrace();
    }

    return _gameRecords;
  }

  private void initEncoding() {
    this.encoding.put("A", Move.ROCK);
    this.encoding.put("B", Move.PAPER);
    this.encoding.put("C", Move.SCISSORS);

    this.encoding.put("X", Move.ROCK);
    this.encoding.put("Y", Move.PAPER);
    this.encoding.put("Z", Move.SCISSORS);
  }

  private void initMyMoveScores() {
    this.myMoveScores.put(Move.ROCK, 1L);
    this.myMoveScores.put(Move.PAPER, 2L);
    this.myMoveScores.put(Move.SCISSORS, 3L);
  }

  private void initScoringSystem() {
    this.scoringSystem.put(Day2.GameResult.LOST, 0L);
    this.scoringSystem.put(Day2.GameResult.DRAW, 3L);
    this.scoringSystem.put(Day2.GameResult.WIN, 6L);
  }

  private void initMoveBeatsMove() {
    this.moveBeatsMove.put(Move.PAPER, Move.ROCK);
    this.moveBeatsMove.put(Move.SCISSORS, Move.PAPER);
    this.moveBeatsMove.put(Move.ROCK, Move.SCISSORS);
  }

  private void initDesiredResults() {
    this.desiredResultsEncoding.put("X", GameResult.LOST);
    this.desiredResultsEncoding.put("Y", GameResult.DRAW);
    this.desiredResultsEncoding.put("Z", GameResult.WIN);
  }

  private enum GameResult {
    LOST, DRAW, WIN
  }

  private enum Move {
    ROCK, PAPER, SCISSORS
  }

  private record GameRecord(Move moveP1, Move moveP2, GameResult result, Long score) {

  }

  private static class InvalidGameStateException extends Exception {

    public InvalidGameStateException(String message) {
      super(message);
    }
  }
}
