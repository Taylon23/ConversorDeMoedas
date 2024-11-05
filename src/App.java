import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.util.Scanner;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import moeda.Coin;

public class App {
    public static void main(String[] args) throws Exception {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("*************************************");
            System.out.println("Bem vindo ao Conversor de moedas");
            System.out.println("1) USD ==> BRL");
            System.out.println("2) BRL ==> USD");
            System.out.println("3) EUR ==> BRL");
            System.out.println("4) BRL ==> EUR");
            System.out.println("5) USD ==> EUR");
            System.out.println("6) EUR ==> USD");
            System.out.println("7) Sair");
            System.out.println("*************************************");
            System.out.print("Opção: ");
            int searchCoin = scanner.nextInt();

            if (searchCoin == 7) {
                System.out.println("Saindo...");
                break;
            }

            // Define baseCoin e targetCoin com base na opção do usuário
            String baseCoin;
            String targetCoin;

            switch (searchCoin) {
                case 1:
                    baseCoin = "USD";
                    targetCoin = "BRL";
                    break;
                case 2:
                    baseCoin = "BRL";
                    targetCoin = "USD";
                    break;
                case 3:
                    baseCoin = "EUR";
                    targetCoin = "BRL";
                    break;
                case 4:
                    baseCoin = "BRL";
                    targetCoin = "EUR";
                    break;
                case 5:
                    baseCoin = "USD";
                    targetCoin = "EUR";
                    break;
                case 6:
                    baseCoin = "EUR";
                    targetCoin = "USD";
                    break;
                default:
                    System.out.println("Opção inválida.");
                    continue;
            }

            // Faz a requisição à API
            String address = "https://v6.exchangerate-api.com/v6/8934113a8fc0bce777497d00/latest/" + baseCoin;
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(address))
                    .build();
            HttpResponse<String> response = client.send(request, BodyHandlers.ofString());

            // Converte a resposta JSON em um objeto Coin
            String json = response.body();
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            Coin coin = gson.fromJson(json, Coin.class);

            // Obtém a taxa de câmbio para a moeda de destino
            double rate = coin.getConversionRate(targetCoin);
            System.out.printf("Taxa de câmbio de %s para %s: %.4f%n", baseCoin, targetCoin, rate);
        }

        scanner.close();
    }
}