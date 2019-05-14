import java.util.ArrayList;
import java.util.HashMap;
import java.util.TreeMap;
import java.util.stream.Collectors;

public class RangeComparator {
  private Range first;
  private Range second;

  public class ComparisonResults {
    public Range first;
    public Range second;
    public int trials;
    public int wins;
    public int losses;
    public int ties;
    public double equity;
  }

  public RangeComparator(Range first, Range second) {
    this.first = first;
    this.second = second;
  }

  public ComparisonResults compare(int trials) {
    int wins = 0;
    int losses = 0;
    int ties = 0;
    for (int i = 0; i < trials; i++) {
      Hand2 hand1, hand2;
      do {
        hand1 = first.sample();
        hand2 = second.sample();
      } while (hand1.conflicts(hand2));
      Deck deck = new Deck(hand1, hand2);
      Board board = new Board(deck);
      Hand7 full1 = new Hand7(hand1, board);
      Hand7 full2 = new Hand7(hand2, board);
      int comp = full1.compareTo(full2);
      if (comp > 0) wins++;
      else if (comp < 0) losses++;
      else ties++;
    }
    ComparisonResults results = new ComparisonResults();
    results.trials = trials;
    results.first = first;
    results.second = second;
    results.wins = wins;
    results.losses = losses;
    results.ties = ties;
    results.equity = (wins + .5 * ties) / trials;
    return results;
  }

  public static void main(String[] args) {
    HashMap<String, Double> equities = new HashMap<>();
    for (HandType type : Range.ALL.getTypes()) {
      Range r = new Range(type);
      RangeComparator comp = new RangeComparator(r, Range.ALL);
      double equity = comp.compare(10000000).equity;
      equities.put(r.toString(), equity);
      System.out.println(r.toString() + ": " + equity);
    }
    System.out.println(equities.entrySet().stream()
        .sorted((e1, e2) -> (int) Math.signum(e2.getValue() - e1.getValue()))
        .collect(Collectors.toList()));
  }
}
