package romannumeralparser;

import java.util.Arrays;
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

   @Override
    public String toString() {
       return Arrays.toString(numerals);
   }
}

/**
 * Sometimes you need to parse multiple Roman numerals contained within an array.
 * RomanNumeralParser is an implementation of parsing iterable.
 * If you as just interested to parse a single Roman numeral (or vice versa), use the RomanNumeral implementation instead.
 */
public class RomanNumeralParser implements Iterable<Short> {
    private final RomanNumeralParserIterator it;

    /**
     * Parsers an array or a successive list of Roman numerals as arguments.
     * @param numerals An array of successive list of arguments of type String.
     */
    public RomanNumeralParser(String ...numerals) {
        it = new RomanNumeralParserIterator(numerals);
    }

    public Iterator<Short> iterator() {
        return it;
    }

    @Override
    public String toString() {
        return it.toString();
    }
}