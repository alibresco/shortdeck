public class HistEntry implements Comparable<HistEntry> {
  private Rank rank;
  private int count;

  public HistEntry(Rank rank, int count) {
    this.rank = rank;
    this.count = count;
  }

  @Override
  public int compareTo(HistEntry o) {
    int comp = count - o.count;
    if (comp == 0) {
      comp = rank.ordinal() - o.rank.ordinal();
    }
    return comp;
  }

  @Override
  public String toString() {
    return "HistEntry{" +
        "rank=" + rank +
        ", count=" + count +
        '}';
  }

  public int getCount() {
    return count;
  }

  public Rank getRank() {
    return rank;
  }
}
