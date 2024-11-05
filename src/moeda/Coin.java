package moeda;

import java.util.List;
import java.util.Map;

public class Coin {
    private String base_code;
    private Map<String, Double> conversion_rates;
    private String typeCoin;

    public double getConversionRate(String targetCoin) {
        return conversion_rates.getOrDefault(targetCoin, 0.0);
    }

    public String setTypeCoin(List<String> list, int indice) {
        return this.typeCoin = list.get(indice);
    }
}
