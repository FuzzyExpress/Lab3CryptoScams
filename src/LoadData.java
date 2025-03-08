import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;


public class LoadData {
    public static List<ScamInstance> loadData() {
        String csvFile = "urls.csv";
        List<String> allLines;

        // First read all lines into memory
        try {
            allLines = Files.readAllLines(Paths.get(csvFile));
        } catch (IOException e) {
            System.err.println("Error reading file: " + e.getMessage());
            throw new RuntimeException(e);
        }

        // Create the stream and collect results
        List<ScamInstance> scamList = allLines.stream()
            .skip(1)
            .map(l -> l.toCharArray())
            .map(l -> {
                String[] parts = new String[7];
                for (int i = 0; i < parts.length; i++) {
                    parts[i] = "";
                }
                int partIndex = 0;
                boolean isInQuotes = false;
                for (int i = 0; i < l.length; i++) {
                    if (l[i] == '"') {
                        isInQuotes = !isInQuotes;
                        continue;
                    }
                    
                    if (isInQuotes) {
                        continue;
                    }   

                    if (l[i] == ',') {
                        partIndex++;
                    } else {
                        parts[partIndex] += l[i];
                    }
                }
                // System.out.println(Arrays.toString(parts));
                return parts;
            })
            .map(l -> new ScamInstance(
                l[0],
                l[1],
                switch (l[2].toLowerCase()) { // Switch expression
                    case "phishing" -> Category.phishing;
                    case "scamming" -> Category.scamming;
                    case "malware" -> Category.malware;
                    case "hacked" -> Category.hacked;
                    default -> throw new IllegalArgumentException("Unexpected category: " + l[2]);
                },
                l[3],
                l[4],
                l[5],
                l[6]
            ))
            .collect(Collectors.toList());
        return scamList;
    }
}
