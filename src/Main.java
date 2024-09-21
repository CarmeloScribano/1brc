import controllers.Calculator;
import java.io.IOException;
import views.Formatter;

public class Main {
    public static void main(String args[]) throws IOException {
        long startTime = System.currentTimeMillis();
        String formattedOutput = new Formatter().printFormat(new Calculator().getStations("./data/measurements-billion.txt"));
        
        System.out.println(formattedOutput);
        System.out.printf("Execution time: %.3f seconds%n", (System.currentTimeMillis() - startTime) / 1000.0);
    }
}
