
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class CSVReader {
    public static void main(String[] args) {
        String csvFilePath = "thyroid_cancer_risk_data.csv"; // Replace with the actual path
        try (BufferedReader reader = new BufferedReader(new FileReader(csvFilePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(line); // Process each line of the CSV
            }
        } catch (IOException e) {
            // e.printStackTrace();
        }
    }
}




