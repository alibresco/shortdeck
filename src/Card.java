import java.util.Objects;

public class Card implements Comparable<Card>
{
  private Rank rank;
  private Suit suit;

  private Card() {}

  public Card(Rank rank, Suit suit) {
    this.rank = rank;
    this.suit = suit;
  }

  public static Card fromString(String card) {
    if (card.length() != 2) throw new IllegalArgumentException("Invalid card string: " + card);
    Rank rank = Rank.fromChar(card.charAt(0));
    Suit suit = Suit.fromChar(card.charAt(1));
    return new Card(rank, suit);
  }

  public Rank getRank() {
    return rank;
  }

  public Suit getSuit() {
    return suit;
  }

  @Override
  public String toString() {
    return rank.toString() + suit.toString();
  }

  @Override
  public int hashCode() {
    return Objects.hash(rank, suit);
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Card card = (Card) o;
    return rank == card.rank && suit == card.suit;
  }

  @Override
  public int compareTo(Card c) {
    return rank.ordinal() - c.rank.ordinal();
  }

}
