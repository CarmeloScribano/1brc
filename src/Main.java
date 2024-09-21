import controllers.Calculator;
import java.io.IOException;

public class Main {
    public static void main(String args[]) {
        Calculator calculator = new Calculator();

        try {
            calculator.readFile();
        } catch (IOException e) {
            System.err.println("Error:" + e);
        }
    }
}