import java.util.ArrayList;

public class NashFinder {

  private static double computeNeededShoveEquity(int stackSizeSB, Range caller) {
    double callingFrequency = caller.callingFrequency();
    return (callingFrequency * stackSizeSB + 2 * callingFrequency - 3)
        / (2 * callingFrequency * stackSizeSB);
  }

  private static double computeNeededCallEquity(int stackSizeSB) {
    return (double) (stackSizeSB - 2) / (2 * stackSizeSB);
  }

  public static Range findBestResponse(int stackSizeSB, Range other, boolean shove,
      Range wider, Range tighter) {
    double neededEquity = shove ?
        computeNeededShoveEquity(stackSizeSB, other) : computeNeededCallEquity(stackSizeSB);
    System.out.println((shove ? "shover" : "caller") + " needs: " + neededEquity);
    ArrayList<HandType> bestResponse = new ArrayList<>();
    if (wider == null) wider = Range.ALL;
    for (HandType type : wider.getTypes()) {
      if (tighter != null && tighter.getTypes().contains(type)) {
        bestResponse.add(type);
        continue;
      }
      Range r = new Range(type);
      RangeComparator comp = new RangeComparator(r, other);
      double equity = comp.compare(1000000).equity;
      if (equity > neededEquity) {
        bestResponse.add(type);
      }
    }
    Range output = new Range(bestResponse);
    System.out.println(output.toPrettyString());
//    System.out.println(equities.entrySet().stream()
//        .sorted((e1, e2) -> (int) Math.signum(e2.getValue() - e1.getValue()))
//        .collect(Collectors.toList()));
    return output;
  }

  public static void main(String[] args) {
    Range oldShove = null;
    Range call = Solutions.CALL_19BB;
    System.out.println(call.toPrettyString());
    while (true) {
      Range shove = findBestResponse(19 * 2, call, true, Solutions.SHOVE_18BB, null);
      if (shove.equals(oldShove)) break;
      oldShove = shove;
      call = findBestResponse(19 * 2, shove, false, Solutions.CALL_18BB, null);
    }
  }
}
