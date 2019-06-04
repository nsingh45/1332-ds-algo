import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;

public class Huffman {

    // Do NOT add any variables (instance or static)

    /**
     * Builds a frequency map of characters for the given string.
     *
     * This should just be the count of each character.
     * No character not in the input string should be in the frequency map.
     *
     * @param s the string to map
     * @return the character -> Integer frequency map
     */
    public static Map<Character, Integer> buildFrequencyMap(String s) {
        if (s == null) {
            throw new IllegalArgumentException();
        }
        HashMap<Character, Integer> myMap = new HashMap<Character, Integer>();
        for (int i = 0; i < s.length(); i++) {
            Character c = new Character(s.charAt(i));
            if (!myMap.containsKey(c)) {
                myMap.put(c, new Integer(1));
            } else {
                Integer newInt = new Integer(myMap.get(c) + 1);
                myMap.put(c, newInt);
            }
        }
        return myMap;
    }

    /**
     * Build the Huffman tree using the frequencies given.
     *
     * Add the nodes to the tree based on their natural ordering (the order
     * given by the compareTo method).
     * The frequency map will not necessarily come from the
     * {@code buildFrequencyMap()} method. Every entry in the map should be in
     * the tree.
     *
     * @param freq the frequency map to represent
     * @return the root of the Huffman Tree
     */
    public static HuffmanNode buildHuffmanTree(Map<Character, Integer> freq) {
        if (freq == null) {
            throw new IllegalArgumentException();
        }
        PriorityQueue<HuffmanNode> pq = new PriorityQueue<HuffmanNode>();
        for (Character c : freq.keySet()) {
            HuffmanNode h = new HuffmanNode(c, freq.get(c));
            pq.add(h);
        }
        pq = treeHelper(pq);
        return pq.remove();
    }

    /**
     * Recursively builds the tree using a priority queue
     * @param pq, the priority queue containing all HuffmanNodes
     * @return the priority queue with one element
     */
    private static PriorityQueue<HuffmanNode>
    treeHelper(PriorityQueue<HuffmanNode> pq) {
        if (pq.size() == 1) {
            return pq;
        }
        HuffmanNode less = pq.remove();
        HuffmanNode more = pq.remove();
        pq.add(new HuffmanNode(less, more));
        return treeHelper(pq);
    }

    /**
     * Traverse the tree and extract the encoding for each character in the
     * tree.
     *
     * The tree provided will be a valid huffman tree but may not come from the
     * {@code buildHuffmanTree()} method.
     *
     * @param tree the root of the Huffman Tree
     * @return the map of each character to the encoding string it represents
     */
    public static Map<Character, EncodedString> buildEncodingMap(
            HuffmanNode tree) {
        if (tree == null) {
            throw new IllegalArgumentException();
        }
        HashMap<Character, EncodedString> myMap
            = new HashMap<Character, EncodedString>();
        EncodedString str = new EncodedString();
        buildHelper(myMap, tree, str);
        return myMap;
    }

    /**
     * Recursively traverses the tree to find characters and adds to
     * the EncodedString as it does so to build the encoding map
     * @param myMap, the map keeping the characters and encoded values
     * @param tree, the tree
     * @param str, the EncodedString being built
     */
    private static void buildHelper(Map<Character, EncodedString> myMap,
            HuffmanNode tree, EncodedString str) {
        if (tree == null) {
            str.remove();
            return;
        } else if (tree.getCharacter() != 0) {
            EncodedString temp = new EncodedString();
            temp.concat(str);
            myMap.put(tree.getCharacter(), temp);
            str.remove();
            return;
        }
        str.zero();
        buildHelper(myMap, tree.getLeft(), str);
        str.one();
        buildHelper(myMap, tree.getRight(), str);
        return;
    }

    /**
     * Encode each character in the string using the map provided.
     *
     * If a character in the string doesn't exist in the map ignore it.
     *
     * The encoding map may not necessarily come from the
     * {@code buildEncodingMap()} method, but will be correct for the tree given
     * to {@code decode()} when decoding this method's output.
     *
     * @param encodingMap the map of each character to the encoding string it
     * represents
     * @param s the string to encode
     * @return the encoded string
     */
    public static EncodedString encode(Map<Character, EncodedString>
            encodingMap, String s) {
        if (s == null || encodingMap == null) {
            throw new IllegalArgumentException();
        }
        EncodedString finalString = new EncodedString();
        for (int i = 0; i < s.length(); i++) {
            Character c = s.charAt(i);
            if (encodingMap.containsKey(c)) {
                finalString.concat(encodingMap.get(c));
            }
        }
        return finalString;
    }

    /**
     * Decode the encoded string using the tree provided.
     *
     * The encoded string may not necessarily come from {@code encode()}, but
     * will be a valid string for the given tree.
     *
     * (tip: use StringBuilder to make this method faster -- concatenating
     * strings is SLOW.)
     *
     * @param tree the tree to use to decode the string
     * @param es the encoded string
     * @return the decoded string
     */
    public static String decode(HuffmanNode tree, EncodedString es) {
        if (tree == null || es == null) {
            throw new IllegalArgumentException();
        }
        StringBuilder result = new StringBuilder("");
        HuffmanNode curr = tree;
        for (byte b : es) {
            if (b == (byte) 1) {
                curr = curr.getRight();
            } else {
                curr = curr.getLeft();
            }
            if (curr.getCharacter() != 0) {
                result.append(curr.getCharacter());
                curr = tree;
            }
        }
        return result.toString();
    }
}
