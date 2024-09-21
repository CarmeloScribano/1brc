import controllers.Calculator;
import java.io.IOException;
import views.Formatter;

public class Main {
    public static void main(String args[]) throws IOException {
        Calculator calculator = new Calculator();

        String formattedOutput = Formatter.printFormat(calculator.getStations());
        System.out.println(formattedOutput);
    }
}