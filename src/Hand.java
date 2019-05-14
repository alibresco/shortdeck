import java.util.List;
import java.util.stream.Collectors;

public abstract class Hand {
  protected List<Card> cards;

  @Override
  public String toString() {
    return String.join("", cards.stream()
        .map(Card::toString).collect(Collectors.toList()));
  }
}
