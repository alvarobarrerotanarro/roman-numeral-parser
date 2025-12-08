import romannumeralparser.RomanNumeral;
import romannumeralparser.RomanNumeralFormatError;

public class TestRomanNumeral {
    private static final int ATTEMPTS = 3999;

    public static void test() {
        RomanNumeral numeral;
        int number = 0;
        int fails = 0;
        StringBuilder failNumerals = new StringBuilder();

        for (short i = 1; i <= ATTEMPTS; i++) {
            try {
                numeral = RomanNumeral.fromArabic(i);
                number = numeral.parse();
                if (number != i) {
                    fails++;
                    failNumerals.append(String.format("%6d\t%s%6d\n", i, numeral, number));
                }
            } catch (RomanNumeralFormatError e) {
                fails++;
                failNumerals.append(String.format("%6d\t%s\n", i, e.getMessage()));
            }
        }

        System.out.println(fails < 1 ? "No errors !" : failNumerals);
        System.out.printf("%d Roman numerals were parsed with a success rate of %.2f%%\n", ATTEMPTS, 100 - ((double) fails / ATTEMPTS * 100));
    }

    /**
     * Iterates across the interval Roman numerals can represent numbers, parses each number to Roman numeral and then unparses it to the original one. If both doesn't match it is considered an error.
     */
    public static void main(String args[]) {
        test();
    }
}