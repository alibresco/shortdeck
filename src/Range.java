import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import org.apache.commons.math3.distribution.EnumeratedDistribution;
import org.apache.commons.math3.util.Pair;

public class Range {
  private Set<HandType> types;
  private EnumeratedDistribution<HandType> dist;

  public static final Range ALL;
  static {
    ArrayList<HandType> types = new ArrayList<>();
    for (int i = 0; i < Rank.values().length; i++) {
      for (int j = i + 1; j < Rank.values().length; j++) {
        Rank lower = Rank.values()[i];
        Rank higher = Rank.values()[j];
        types.add(new HandType(higher, lower, HandType.Suitedness.OFFSUIT));
        types.add(new HandType(higher, lower, HandType.Suitedness.SUITED));
      }
    }
    for (Rank r : Rank.values()) {
      types.add(new HandType(r, r, HandType.Suitedness.POCKET_PAIR));
    }
    ALL = new Range(types);
  }


  public Range(Collection<HandType> types) {
    this.types = new HashSet<>(types);
    prepareDist();
  }

  public Range(String range) {
    range = range.replaceAll("\\s", "");
    range = range.replaceAll("-", "");
    types = Arrays.stream(range.split("[,|]"))
        .filter(s -> !s.isEmpty())
        .map(HandType::new).collect(Collectors.toSet());
    prepareDist();
  }

  public Range(HandType... types) {
    this(Arrays.asList(types));
  }

  private void prepareDist() {
    if (dist != null) return;
    dist = new EnumeratedDistribution<>(types.stream()
        .map(t -> new Pair<>(t, (double) t.combos())).collect(Collectors.toList()));
  }

  public Collection<HandType> getTypes() {
    return types;
  }

  public Hand2 sample() {
    HandType type = dist.sample();
    return type.sample();
  }

  public double callingFrequency() {
    int combos = types.stream().map(HandType::combos).reduce(0, Integer::sum);
    int total = 630;
    return (double) combos / total;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Range range = (Range) o;
    return Objects.equals(types, range.types);
  }

  @Override
  public int hashCode() {
    return Objects.hash(types);
  }

  @Override
  public String toString() {
    return types.toString();
  }

  public String toPrettyString() {
    String[][] output = new String[9][9];
    for (int i = 0; i < 9; i++) {
      for (int j = i; j < 9; j++) {
        if (i == j) {
          HandType type = new HandType(Rank.values()[i],
              Rank.values()[i], HandType.Suitedness.POCKET_PAIR);
          if (types.contains(type)) {
            output[8 - i][8 - i] = type.toString() + " ";
          } else {
            output[8 - i][8 - i] = "   ";
          }
        } else {
          HandType offsuit = new HandType(Rank.values()[i],
              Rank.values()[j], HandType.Suitedness.OFFSUIT);
          HandType suited = new HandType(Rank.values()[i],
              Rank.values()[j], HandType.Suitedness.SUITED);
          if (types.contains(offsuit)) {
            output[8 - i][8 - j] = offsuit.toString();
          } else {
            output[8 - i][8 - j] = "   ";
          }
          if (types.contains(suited)) {
            output[8 - j][8 - i] = suited.toString();
          } else {
            output[8 - j][8 - i] = "   ";
          }
        }
      }
    }
    StringBuilder builder = new StringBuilder();
    String divider = "-------------------------------------\n";
    builder.append(divider);
    for (String[] line : output) {
      builder.append("|");
      for (String type : line) {
        builder.append(type);
        builder.append("|");
      }
      builder.append("\n");
      builder.append(divider);
    }
    return builder.toString();
  }

  public static void main(String[] args) {
    String str = "AA, AKo, AKs, AQo, kk";
    Range range = new Range(str);
    System.out.println(range.types);
    System.out.println(range.dist);
    HashMap<HandType, Integer> counter = new HashMap<>();
    for (int i = 0; i < 10; i++) {
      HandType hand = range.dist.sample();
      if (counter.containsKey(hand)) {
        counter.put(hand, counter.get(hand) + 1);
      } else {
        counter.put(hand, 1);
      }
    }
    System.out.println(counter);
    for (int i = 0; i < 10; i++) System.out.println(range.sample());
    System.out.println(range.toPrettyString());
  }
}
