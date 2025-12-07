package romannumeralparser;

public class Internal {

    private static final char NUMERALS[] = {
            'I', 'V', 'X', 'L', 'C', 'D', 'M'
    };
    private static final short NUMERALS_VAL[] = {
            1, 5, 10, 50, 100, 500, 1000
    };

    public static int countDigits(int val) {
        int aux = val;
        int digits = 0;

        do {
            digits++;
        } while ((aux /= 10) > 0);

        return digits;
    }

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

    public static int[] getNumeralBoundaries(short number) {
        boolean found = false;
        int bounds[] = {0, 1};

        for (int i = 0; !found && i < NUMERALS_VAL.length - 1; i++) {
            if (NUMERALS_VAL[i] < number && number <= NUMERALS_VAL[i + 1]) {
                bounds[0] = i;
                bounds[1] = i + 1;
            }
        }

        return bounds;
    }

    public static char getNumeralChar(short number) {
        int pos = 0;
        boolean found = false;

        for (int i = 0; !found && i < NUMERALS_VAL.length; i++) {
            if (NUMERALS_VAL[i] == number) {
                pos = i;
                found = true;
            }
        }

        return NUMERALS[pos];
    }

    public static int getNumeralPos(short numeral) {
        int pos = -1;
        for (int i = 0; pos < 0 && i < NUMERALS_VAL.length; i++) {
            if (NUMERALS_VAL[i] == numeral) {
                pos = i;
            }
        }
        return Math.max(pos, 0);
    }

    public static boolean requiresNumeralSubstraction(short number) {
        int scale = Internal.getProportionalScale(number);
        int pos = number / scale;
        return pos == 4 || pos == 9;
    }

    public static String scaledArabicToNumeral(short number) {
        String numeral = "";
        int numerals[] = getNumeralBoundaries(number);

        boolean needsSubstraction = requiresNumeralSubstraction(number);

        short scale = Internal.getProportionalScale(number);
        int factor = number / scale;
        int reps;

        // Trivial numerals.
        if (number < 1) {
            return numeral;
        } else if (number > 999) {
            numeral += "M".repeat((number / 1000) % 10);
            return numeral;
        } else if (NUMERALS_VAL[numerals[1]] == number) {
            numeral = String.valueOf(NUMERALS[numerals[1]]);
            return numeral;
        }

        // Concatenate the numerals.
        if (needsSubstraction) {
            reps = Math.abs(NUMERALS_VAL[numerals[1]] - (factor * scale)) / scale;
            numeral = String.valueOf(getNumeralChar(scale)).repeat(reps) + NUMERALS[numerals[1]];
        } else {
            reps = Math.abs(NUMERALS_VAL[numerals[0]] - (factor * scale)) / scale;
            numeral = NUMERALS[numerals[0]] + String.valueOf(getNumeralChar(scale)).repeat(reps);
        }

        return numeral;
    }

    public static short parseSingleNumeral(char numeral) {
        int pos = -1;
        for (int i = 0; i < NUMERALS.length; i++) {
            if (NUMERALS[i] == numeral)
                pos = i;
        }

        return NUMERALS_VAL[Math.max(pos, 0)];
    }
}
