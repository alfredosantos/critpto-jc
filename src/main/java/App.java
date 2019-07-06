import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Joiner;
import java.io.File;
import java.io.IOException;
import java.math.BigInteger;
import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Pattern;

public class App {

  public static void main(String[] args) throws Exception {

    HttpClient http = new HttpClient();
    System.out.println("Send Http GET request");

    final CriptoBody criptoBody = http.getCriptoBody();
    final List<String> decript = new LinkedList<>();
    final String decifrado;

    LinkedAlphabet linkedAlphabet = new LinkedAlphabet();
    Arrays.stream(criptoBody.getCifrado().toLowerCase().split("|")).forEach(letter -> {
      if (!Pattern.matches("[a-z]", letter)) {
        decript.add(letter);
      } else {
        final int nextPos = linkedAlphabet.getAlphabetNext(criptoBody.getNumero_casas(), letter);
        decript.add(linkedAlphabet.getLetter(nextPos));
      }
    });

    decifrado = Joiner.on("").join(decript);

    MessageDigest mDigest = MessageDigest.getInstance("SHA1");
    byte[] result = mDigest.digest(decifrado.getBytes(
        Charset.forName("UTF-8")));
    criptoBody.setDecifrado(decifrado);
    criptoBody.setResumo_criptografico(
        new BigInteger(1, result).toString(16));

    createFile(criptoBody);

    System.err.println(decifrado + " Decript");

    System.out.println("\nTesting 2 - Send Http POST request");
    http.sendPost("answer.json");
  }


  public static void createFile(CriptoBody criptoBody) throws IOException {
    ObjectMapper objectMapper = new ObjectMapper();
    objectMapper.writeValue(new File("answer.json"), criptoBody);
  }
}