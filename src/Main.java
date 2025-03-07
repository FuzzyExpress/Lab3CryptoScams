import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Stream;

public class Main {
    public static void main(String[] args) {
        String csvFile = "urls.csv";
        List<String> allLines;

        // First read all lines into memory
        try {
            allLines = Files.readAllLines(Paths.get(csvFile));
        } catch (IOException e) {
            System.err.println("Error reading file: " + e.getMessage());
            return;
        }

        // Then process the lines with stream
        long count = allLines.stream()
            .map(l -> {
                int commas = l.length() - l.replace(",", "").length();
                if (commas > 6) {
                    System.out.println("Number of commas: " + commas);
                    System.out.println("Line: " + l);
                }
                return commas > 6 ? l : null;
            })
            .filter(l -> l != null)
            .count();
        
        System.out.println("Total lines with more than 6 commas: " + count);
    }
}