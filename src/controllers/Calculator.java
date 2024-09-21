package controllers;

import Views.Format;
import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Paths;

public class Calculator {
    
    public void readFile() throws IOException {
        
        try (BufferedReader br = java.nio.file.Files.newBufferedReader(Paths.get("./measurements-thousand.txt"))) {
            while (br.readLine() != null) {
                System.out.println(Format.printFormat(br.readLine()));
            }
        } catch (Exception e) {
            System.err.println("Error occurred: " + e);
        }
    }

}
