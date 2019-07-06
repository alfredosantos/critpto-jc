import com.fasterxml.jackson.annotation.JsonProperty;

@lombok.Data
public class CriptoBody {
  @JsonProperty("numero_casas")
  int numero_casas;
  @JsonProperty("token")
  String token;
  @JsonProperty("cifrado")
  String cifrado;
  @JsonProperty("decifrado")
  String decifrado;
  @JsonProperty("resumo_criptografico")
  String resumo_criptografico;
}
