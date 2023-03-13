package Main;

import FileParser.FileParser;

import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        FileParser fp = new FileParser("C:\\Users\\s25189\\iris.data");
        fp.parseFile().forEach(System.out::println);
    }
}
