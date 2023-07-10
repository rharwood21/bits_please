package bits_please_api;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class BitsPleaseRequest {
    private String apiKey;
    public BitsPleaseRequest(String apiKey){
        this.apiKey = apiKey;
    }
    public JSONObject sendRequest(String apiEndpointUrl, JSONObject json) throws IOException {
        // https://stackoverflow.com/questions/7181534/http-post-using-json-in-java

        CloseableHttpClient httpClient = HttpClientBuilder.create().build();

        try {
            HttpPost request = new HttpPost(apiEndpointUrl);
            StringEntity requestEntity = new StringEntity(
                    json.toString(),
                    ContentType.APPLICATION_JSON
            );
            request.addHeader("Content-Type", "application/json");
            request.addHeader("x-api-key", apiKey);
            request.setEntity(requestEntity);

            CloseableHttpResponse httpResponse = httpClient.execute(request);
            // Check Status Code & Get Content
            int statusCode = httpResponse.getStatusLine().getStatusCode();
            StringBuffer response = new StringBuffer();
            if (statusCode >= 200 && statusCode < 300){  // Good
                BufferedReader reader = new BufferedReader(new InputStreamReader(
                        httpResponse.getEntity().getContent()));
                String inputLine;


                while ((inputLine = reader.readLine()) != null) {
                    response.append(inputLine);
                }
                reader.close();
            } else {
                throw new APIRequestException("API Returned Non-2XX HTTP Status Code.");
            }
            return new JSONObject(response.toString());
        } catch (Exception ex) {
            // handle exception here
        } finally {
            httpClient.close();
        }
        return null;
    }
}
