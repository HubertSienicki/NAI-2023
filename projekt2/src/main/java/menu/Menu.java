package menu;

import perceptron.Perceptron;

import java.util.List;
import java.util.Objects;
import java.util.Scanner;

import static java.lang.System.out;

public class Menu {

    private Menu() {

    }

    public static void startMenu(Perceptron perceptron, Scanner sc, List<Double> features) {
        String className;
        String command;
        command = sc.nextLine();

        while (!Objects.equals(command, ".quit")) {
            if (Objects.equals(command, ".quit") || Objects.equals(command, "1") && (command.equals("1"))) {
                for (int i = 0; i < perceptron.getNumFeatures(); i++) {
                    out.println("Type a feature double");
                    try {
                        features.add(Double.parseDouble(sc.nextLine()));
                    } catch (Exception e) {
                        out.println("Invalid input! Try again.");
                    }
                }
                out.println("Type class name");
                className = sc.nextLine();
                perceptron.showResults(features, className);
                command = sc.nextLine();
            }
        }
    }
}