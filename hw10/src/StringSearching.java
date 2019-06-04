import java.util.ArrayList;
import java.util.List;

public class StringSearching implements StringSearchingInterface {

    @Override
    public List<Integer> boyerMoore(CharSequence pattern, CharSequence text) {
        if (pattern == null || text == null || pattern.length() == 0) {
            throw new IllegalArgumentException();
        }
        ArrayList<Integer> matches = new ArrayList<Integer>();
        int[] table = buildLastTable(pattern);
        int i = pattern.length() - 1;
        int j = pattern.length() - 1;
        while (j < text.length()) {
            char c = text.charAt(j);
            if (pattern.charAt(i) == c) {
                int copyi = i;
                int copyj = j;
                do {
                    copyi--;
                    copyj--;
                    if (copyi >= 0) {
                        c = text.charAt(copyj);
                    }
                } while (copyi >= 0 && pattern.charAt(copyi) == c);
                if (copyi < 0) {
                    matches.add(copyj + 1);
                } else {
                    j = copyj;
                }
            }
            int shift = table[c];
            if (shift != -1) {
                j += Math.max(pattern.length() - 1 - shift, 1);
            } else {
                j += pattern.length();
            }
        }
        return matches;
    }

    @Override
    public int[] buildLastTable(CharSequence pattern) {
        if (pattern == null || pattern.length() == 0) {
            throw new IllegalArgumentException();
        }
        int[] myTable = new int[Character.MAX_VALUE + 1];
        for (int i = 0; i < myTable.length; i++) {
            myTable[i] = -1;
        }
        for (int i = 0; i < pattern.length(); i++) {
            myTable[pattern.charAt(i)] = i;
        }
        return myTable;
    }

    @Override
    public int generateHash(CharSequence current, int length) {
        if (current == null || length < 0 || length > current.length()) {
            throw new IllegalArgumentException();
        }
        int result = 0;
        for (int i = 0; i < length; i++) {
            result += (current.charAt(i) * exponent(length - (i + 1)));
        }
        return result;
    }

    /**
     * Multiplies the base prime by itself until it reaches the given power
     * @param pow, the power for the base prime
     * @return the exponentiated prime
     */
    private int exponent(int pow) {
        int result = 1;
        while (pow > 0) {
            result *= BASE;
            pow--;
        }
        return result;
    }

    @Override
    public int updateHash(int oldHash, int length, char oldChar, char newChar) {
        int newHash = oldHash;
        newHash -= (oldChar * exponent(length - 1));
        newHash *= BASE;
        newHash += newChar;
        return newHash;
    }

    @Override
    public List<Integer> rabinKarp(CharSequence pattern, CharSequence text) {
        if (pattern == null || pattern.length() == 0 || text == null) {
            throw new IllegalArgumentException();
        }
        ArrayList<Integer> matches = new ArrayList<Integer>();
        if (pattern.length() > text.length() || text.length() == 0) {
            return matches;
        }
        int l = pattern.length();
        int tracker = l;
        int patternHash = generateHash(pattern, l);
        int initialHash = generateHash(text, l);
        while (tracker <= text.length()) {
            if (initialHash == patternHash) {
                boolean isSame = true;
                int i = l;
                int j = tracker;
                while (i > 0 && isSame) {
                    if (!(pattern.charAt(i - 1) == text.charAt(j - 1))) {
                        isSame = false;
                    }
                    i--;
                    j--;
                }
                if (isSame) {
                    matches.add(j);
                }
            }
            tracker++;
            if (tracker <= text.length()) {
                initialHash = updateHash(initialHash, l,
                        text.charAt(tracker - l - 1),
                        text.charAt(tracker - 1));
            }
        }
        return matches;
    }
}
