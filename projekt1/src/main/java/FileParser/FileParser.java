package FileParser;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class FileParser {

    private final String path;

    public FileParser(String path) {
        this.path = path;
    }

    private String readFile() throws IOException {
        BufferedReader fileReader = new BufferedReader(new FileReader(path));

        String line = fileReader.readLine();
        StringBuilder textFile = new StringBuilder();
        while (line != null) {
            textFile.append(line).append("\n");
            line = fileReader.readLine();
        }
        return textFile.toString();
    }

    public List<String> parseFile() {
        try {
            String textFile = readFile();
            return Stream.of(textFile.split("\n")).collect(Collectors.toList());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
