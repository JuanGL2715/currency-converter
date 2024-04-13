
import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Scanner;

public class Main {
    @SerializedName("conversion_result")
    static String apiKey = "874d80077ad32b5a749ce1ca";


    public static String[]  obtainVariables(){
        Scanner sc = new Scanner(System.in);
        System.out.print("""
                    Currency converter
                
                Enter the amount to convert:""");
        String amount = sc.next();
        System.out.print("From: ");
        String first = sc.next();
        System.out.print("To: ");
        String second = sc.next();
        return new String[]{amount, first, second};
    }
    public void convertACurrency() throws IOException, InterruptedException {
        String[] variables = obtainVariables();
        String amount = variables[0];
        String first = variables[1];
        String second = variables[2];
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(STR."https://v6.exchangerate-api.com/v6/\{apiKey}/pair/\{first}/\{second}/\{amount}")).build();
        HttpResponse<String> response = client
                .send(request, HttpResponse.BodyHandlers.ofString());

        String json = response.body();
        Gson gson = new Gson();
        Currency currency = gson.fromJson(json, Currency.class);
        System.out.println(STR."\{amount} \{first} = \{currency.conversion_result()} \{second}");
    }
    public void menu() throws IOException, InterruptedException {
        Scanner sc = new Scanner(System.in);
        while(true){
            System.out.println("""
                                MENU
                    1. Convert a currency
                    2. Exit
                    """);
            int selected = sc.nextInt();
            switch(selected){
                case 1:
                    convertACurrency();
                    break;
                case 2:
                    System.out.println("Exiting the program...");
                    return;
                default:
                    System.out.println("Invalid option. Please select again.");
                    break;
            }

        }
    }
    public static void main(String[] args) throws IOException, InterruptedException {
        Main instance = new Main();
        try {
            instance.menu();
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}