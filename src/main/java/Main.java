import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;

import java.io.IOException;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        CloseableHttpClient httpClient = HttpClientBuilder.create()
                .setDefaultRequestConfig(RequestConfig.custom()
                        .setConnectTimeout(5000)    // максимальное время ожидание подключения к серверу
                        .setSocketTimeout(30000)    // максимальное время ожидания получения данных
                        .setRedirectsEnabled(false) // возможность следовать редиректу в ответе
                        .build())
                .build();

        HttpGet request = new HttpGet("https://raw.githubusercontent.com/netology-code/jd-homeworks/master/http/task1/cats");

        try {
            CloseableHttpResponse response = httpClient.execute(request);

            ObjectMapper objectMapper = new ObjectMapper();
            List<ResponseServer> responseServerList = objectMapper.readValue(response.getEntity().getContent().readAllBytes(), new TypeReference<>() {
            });

            responseServerList.stream()
                    .filter(val -> val.getUpvotes() != null && val.getUpvotes() > 0)
                    .forEach(System.out::println);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}
