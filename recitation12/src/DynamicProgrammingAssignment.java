/**
 * Assignment to teach dynamic programming using 3 simple example problems:
 * 1. Fibonacci numbers
 * 2. Longest common subsequence
 * 3. Edit distance
 * 
 * @author Julia Ting (julia.ting@gatech.edu)
 */
public class DynamicProgrammingAssignment {
	public static int num_calls = 0; //DO NOT TOUCH

	/**
	 * Calculates the nth fibonacci number: fib(n) = fib(n-1) + fib(n-2).
	 * Remember that fib(0) = 0 and fib(1) = 1.
	 * 
	 * This should NOT be done recursively - instead, use a 1 dimensional
	 * array so that intermediate fibonacci values are not re-calculated.
	 * 
	 * The running time of this function should be O(n).
	 * 
	 * @param n A number 
	 * @return The nth fibonacci number
	 */
	public static int fib(int n) {
		num_calls++; //DO NOT TOUCH
		int[] fibArr = new int[n + 2];
		fibArr[0] = 0;
		fibArr[1] = 1;
		for (int i = 2; i <= n; i++) {
		    fibArr[i] = fibArr[i - 1] + fibArr[i - 2];
		}
		return fibArr[n];
	}
	
	/**
	 * Calculates the length of the longest common subsequence between a and b.
	 * 
	 * @param a
	 * @param b
	 * @return The length of the longest common subsequence between a and b
	 */
	public static int lcs(String a, String b) {
		num_calls++; //DO NOT TOUCH
		int[][] lcsArr = new int[a.length() + 1][b.length() + 1];
		for (int i = 0; i < lcsArr.length; i++) {
		    lcsArr[i][0] = 0;
		}
		for (int i = 0; i < lcsArr[0].length; i++) {
		    lcsArr[0][i] = 0;
		}
		for (int i = 1; i < lcsArr[0].length; i++) {
		    for (int j = 1; j < lcsArr.length; j++) {
		        if (a.charAt(j - 1) == b.charAt(i - 1)) {
		            lcsArr[j][i] = lcsArr[j - 1][i - 1] + 1;
		        } else {
		            lcsArr[j][i] = Math.max(lcsArr[j][i - 1], lcsArr[j-1][i]);
		        }
		    }
		}
		return lcsArr[a.length()][b.length()];
	}

	/**
	 * Calculates the edit distance between two strings.
	 * 
	 * @param a A string
	 * @param b A string
	 * @return The edit distance between a and b
	 */
	public static int edit(String a, String b) {
		num_calls++; //DO NOT TOUCH
		int[][] arr = new int[a.length() + 1][b.length() + 1];
		for (int i = 0; i < arr.length; i++) {
            arr[i][0] = 0;
        }
        for (int i = 0; i < arr[0].length; i++) {
            arr[0][i] = 0;
        }
        for (int i = 1; i < arr[0].length; i++) {
            for (int j = 1; j < arr.length; j++) {
                if (a.charAt(j - 1) == b.charAt(i - 1)) {
                    arr[j][i] = arr[j - 1][i - 1] + 1;
                } else {
                    arr[j][i] = Math.max(arr[j][i - 1], arr[j-1][i]);
                }
            }
        }
        int num = Math.min((arr[a.length() - 1][b.length()] + 1), arr[a.length()][b.length() - 1] + 1);
        int otherNum = Math.min(num, (arr[a.length() - 1][b.length() - 1]));
		return otherNum;
	}
	
}

