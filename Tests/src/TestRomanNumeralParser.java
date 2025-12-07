import romannumeralparser.RomanNumeral;
import romannumeralparser.RomanNumeralParser;

import java.util.Arrays;

public class TestRomanNumeralParser {

    public static final int ATTEMPTS = 3999;

    public static String[] generateNumerals()  {
       String numerals[] = new String[ATTEMPTS];
        for (short i = 0; i < ATTEMPTS; i++) {
            numerals[i] = RomanNumeral.fromArabic((short)(i +  1)).toString();
        }
        return numerals;
    }

    public static void test() {
        String numerals[] = generateNumerals();
        RomanNumeralParser parser = new RomanNumeralParser(numerals);
        for (short _ : parser)
            ;
    }

    public static void main(String args[]) {
        test();
        System.out.printf("%d Roman numerals were parsed using RomanNumeralParser, an implementation of Iterable<Short>.\n", ATTEMPTS);
    }
}
