import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;

public class HttpClient {

  private final String USER_AGENT = "Mozilla/5.0";

  // HTTP GET request
  public CriptoBody getCriptoBody() throws Exception {

    String url = "https://api.codenation.dev/v1/challenge/dev-ps/generate-data?token=53e7ef0e10c929e1487f2e5cadf2259fcec2723b";
    final RequestConfig requestConfig = RequestConfig.custom()
        .setProxy(HttpHost.create("")).build();
    final CloseableHttpClient client = HttpClientBuilder.create()
        .setDefaultRequestConfig(requestConfig).build();

    HttpGet request = new HttpGet(url);

    // add request header
    request.addHeader("User-Agent", USER_AGENT);

    HttpResponse response = client.execute(request);

    System.out.println("\nSending 'GET' request to URL : " + url);
    System.out.println("Response Code : " +
        response.getStatusLine().getStatusCode());

    CriptoBody obj;
    try (BufferedReader rd = new BufferedReader(
        new InputStreamReader(response.getEntity().getContent()))) {

      StringBuffer result = new StringBuffer();
      String line = "";
      while ((line = rd.readLine()) != null) {
        result.append(line);
      }

      ObjectMapper mapper = new ObjectMapper();

      obj = null;

      try {
        // String to Java object
        obj = mapper.readValue(result.toString(), CriptoBody.class);

      } catch (IOException e) {
        e.printStackTrace();
      }

    }
    return obj;
  }

  // HTTP POST request
  void sendPost(String file) throws Exception {

    String url = "https://api.codenation.dev/v1/challenge/dev-ps/submit-solution?token=53e7ef0e10c929e1487f2e5cadf2259fcec2723b";
    final RequestConfig requestConfig = RequestConfig.custom()
        .setProxy(HttpHost.create("spobrproxy.serasa.intranet:3128")).build();
    final CloseableHttpClient client = HttpClientBuilder.create()
        .setDefaultRequestConfig(requestConfig).build();

    HttpPost httpPost = new HttpPost(url);

    final HttpEntity answer = MultipartEntityBuilder.create()
        .setMode(HttpMultipartMode.BROWSER_COMPATIBLE)
        .setCharset(Charset.forName("UTF-8"))
        .addBinaryBody("answer", new File(file))
        .build();

    httpPost.setEntity(answer);
    HttpResponse response = client.execute(httpPost);

    System.out.println("\nSending 'POST' request to URL : " + url);
    System.out.println("Response Code : " +
        response.getStatusLine().getStatusCode());

    try (BufferedReader rd = new BufferedReader(
        new InputStreamReader(response.getEntity().getContent()))) {

      StringBuffer result = new StringBuffer();
      String line = "";
      while ((line = rd.readLine()) != null) {
        result.append(line);
      }
      System.out.println("Codenation response: " + result.toString());
    }
  }
}