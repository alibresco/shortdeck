import java.util.ArrayList;
import java.util.List;

public class Hand2 extends Hand {
  private Hand2() {}

  public Hand2(List<Card> cards) {
    if (cards.size() != 2) {
      throw new IllegalArgumentException("Hand must be of size 2");
    }
    this.cards = new ArrayList<>(cards);
  }

  public Hand2(Card first, Card second) {
    this.cards = new ArrayList<>(2);
    cards.add(first);
    cards.add(second);
  }

  public Hand2(Deck deck) {
    this(deck.next(), deck.next());
  }

  public boolean conflicts(Hand2 o) {
    return cards.get(0).equals(o.cards.get(0)) || cards.get(0).equals(o.cards.get(1)) ||
        cards.get(1).equals(o.cards.get(0)) || cards.get(1).equals(o.cards.get(1));
  }
}
