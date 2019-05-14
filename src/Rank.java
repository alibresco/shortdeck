public enum Rank {
  SIX,
  SEVEN,
  EIGHT,
  NINE,
  TEN,
  JACK,
  QUEEN,
  KING,
  ACE;

  @Override
  public String toString() {
    switch (this) {
      case SIX:
        return "6";
      case SEVEN:
        return "7";
      case EIGHT:
        return "8";
      case NINE:
        return "9";
      case TEN:
        return "T";
      case JACK:
        return "J";
      case QUEEN:
        return "Q";
      case KING:
        return "K";
      case ACE:
        return "A";
    }
    return null;
  }

  public static Rank fromChar(char chr) {
    switch (Character.toUpperCase(chr)) {
      case '6':
        return SIX;
      case '7':
        return SEVEN;
      case '8':
        return EIGHT;
      case '9':
        return NINE;
      case 'T':
        return TEN;
      case 'J':
        return JACK;
      case 'Q':
        return QUEEN;
      case 'K':
        return KING;
      case 'A':
        return ACE;
    }
    throw new IllegalArgumentException("Invalid rank char: " + chr);
  }
}
