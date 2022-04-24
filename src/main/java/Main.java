import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;

import java.io.IOException;
import java.util.List;
import java.util.stream.Stream;

public class Main {
    // json mapper
    public static ObjectMapper mapper = new ObjectMapper();

    // call server
    public static void main(String[] args) throws IOException {
        CloseableHttpClient httpClient = HttpClientBuilder.create()
                .setDefaultRequestConfig(RequestConfig.custom()
                        .setConnectTimeout(5000)
                        .setSocketTimeout(3000)
                        .setRedirectsEnabled(false)
                        .build())
                .build();

        // create an object
        HttpGet request = new HttpGet("https://raw.githubusercontent.com/netology-code/jd-homeworks/master/http/task1/cats");
        // send a request
        CloseableHttpResponse response = httpClient.execute(request);

        // parse response that is in JSON form to cats List format
        List<Cat> cats = mapper.readValue(response.getEntity().getContent(), new TypeReference<>() {
        });

        // convert cats list in to stream
        Stream<Cat> stream = cats.stream();
        // filter the stream and collect it in to collection
        Stream<Cat> collection = stream.filter(value -> value.getUpvotes() != 0 && value.getUpvotes() > 0);

        // print out filtered stream
        collection.forEach(System.out::println);
    }
}
