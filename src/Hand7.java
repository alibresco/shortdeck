import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class Hand7 extends Hand implements Comparable<Hand7> {
  // statically computed for speed
  private static final int[][] COMBINATIONS = {
      {0, 1, 2, 3, 4},
      {0, 1, 2, 3, 5},
      {0, 1, 2, 3, 6},
      {0, 1, 2, 4, 5},
      {0, 1, 2, 4, 6},
      {0, 1, 2, 5, 6},
      {0, 1, 3, 4, 5},
      {0, 1, 3, 4, 6},
      {0, 1, 3, 5, 6},
      {0, 1, 4, 5, 6},
      {0, 2, 3, 4, 5},
      {0, 2, 3, 4, 6},
      {0, 2, 3, 5, 6},
      {0, 2, 4, 5, 6},
      {0, 3, 4, 5, 6},
      {1, 2, 3, 4, 5},
      {1, 2, 3, 4, 6},
      {1, 2, 3, 5, 6},
      {1, 2, 4, 5, 6},
      {1, 3, 4, 5, 6},
      {2, 3, 4, 5, 6}
  };

  private Hand5 best5;

  private Hand7() {}

  public Hand7(List<Card> cards) {
    if (cards.size() != 7) {
      throw new IllegalArgumentException("Hand must be of size 7");
    }
    this.cards = new ArrayList<>(cards);
    findBestFive();
  }

  public Hand7(Hand2 holeCards, Board board) {
    this.cards = new ArrayList<>(7);
    cards.addAll(holeCards.cards);
    cards.addAll(board.cards);
    findBestFive();
  }

  public static Hand7 fromString(String cards) {
    if (cards.length() != 14) throw new IllegalArgumentException("Invalid hand: " + cards);
    List<Card> cardsBuild = new ArrayList<>(7);
    for (int i = 0; i < 7; i++) {
      cardsBuild.add(Card.fromString(cards.substring(i * 2, i * 2 + 2)));
    }
    return new Hand7(cardsBuild);
  }

  public HandRank rank() {
    return best5.rank();
  }

  public Hand5 getBest5() {
    return best5;
  }

  @Override
  public int compareTo(Hand7 o) {
    return best5.compareTo(o.best5);
  }

  private void findBestFive() {
    List<Hand5> all = new ArrayList<>(21);
    for (int[] comb : COMBINATIONS) {
      all.add(new Hand5(Arrays.stream(comb).mapToObj(cards::get).collect(Collectors.toList())));
    }
    Collections.sort(all);
    best5 = all.get(all.size() - 1);
  }

  public static void main(String[] args) {
    Hand7 sf = Hand7.fromString("ackd8cadtdjdqd");
    System.out.println(sf);
    System.out.println(sf.getBest5());
    System.out.println(sf.rank());
    System.out.println();

    Hand7 quads = Hand7.fromString("kd6dkhqdksqhkc");
    System.out.println(quads);
    System.out.println(quads.getBest5());
    System.out.println(quads.rank());
    System.out.println();

    Hand7 flush = Hand7.fromString("kd6d6h8dqd6cad");
    System.out.println(flush);
    System.out.println(flush.getBest5());
    System.out.println(flush.rank());
    System.out.println();

    Hand7 boat = Hand7.fromString("7hkd6d6ckskc7d");
    System.out.println(boat);
    System.out.println(boat.getBest5());
    System.out.println(boat.rank());
    System.out.println();

    Hand7 straight = Hand7.fromString("8d7d6h9ctcas6c");
    System.out.println(straight);
    System.out.println(straight.getBest5());
    System.out.println(straight.rank());
    System.out.println();

    Hand7 wheel = Hand7.fromString("8d7d6h9cjcas6c");
    System.out.println(wheel);
    System.out.println(wheel.getBest5());
    System.out.println(wheel.rank());
    System.out.println();

    Hand7 trips = Hand7.fromString("jcqd8d7d8c8s6c");
    System.out.println(trips);
    System.out.println(trips.getBest5());
    System.out.println(trips.rank());
    System.out.println();

    Hand7 tp = Hand7.fromString("8d7d8cqd7s6cjc");
    System.out.println(tp);
    System.out.println(tp.getBest5());
    System.out.println(tp.rank());
    System.out.println();

    Hand7 pair = Hand7.fromString("9hjh8dad8cks6c");
    System.out.println(pair);
    System.out.println(pair.getBest5());
    System.out.println(pair.rank());
    System.out.println();

    Hand7 hc = Hand7.fromString("8dad7cksts6cqd");
    System.out.println(hc);
    System.out.println(hc.getBest5());
    System.out.println(hc.rank());
    System.out.println();

    System.out.println(sf.compareTo(quads) > 0);
    System.out.println(quads.compareTo(flush) > 0);
    System.out.println(flush.compareTo(boat) > 0);
    System.out.println(boat.compareTo(straight) > 0);
    System.out.println(straight.compareTo(wheel) > 0);
    System.out.println(wheel.compareTo(trips) > 0);
    System.out.println(trips.compareTo(tp) > 0);
    System.out.println(tp.compareTo(pair) > 0);
    System.out.println(pair.compareTo(hc) > 0);
  }
}
