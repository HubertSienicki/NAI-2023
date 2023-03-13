package parser.fileparser;

import datamodel.Iris;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
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

    //TODO: Finish file parser
//    public List<Iris> parseFile() {
//        try {
//            String textFile = readFile();
//            return Stream.of(textFile.split("\n")).forEach(line -> {
//                List<String> splitLine = splitLine(line);
//
//                String className = splitLine.get(1);
//                List<Double> data = getData(splitLine);
//            }).collect(Collectors.toList());
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
//    }

    private List<String> splitLine(String line) {
        List<String> split = new ArrayList<>();
        split.add(line.substring(0, 15));
        split.add(line.substring(15, line.length()));
        return split;
    }

    private List<Double>getData(List<String> splitLine){
        return Stream.of(splitLine.get(0).split(",")).map(Double::parseDouble).collect(Collectors.toList());
    }
}
