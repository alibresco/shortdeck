import java.util.Objects;

public class HandType {
  public enum Suitedness {
    SUITED,
    OFFSUIT,
    POCKET_PAIR
  }

  private Suitedness suitedness;
  private Rank highRank;
  private Rank lowRank;

  public HandType(Rank highRank, Rank lowRank, Suitedness suitedness) {
    if (highRank.ordinal() >= lowRank.ordinal()) {
      this.highRank = highRank;
      this.lowRank = lowRank;
    } else {
      this.highRank = lowRank;
      this.lowRank = highRank;
    }
    this.suitedness = suitedness;
  }

  public HandType(String descriptor) {
    if (descriptor.length() == 2) {
      suitedness = Suitedness.POCKET_PAIR;
    } else if (descriptor.charAt(2) == 'o') {
      suitedness = Suitedness.OFFSUIT;
    } else {
      suitedness = Suitedness.SUITED;
    }
    Rank highRank = Rank.fromChar(descriptor.charAt(0));
    Rank lowRank = Rank.fromChar(descriptor.charAt(1));
    if (highRank.ordinal() >= lowRank.ordinal()) {
      this.highRank = highRank;
      this.lowRank = lowRank;
    } else {
      this.highRank = lowRank;
      this.lowRank = highRank;
    }
  }

  public int combos() {
    switch (suitedness) {
      case SUITED: return 4;
      case OFFSUIT: return 12;
      case POCKET_PAIR: return 6;
    }
    return 0;
  }

  public Hand2 sample() {
    Card first = new Card(highRank, Suit.sample());
    Suit secondSuit = null;
    if (suitedness == Suitedness.SUITED) {
      secondSuit = first.getSuit();
    } else {
      boolean done = false;
      while (!done) {
        secondSuit = Suit.sample();
        if (secondSuit != first.getSuit()) {
          done = true;
        }
      }
    }
    Card second = new Card(lowRank, secondSuit);
    return new Hand2(first, second);
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    HandType handType = (HandType) o;
    return suitedness == handType.suitedness &&
        highRank == handType.highRank &&
        lowRank == handType.lowRank;
  }

  @Override
  public int hashCode() {
    return Objects.hash(suitedness, highRank, lowRank);
  }

  @Override
  public String toString() {
    String start = highRank.toString() + lowRank;
    if (suitedness == Suitedness.SUITED) {
      return start + "s";
    } else if (suitedness == Suitedness.OFFSUIT) {
      return start + "o";
    } else {
      return start;
    }
  }
}
