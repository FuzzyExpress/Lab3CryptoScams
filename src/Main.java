import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Main {
    public static void main(String[] args) {
        List<ScamInstance> scamList = LoadData.loadData();

        // Now use the list for multiple operations
        System.out.println(scamList.get(0));
        System.out.println(scamList.get(9));
        System.out.println(scamList.size());
    }
}