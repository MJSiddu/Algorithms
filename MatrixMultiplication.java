import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

public class Main {
	public static void findCost(int arr[]) {
		int temp[][] = new int[arr.length][arr.length];
		int buffer[][] = new int[arr.length][arr.length];
		int q, min = 0;
		for (int l = 1; l < arr.length - 1; l++) {
			for (int i = 1; i < arr.length - l; i++) {
				int j = i + l;
				min = Integer.MAX_VALUE;
				for (int k = i; k < j; k++) {
					q = temp[i][k] + temp[k + 1][j] + arr[i - 1] * arr[k] * arr[j];
					if (q < min) {
						min = q;
						buffer[i][j] = k;

					}
				}
				temp[i][j] = min;
			}
		}
		System.out.println(temp[1][arr.length - 1]);
		printParenthesis(1, arr.length - 1, arr.length, buffer);
	}

	public static void printParenthesis(int i, int j, int n, int buffer[][]) {
		if (i == j) {
			System.out.print(i);
			return;
		}
		System.out.print("(");
		printParenthesis(i, buffer[i][j], n, buffer);
		printParenthesis(buffer[i][j] + 1, j, n, buffer);	
		System.out.print(")");
	}

	public static void main(String args[]) throws IOException {

		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

		int data[] = new int[1];
		Arrays.fill(data, 0);
		String[] strData;

		strData = reader.readLine().split("\\s");

		for (int i = 0; i < strData.length; i++) {
			data[i] = Integer.parseInt(strData[i]);
		}

		int sizes[] = new int[data[0] + 1];
		Arrays.fill(sizes, 0);
		String[] strSizes;

		strSizes = reader.readLine().split("\\s");

		for (int i = 0; i < strSizes.length; i++) {
			sizes[i] = Integer.parseInt(strSizes[i]);
		}

		findCost(sizes);
		System.out.println();

	}
}
