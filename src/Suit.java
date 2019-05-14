public enum Suit {
  HEARTS,
  DIAMONDS,
  SPADES,
  CLUBS;

  @Override
  public String toString() {
    switch (this) {
      case HEARTS:
        return "h";
      case DIAMONDS:
        return "d";
      case SPADES:
        return "s";
      case CLUBS:
        return "c";
    }
    return null;
  }

  public static Suit fromChar(char chr) {
    switch (Character.toLowerCase(chr)) {
      case 'h':
        return HEARTS;
      case 'd':
        return DIAMONDS;
      case 's':
        return SPADES;
      case 'c':
        return CLUBS;
    }
    throw new IllegalArgumentException("Invalid suit char: " + chr);
  }

  public static Suit sample() {
    int rand = (int) (Math.random() * 4);
    return Suit.values()[rand];
  }
}
