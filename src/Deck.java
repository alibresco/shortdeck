import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class Deck {
  private List<Card> deck;
  private int pointer;

  public Deck() {
    this(Collections.emptySet());
  }

  public Deck(Set<Card> blocked) {
    deck = new ArrayList<>(36 - blocked.size());
    for (Suit s : Suit.values()) {
      for (Rank r : Rank.values()) {
        Card c = new Card(r, s);
        if (blocked.contains(c)) continue;
        deck.add(c);
      }
    }
    Collections.shuffle(deck);
  }

  public Deck(Hand2... blocked) {
    this(Arrays.stream(blocked).flatMap(h -> h.cards.stream()).collect(Collectors.toSet()));
  }

  public Card next() {
    return deck.get(pointer++);
  }

  public int size() {
    return deck.size() - pointer;
  }

  @Override
  public String toString() {
    return String.join("", deck.stream()
        .skip(pointer)
        .map(Card::toString)
        .collect(Collectors.toList()));
  }

  public static void main(String[] args) {
    Deck d1 = new Deck();
    System.out.println(d1);
    System.out.println(d1.size());
    System.out.println(d1.next());
    System.out.println(d1);
    System.out.println(d1.size());
    System.out.println();

    Set<Card> blockers = new HashSet<>();
    blockers.add(new Card(Rank.KING, Suit.CLUBS));
    blockers.add(new Card(Rank.KING, Suit.DIAMONDS));
    Deck d2 = new Deck(blockers);
    System.out.println(d2);
    System.out.println(d2.size());
    System.out.println(d2.next());
    System.out.println(d2);
    System.out.println(d2.size());

  }
}
