import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Hand5 extends Hand implements Comparable<Hand5> {
  private HandRank rank;
  private Boolean flush;
  private Boolean straight;
  private List<HistEntry> histogram;

  private Hand5() {}

  public Hand5(List<Card> cards) {
    if (cards.size() != 5) {
      throw new IllegalArgumentException("Hand must be of size 5");
    }
    this.cards = new ArrayList<>(cards);
    Collections.sort(this.cards);
    makeHistogram();
    flush = isFlush();
    straight = isStraight();
  }

  public HandRank rank() {
    if (rank != null) return rank;
    if (flush && straight) rank = HandRank.STRAIGHT_FLUSH;
    else if (isFourOfAKind()) rank = HandRank.FOUR_OF_A_KIND;
    else if (flush) rank = HandRank.FLUSH;
    else if (isFullHouse()) rank = HandRank.FULL_HOUSE;
    else if (straight) rank = HandRank.STRAIGHT;
    else if (isThreeOfAKind()) rank = HandRank.THREE_OF_A_KIND;
    else if (isTwoPair()) rank = HandRank.TWO_PAIR;
    else if (isPair()) rank = HandRank.PAIR;
    else rank = HandRank.HIGH_CARD;
    return rank;
  }

  @Override
  public int compareTo(Hand5 h) {
    HandRank rank1 = rank();
    HandRank rank2 = h.rank();
    int diff = rank1.ordinal() - rank2.ordinal();
    if (diff != 0) return diff;
    switch (rank1) {
      case STRAIGHT_FLUSH:
        return compareStraightFlush(h);
      case FOUR_OF_A_KIND:
        return compareFourOfAKind(h);
      case FLUSH:
        return compareFlush(h);
      case FULL_HOUSE:
        return compareFullHouse(h);
      case STRAIGHT:
        return compareStraight(h);
      case THREE_OF_A_KIND:
        return compareThreeOfAKind(h);
      case TWO_PAIR:
        return compareTwoPair(h);
      case PAIR:
        return comparePair(h);
      case HIGH_CARD:
        return compareHighCard(h);
    }
    throw new RuntimeException("Should never happen");
  }

  public static Hand5 fromString(String cards) {
    if (cards.length() != 10) throw new IllegalArgumentException("Invalid hand: " + cards);
    List<Card> cardsBuild = new ArrayList<>(5);
    for (int i = 0; i < 5; i++) {
      cardsBuild.add(Card.fromString(cards.substring(i * 2, i * 2 + 2)));
    }
    return new Hand5(cardsBuild);
  }

  private void makeHistogram() {
    int[] hist = new int[9];
    for (Card c : cards) {
      hist[c.getRank().ordinal()]++;
    }
    histogram = new ArrayList<>();
    for (int i = 0; i < hist.length; i++) {
      if (hist[i] != 0) {
        histogram.add(new HistEntry(Rank.values()[i], hist[i]));
      }
    }
    Collections.sort(histogram);
    Collections.reverse(histogram);
  }

  private int compareStraightFlush(Hand5 h) {
    return compareStraight(h);
  }

  private int compareFourOfAKind(Hand5 h) {
    int comp = histogram.get(0).compareTo(h.histogram.get(0));
    if (comp == 0) {
      return histogram.get(1).compareTo(h.histogram.get(1));
    }
    return comp;
  }

  private int compareFullHouse(Hand5 h) {
    int comp = histogram.get(0).compareTo(h.histogram.get(0));
    if (comp == 0) {
      return histogram.get(1).compareTo(h.histogram.get(1));
    }
    return comp;
  }

  private int compareFlush(Hand5 h) {
    for (int i = 4; i >= 0; i--) {
      int comp = cards.get(i).compareTo(h.cards.get(i));
      if (comp != 0) {
        return comp;
      }
    }
    return 0;
  }

  private int compareStraight(Hand5 h) {
    int comp = cards.get(0).compareTo(h.cards.get(0));
    if (comp == 0) return h.cards.get(4).compareTo(cards.get(4));
    return comp;
  }

  private int compareThreeOfAKind(Hand5 h) {
    int comp = histogram.get(0).compareTo(h.histogram.get(0));
    if (comp == 0) {
      comp = histogram.get(1).compareTo(h.histogram.get(1));
      if (comp == 0) {
        return histogram.get(2).compareTo(h.histogram.get(2));
      }
    }
    return comp;
  }

  private int compareTwoPair(Hand5 h) {
    int comp = histogram.get(0).compareTo(h.histogram.get(0));
    if (comp == 0) {
      comp = histogram.get(1).compareTo(h.histogram.get(1));
      if (comp == 0) {
        return histogram.get(2).compareTo(h.histogram.get(2));
      }
    }
    return comp;
  }

  private int comparePair(Hand5 h) {
    int comp = histogram.get(0).compareTo(h.histogram.get(0));
    if (comp == 0) {
      comp = histogram.get(1).compareTo(h.histogram.get(1));
      if (comp == 0) {
        comp = histogram.get(2).compareTo(h.histogram.get(2));
        if (comp == 0) {
          return histogram.get(3).compareTo(h.histogram.get(3));
        }
      }
    }
    return comp;
  }

  private int compareHighCard(Hand5 h) {
    for (int i = 4; i >= 0; i--) {
      int comp = cards.get(i).compareTo(h.cards.get(i));
      if (comp != 0) return comp;
    }
    return 0;
  }

  private boolean isFourOfAKind() {
    return histogram.get(0).getCount() == 4;
  }

  private boolean isFullHouse() {
    return histogram.get(0).getCount() == 3 && histogram.get(1).getCount() == 2;
  }

  private boolean isFlush() {
    for (int i = 1; i < cards.size(); i++) {
      if (cards.get(i).getSuit() != cards.get(0).getSuit()) {
        return false;
      }
    }
    return true;
  }

  private boolean isWheel() {
    return cards.get(0).getRank() == Rank.SIX
        && cards.get(1).getRank() == Rank.SEVEN
        && cards.get(2).getRank() == Rank.EIGHT
        && cards.get(3).getRank() == Rank.NINE
        && cards.get(4).getRank() == Rank.ACE;
  }

  private boolean isStraight() {
    return isWheel() || histogram.get(0).getCount() == 1
        && cards.get(4).getRank().ordinal() - cards.get(0).getRank().ordinal() == 4;
  }

  private boolean isThreeOfAKind() {
    return histogram.get(0).getCount() == 3;
  }

  private boolean isTwoPair() {
    return histogram.get(0).getCount() == 2 && histogram.get(1).getCount() == 2;
  }

  private boolean isPair() {
    return histogram.get(0).getCount() == 2;
  }

  public static void main(String[] args) {
    Hand5 sf = Hand5.fromString("kdadtdjdqd");
    System.out.println(sf);
    System.out.println(sf.rank());

    Hand5 quads = Hand5.fromString("kd6dkhkskc");
    System.out.println(quads);
    System.out.println(quads.rank());

    Hand5 flush = Hand5.fromString("kd6d8dqdad");
    System.out.println(flush);
    System.out.println(flush.rank());

    Hand5 boat = Hand5.fromString("kd6d6ckskc");
    System.out.println(boat);
    System.out.println(boat.rank());

    Hand5 straight = Hand5.fromString("kdqdjcastc");
    System.out.println(straight);
    System.out.println(straight.rank());

    Hand5 wheel = Hand5.fromString("8d7d9cas6c");
    System.out.println(wheel);
    System.out.println(wheel.rank());

    Hand5 trips = Hand5.fromString("8d7d8c8s6c");
    System.out.println(trips);
    System.out.println(trips.rank());

    Hand5 tp = Hand5.fromString("8d7d8c7s6c");
    System.out.println(tp);
    System.out.println(tp.rank());

    Hand5 pair = Hand5.fromString("8dad8cks6c");
    System.out.println(pair);
    System.out.println(pair.rank());

    Hand5 hc = Hand5.fromString("8dad7cks6c");
    System.out.println(hc);
    System.out.println(hc.rank());


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
