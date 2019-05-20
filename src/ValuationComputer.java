public class ValuationComputer {

  private static double computeShoveEv(double stackSizeSB, double equity,
      double shoveFreq, double callFreq) {
    double foldEv = -1;
    double shoveEv = callFreq * (equity * stackSizeSB - (1 - equity) * stackSizeSB) + (1 - callFreq) * 2;
    return shoveFreq * shoveEv + (1 - shoveFreq) * foldEv;
  }

  private static double computeCallEv(double stackSizeSB, double equity,
      double shoveFreq, double callFreq) {
    double shoverFoldsEv = 1;
    double foldEv = -2;
    double callEv = equity * stackSizeSB - (1 - equity) * stackSizeSB;
    return shoverFoldsEv * (1 - shoveFreq) + shoveFreq * (callFreq * callEv + (1 - callFreq) * foldEv);
  }
  public static void main(String[] args) {
    System.out.println("BB:       NPV       IRR");
    for (int i = 0; i < 20; i++) {
      int bigBlinds = i + 1;
      int smallBlinds = bigBlinds * 2;
      Range shove = Solutions.SHOVES.get(i);
      Range call = Solutions.CALLS.get(i);
      RangeComparator comp = new RangeComparator(shove, call);
      double equity = comp.compare(10000000).equity;
      double shoveEV = computeShoveEv(smallBlinds, equity,
          shove.callingFrequency(), call.callingFrequency()) / 2;
      double callEV = computeCallEv(smallBlinds, 1 - equity,
          shove.callingFrequency(), call.callingFrequency()) / 2;
      double shoveIRR = (shoveEV + bigBlinds) / bigBlinds - 1;
      double callIRR = (callEV + bigBlinds) / bigBlinds - 1;
      System.out.printf("%2d: %9.5f %9.5f\n", bigBlinds, shoveEV, shoveIRR);
//      System.out.println(bigBlinds + " big blinds:");
//      System.out.println("Shove EV: " + shoveEV);
//      System.out.println("Call EV: " + callEV);
//      System.out.println("Shove IRR: " + shoveIRR);
//      System.out.println("Call IRR: " + callIRR);
//      System.out.println();
    }
  }

}
