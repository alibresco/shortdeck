import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Board extends Hand {

  public Board(List<Card> cards) {
    if (cards.size() != 5) {
      throw new IllegalArgumentException("Board must be of size 5");
    }
    this.cards = new ArrayList<>(cards);
  }

  public Board(Deck deck) {
    this(IntStream.range(0, 5).mapToObj(i -> deck.next()).collect(Collectors.toList()));
  }

  public static void main(String[] args) {
    Deck deck = new Deck();
    System.out.println(deck);
    Hand2 h1 = new Hand2(deck);
    Hand2 h2 = new Hand2(deck);
    Board board = new Board(deck);
    Hand7 full1 = new Hand7(h1, board);
    Hand7 full2 = new Hand7(h2, board);
    System.out.println(h1);
    System.out.println(h2);
    System.out.println(board);
    System.out.println("Player 1 has a " + full1.rank() + " " + full1.getBest5());
    System.out.println("Player 2 has a " + full2.rank() + " " + full2.getBest5());
    int comp = full1.compareTo(full2);
    if (comp > 0) {
      System.out.println("Player 1 wins");
    } else if (comp < 0) {
      System.out.println("Player 2 wins");
    } else {
      System.out.println("It's a tie");
    }
    System.out.println();
    simulate();
  }

  public static void simulate() {
    int wins = 0;
    int losses = 0;
    int ties = 0;
    for (int i = 0; i < 100000; i++) {
      Deck deck = new Deck();
      Hand2 h1 = new Hand2(deck);
      Hand2 h2 = new Hand2(deck);
      Board board = new Board(deck);
      Hand7 full1 = new Hand7(h1, board);
      Hand7 full2 = new Hand7(h2, board);
      int comp = full1.compareTo(full2);
      int neg = full2.compareTo(full1);
      if ((comp > 0 && neg >= 0) || (comp < 0 && neg <= 0) || (comp == 0 && neg != 0)) {
        System.out.println("error");
        System.out.println("wins = " + wins);
        System.out.println(h1);
        System.out.println(h2);
        System.out.println(board);
        System.out.println(comp);
        System.out.println(neg);
        System.exit(1);
      }
      if (comp > 0) {
        wins++;
      } else if (comp < 0) {
        losses++;
      } else {
        ties++;
      }
    }
    System.out.printf("wins: %d, losses: %d, ties: %d\n", wins, losses, ties);
    System.out.println(1. * wins / (wins + losses + ties));
    System.out.println(1. * losses / (wins + losses + ties));
    System.out.println(1. * ties / (wins + losses + ties));
  }

}
