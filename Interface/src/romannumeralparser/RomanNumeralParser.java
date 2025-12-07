package romannumeralparser;

import java.util.Iterator;
import java.util.NoSuchElementException;

class RomanNumeralParserIterator implements Iterator<Short> {
   private final String numerals[];
   private int pos = 0;

   public RomanNumeralParserIterator(String numerals[]) {
      this.numerals = numerals;
   }

   public boolean hasNext() {
       return pos < numerals.length;
   }

   public Short next() {
        if (pos >= numerals.length)  {
            throw new NoSuchElementException("No remaining numerals.");
        }

        RomanNumeral numeral = new RomanNumeral(numerals[pos++]);
        return numeral.parse();
   }
}

public class RomanNumeralParser implements Iterable<Short> {
    private final RomanNumeralParserIterator it;

    public RomanNumeralParser(String ...numerals) {
        if (numerals.length < 1) {
            throw new RuntimeException("Missing numerals to parse.");
        }
        it = new RomanNumeralParserIterator(numerals);
    }

    public Iterator<Short> iterator() {
        return it;
    }
}