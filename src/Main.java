import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
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
                    // System.out.println("Number of commas: " + commas);
                    // System.out.println("Line: " + l);
                }
                return commas > 6 ? l : null;
            })
            .filter(Objects::nonNull)
            .count();

//        Stream<ScamInstance> scamInstances = allLines.stream()
//                .skip(1)
//                .map(l -> l.replaceAll(",,", ", ,"))
//                .map(l -> l.split(","))
//                .map(l -> new ScamInstance(
//                        l[0],
//                        l[1],
//                        switch (l[2].toLowerCase()) { // Switch expression
//                            case "phishing" -> Category.phishing;
//                            case "scamming" -> Category.scamming;
//                            case "malware" -> Category.malware;
//                            case "hacked" -> Category.hacked;
//                            default -> throw new IllegalArgumentException("Unexpected category: " + l[2]);
//                        },
//
//                        l[3],
//                        l[4],
//                        l[5]
//                ));
//
//        scamInstances.forEach(System.out::println);

        Stream<ScamInstance> scamInstances = allLines.stream()
            .skip(1)
            .map(l -> l.toCharArray())
            .map(l -> {
                // System.out.println(l);
                String[] parts = new String[11];
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
                System.out.println(Arrays.toString(parts));
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
                l[5]));

        scamInstances.forEach(System.out::println);
    }
}