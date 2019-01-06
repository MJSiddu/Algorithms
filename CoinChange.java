import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

public class Main {

	public static void min(int[] denoms, int total) {
		int[] dp = new int[total + 1];
		Arrays.sort(denoms);
		Arrays.fill(dp, Integer.MAX_VALUE);

		dp[0] = 0;
		for (int i = 1; i <= total; i++) {
			for (int j = 0; j < denoms.length; j++) {
            	if (i >= denoms[j] && (dp[i - denoms[j]] + 1) < dp[i]) {
                        dp[i] = dp[i - denoms[j]] + 1;
            	}
                
            }
		}
		System.out.println(dp[total]);
	}

	public static void main(String[] args) throws IOException {

		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

		int data[] = new int[2];
		Arrays.fill(data, 0);
		String[] strData;

		strData = reader.readLine().split("\\s");

		for (int i = 0; i < strData.length; i++) {
			data[i] = Integer.parseInt(strData[i]);
		}

		int denoms[] = new int[data[1]];
		Arrays.fill(denoms, 0);
		String[] strDenoms;

		strDenoms = reader.readLine().split("\\s");

		for (int j = 0; j < strDenoms.length; j++) {
			denoms[j] = Integer.parseInt(strDenoms[j]);
		}

		min(denoms, data[0]);
	}

}
