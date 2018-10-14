import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {
    public static void main(String[] args) {
        YandexTranslate yandexTranslate = new YandexTranslate();
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("Enter a phrase in English");
        while (true) {
            try {
                String line = br.readLine();
                if(line.equals("q")) break;
                String translateText = yandexTranslate.translate(line);
                System.out.println(translateText);
            } catch (IOException e) {
                System.out.println("Error");
            }
        }
    }
}
