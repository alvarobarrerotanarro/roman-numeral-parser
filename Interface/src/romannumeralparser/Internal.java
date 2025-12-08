package romannumeralparser;

public class Internal {

    private static final char NUMERALS[] = {
            'I', 'V', 'X', 'L', 'C', 'D', 'M'
    };
    private static final short NUMERALS_VAL[] = {
            1, 5, 10, 50, 100, 500, 1000
    };

    /* Math utilities. */

    /**
     * Allows to cunt the amount of digits for given number.
     * @param val Any integer number.
     * @return The number of digits for val.
     */
    public static int countDigits(int val) {
        int aux = val;
        int digits = 0;

        do {
            digits++;
        } while ((aux /= 10) > 0);

        return digits;
    }

    /**
     * Allows to break down the given val in different addends as in the form:
     * 154 = 100 + 50 + 4
     * @param val Any integer number.
     * @return An array of addends.
     */
    public static short[] summationSplit(int val) {
        int digitCount = countDigits(val);
        short digits[] = new short[digitCount];
        int aux = val;

        for (int i = 0; i < digitCount; i++, aux /= 10) {
            digits[digitCount - i - 1] = (short) (aux % 10 * Math.pow(10, i));
        }

        return digits;
    }

    /**
     * Returns the proportional scale of a given number as power of 10.
     * Take as an example the number 763, this method will ignore the
     * last two digits (700) and will return 100 as 700 is 7 * 100.
     *
     * @param number Any number.
     * @return The proportional scale.
     */
    public static short getProportionalScale(short number) {
        int div = number;
        short n = 1;
        while ((div /= 10) > 0)
            n *= 10;
        return n;
    }

    /* Single numeral utilities. */

    /**
     * Allows to calculate the Roman numeral boundaries for the given number.
     * The number 47, for instance would be within the X and L bounds.
     * @param number Any integer number < 1000.
     * @return An array with the values of the Roman numerals, being the first position the left boundary and last one the right boundary.
     */
    public static short[] getNumeralBoundaries(short number) {
        boolean found = false;
        short bounds[] = {0, 1};

        for (int i = 0; !found && i < NUMERALS_VAL.length - 1; i++) {
            if (NUMERALS_VAL[i] < number && number <= NUMERALS_VAL[i + 1]) {
                bounds[0] = NUMERALS_VAL[i];
                bounds[1] = NUMERALS_VAL[i + 1];
            }
        }

        return bounds;
    }

    /**
     * Checks weather the given number may be represented using just one character from the Roman numerals.
     * @param number Any integer number.
     * @return true in case it does, false otherwise.
     */
    public static boolean isValidNumeral(short number) {
        boolean found = false;

        for (int i = 0; !found && i < NUMERALS_VAL.length; i++) {
            if (NUMERALS_VAL[i] == number) {
                found = true;
            }
        }

        return found;
    }

    /**
     * Allows to get the position for number in the Roman numerals scale. Number should be the value for an existing Roman numeral [1, 5, 10, ... 1000], otherwise it will always return 0.
     * @param number A Roman numeral value [1, 5, 10 .... 1000].
     * @return The position for number in the Roman numerals scale.
     */
    public static int getNumeralPos(short number) {
        int pos = -1;
        for (int i = 0; pos < 0 && i < NUMERALS_VAL.length; i++) {
            if (NUMERALS_VAL[i] == number) {
                pos = i;
            }
        }
        return Math.max(pos, 0);
    }

    /**
     * Allows to get the position for numeral in the Roman numerals scale. Numeral should be the character for an existing Roman numeral [I, V, X, ... M], otherwise it will always return 0.
     * @param numeral A Roman numeral character [I, V, X .... M].
     * @return The position for numeral in the Roman numerals scale.
     */
    public static int getNumeralPos(char numeral) {
        int pos = -1;
        for (int i = 0; pos < 0 && i < NUMERALS.length; i++) {
            if (NUMERALS[i] == numeral) {
                pos = i;
            }
        }
        return Math.max(pos, 0);
    }

    /**
     * Allows to get the character representation for number. Number should be the value for an existing Roman numeral [1, 5, 10 ... 1000], otherwise it will always return 'I'.
     * @param number A Roman numeral value [1, 5, 10 ... 1000]
     * @return The character representation of number.
     */
    public static char getNumeralChar(short number) {
        return NUMERALS[getNumeralPos(number)];
    }

    /**
     * Allows to get the value from the given Roman numeral character. If it wasn't found always return 1.
     * @param numeral Any valid Roman numeral character.
     * @return The associated value with numeral.
     */
    public static short getNumeralValue(char numeral) {
        return NUMERALS_VAL[getNumeralPos(numeral)];
    }
}
