import java.util.*;

public class ShamirSecretSharing {

    // Function to decode the value from the given base
    public static int decode(String value, int base) {
        return Integer.parseInt(value, base);
    }

    // Lagrange interpolation function to find the polynomial constant term
    public static double lagrangeInterpolation(int[] x, int[] y, int k) {
        double result = 0;

        for (int i = 0; i < k; i++) {
            double term = y[i];
            for (int j = 0; j < k; j++) {
                if (i != j) {
                    term = term * (0 - x[j]) / (x[i] - x[j]);
                }
            }
            result += term;
        }
        return result;
    }

    public static void main(String[] args) {
        // Sample input
        String jsonString = "{ \"keys\": { \"n\": 4, \"k\": 3 }, \"1\": { \"base\": \"10\", \"value\": \"4\" }, \"2\": { \"base\": \"2\", \"value\": \"111\" }, \"3\": { \"base\": \"10\", \"value\": \"12\" }, \"6\": { \"base\": \"4\", \"value\": \"213\" }}";
        
        // Manual parsing of the input (since org.json is not available)
        Map<String, Map<String, String>> data = new HashMap<>();
        
        data.put("1", new HashMap<>(Map.of("base", "10", "value", "4")));
        data.put("2", new HashMap<>(Map.of("base", "2", "value", "111")));
        data.put("3", new HashMap<>(Map.of("base", "10", "value", "12")));
        data.put("6", new HashMap<>(Map.of("base", "4", "value", "213")));

        // Extracting the number of points and required roots from the "keys" object
        int n = 4; // number of roots
        int k = 3; // minimum required points to solve the polynomial

        // Arrays to hold the x and decoded y values
        int[] x = new int[k];
        int[] y = new int[k];

        int count = 0;
        for (String key : data.keySet()) {
            if (count >= k) break;
            Map<String, String> root = data.get(key);
            int base = Integer.parseInt(root.get("base"));
            String value = root.get("value");

            // Fill in x and decoded y arrays
            x[count] = Integer.parseInt(key); // x is the key itself
            y[count] = decode(value, base);   // Decode y based on the given base

            count++;
        }

        // Use Lagrange interpolation to find the constant term 'c'
        double constant = lagrangeInterpolation(x, y, k);

        // Output the result
        System.out.println("The constant term (c) is: " + (int)(constant));
    }
}
