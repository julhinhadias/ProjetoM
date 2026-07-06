// Updated GeminiService to load GEMINI_API_KEY from .env or environment, use Authorization header, handle HTTP error streams and log responses for diagnosis.
package service;

import io.github.cdimascio.dotenv.Dotenv;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class GeminiService {

    public String gerarCidade() {

        try {

            // Carrega credenciais do .env com fallback para variáveis de ambiente
            Dotenv dotenv = Dotenv.load();
            String apiKey = dotenv.get("GEMINI_API_KEY");
            String oauthToken = dotenv.get("GEMINI_OAUTH_TOKEN");

            if (apiKey == null || apiKey.isBlank()) {
                apiKey = System.getenv("GEMINI_API_KEY");
            }

            if (oauthToken == null || oauthToken.isBlank()) {
                oauthToken = System.getenv("GEMINI_OAUTH_TOKEN");
            }

            if ((apiKey == null || apiKey.isBlank()) && (oauthToken == null || oauthToken.isBlank())) {
                System.err.println("Nenhuma credencial encontrada. Defina GEMINI_API_KEY ou GEMINI_OAUTH_TOKEN no .env ou nas variáveis de ambiente.");
                return null;
            }

            String prompt = """
                    Gere uma cidade brasileira para cadastro.
                    Responda EXATAMENTE neste formato:

                    nome=NomeDaCidade
                    estado=UF
                    distancia=123
                    """;

            String baseEndpoint = "https://generativelanguage.googleapis.com/v1beta/models/gemini-2.5-flash:generateContent";

            // Se houver token OAuth, usamos Authorization Bearer. Caso contrário, usamos API key via query param (?key=...)
            boolean usingOauth = oauthToken != null && !oauthToken.isBlank();

            String endpoint = baseEndpoint;
            if (!usingOauth) {
                // usar API key como query param
                endpoint = endpoint + "?key=" + java.net.URLEncoder.encode(apiKey, "UTF-8");
            }

            URL url = new URL(endpoint);

            HttpURLConnection conexao = (HttpURLConnection) url.openConnection();

            conexao.setRequestMethod("POST");
            conexao.setRequestProperty("Content-Type", "application/json");
            if (usingOauth) {
                conexao.setRequestProperty("Authorization", "Bearer " + oauthToken);
            }

            conexao.setConnectTimeout(10_000);
            conexao.setReadTimeout(30_000);

            conexao.setDoOutput(true);

            String json = """
            {
              "contents": [{
                "parts": [{
                  "text": "%s"
                }]
              }]
            }
            """.formatted(prompt.replace("\"", "\\\""));

            try (OutputStream os = conexao.getOutputStream()) {
                os.write(json.getBytes());
            }

            int code = conexao.getResponseCode();

            InputStream stream = (code >= 200 && code < 300) ? conexao.getInputStream() : conexao.getErrorStream();

            BufferedReader leitor = new BufferedReader(new InputStreamReader(stream));

            StringBuilder resposta = new StringBuilder();
            String linha;

            while ((linha = leitor.readLine()) != null) {
                resposta.append(linha);
            }

            leitor.close();

            String texto = resposta.toString();

            // Log da resposta bruta para diagnóstico
            System.out.println("[GeminiService] HTTP code=" + code + " response=" + texto);

            if (code < 200 || code >= 300) {
                // Em caso de erro HTTP, retornar null para ser tratado pela UI e já ter log no console
                return null;
            }

            System.out.println("Texto retornado:");
            System.out.println(texto);

            System.out.println("Contém 'text'? " + texto.contains("text"));

            // Extrai a resposta do Gemini (campo "text")
            int inicio = texto.indexOf("\"text\": \"");

            System.out.println(texto.indexOf("\"text\": \""));
            if (inicio == -1) {
                System.err.println("Campo 'text' não encontrado na resposta do Gemini.");
                return null;
            }

            inicio += 8; // posiciona após "text":"

            int fim = texto.indexOf("\"", inicio);

            if (fim == -1) {
                System.err.println("Resposta do Gemini inesperada ao extrair texto.");
                return null;
            }

            return texto.substring(inicio, fim).replace("\\n", "\n");

        } catch (Exception e) {

            e.printStackTrace();
            return null;
        }
    }
}