package filereader;

import datamodel.DataModel;
import filereader.parser.Parser;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Reader {

    private final FileReader fileReader;

    public Reader(String path) throws FileNotFoundException {
        fileReader = new FileReader(path);
    }

    /**
     * @return Whole file as a string
     * @throws IOException when input is invalid
     */
    public List<DataModel> readFile() throws IOException {
        String line;
        List<DataModel> dataModels = new ArrayList<>();
        BufferedReader br = new BufferedReader(fileReader);

        while ((line = br.readLine()) != null) {
            dataModels.add(Parser.parseFile(line));
        }
        return dataModels;
    }
}
