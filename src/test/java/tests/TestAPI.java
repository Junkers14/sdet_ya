package tests;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.testng.annotations.Test;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class TestAPI {

    @Test
    public  void testAPICase(){
/*        try {
            HttpURLConnection connection = (HttpURLConnection) new URL("https://reqres.in/api/users").openConnection();
            connection.setRequestMethod("GET");
            InputStream is = connection.getInputStream();
            byte[] bytes= new byte[is.available()];
            is.read(bytes);
            System.out.print(new String(bytes));
        } catch (Exception ignored) {}*/
        String lineFromBuffer, resultString = null;
        try (CloseableHttpClient httpClient = HttpClientBuilder.create().build()) {

            HttpGet request = new HttpGet("https://reqres.in/api/users");
            HttpResponse response = httpClient.execute(request);
            BufferedReader bufferedReader = new BufferedReader (new InputStreamReader(response.getEntity().getContent()));

            while ((lineFromBuffer = bufferedReader.readLine()) != null) {
                resultString=lineFromBuffer;}
            System.out.println(resultString);
        } catch (Exception ignored) {}
    }
}
