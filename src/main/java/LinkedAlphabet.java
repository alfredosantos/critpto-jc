import java.util.LinkedList;

public class LinkedAlphabet {

  static final LinkedList<String> alphabet = new LinkedList<String>();

  static {
    alphabet.add(0, "a");
    alphabet.add(1, "b");
    alphabet.add(2, "c");
    alphabet.add(3, "d");
    alphabet.add(4, "e");
    alphabet.add(5, "f");
    alphabet.add(6, "g");
    alphabet.add(7, "h");
    alphabet.add(8, "i");
    alphabet.add(9, "j");
    alphabet.add(10, "k");
    alphabet.add(11, "l");
    alphabet.add(12, "m");
    alphabet.add(13, "n");
    alphabet.add(14, "o");
    alphabet.add(15, "p");
    alphabet.add(16, "q");
    alphabet.add(17, "r");
    alphabet.add(18, "s");
    alphabet.add(19, "t");
    alphabet.add(20, "u");
    alphabet.add(21, "v");
    alphabet.add(22, "w");
    alphabet.add(23, "x");
    alphabet.add(24, "y");
    alphabet.add(25, "z");
  }

  private int getAlphabetActual(String letter) {
    return alphabet.indexOf(letter);
  }

  public int getAlphabetNext(int pos, String letter) {
    int nextPos = getAlphabetActual(letter) - pos;
    if (nextPos < 0) {
      return alphabet.size() + nextPos;
    }
    return nextPos;
  }

  public String getLetter(int pos) {
    return alphabet.get(pos);
  }
}
