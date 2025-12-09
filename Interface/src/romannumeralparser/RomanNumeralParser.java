package romannumeralparser;

import java.util.*;

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
     * @param numerals An array or successive list of arguments of type String.
     */
    public RomanNumeralParser(String ...numerals) {
        it = new RomanNumeralParserIterator(numerals);
    }

    /**
     * Consumes the iterator and returns a list with all the numerals parsed.
     * @return A list with the parsed version of the Roman numerals.
     */
    public List<Short> asParsedList() {
        List<Short> numerals = new ArrayList<>();
        while (it.hasNext()) {
            numerals.add(it.next());
        }
        return numerals;
    }

    public Iterator<Short> iterator() {
        return it;
    }

    @Override
    public String toString() {
        return it.toString();
    }
}