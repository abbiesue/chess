package server;

import com.google.gson.Gson;
import records.*;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.*;
import java.util.HashMap;
import java.util.Map;

public class ServerFacade {
    private final String serverURL;

    public ServerFacade(String serverURL) {
        this.serverURL = serverURL;
    }

    public RegisterResult register(RegisterRequest request) throws ResponseException {
        var path = "/user";
        return this.makeRequest("POST", path, request, RegisterResult.class);
    }

    public LoginResult login(LoginRequest request) throws ResponseException {
        var path = "/session";
        return this.makeRequest("POST", path, request, LoginResult.class);
    }

    public LogoutResult logout(LogoutRequest request) throws ResponseException {
        var path = "/session";
        return this.makeRequest("DELETE", path, request, LogoutResult.class);
    }

    public CreateResult create(CreateRequest request) throws ResponseException {
        var path = "/game";
        return this.makeRequest("POST", path, request, CreateResult.class);
    }

    public JoinResult join(JoinRequest request) throws ResponseException {
        var path = "/game";
        return this.makeRequest("PUT", path, request, JoinResult.class);
    }

    public ListResult list(ListRequest request) throws ResponseException {
        var path = "/game";
        return this.makeRequest("GET", path, request, ListResult.class);
    }

    public ClearResult clear() throws ResponseException {
        var path = "/db";
        return this.makeRequest("DELETE", path, null, ClearResult.class);
    }



    private <T> T makeRequest(String method, String path, Object request, Class<T> responseClass) throws ResponseException {
        try {
            URL url = (new URI(serverURL + path)).toURL();
            HttpURLConnection http = (HttpURLConnection) url.openConnection();
            http.setRequestMethod(method);
            http.setDoOutput(true);

            Map<String, String> headers = extractHeaders(request);
            for (Map.Entry<String, String> header : headers.entrySet()) {
                http.setRequestProperty(header.getKey(), header.getValue());
            }

            writeBody(request, http);
            http.connect();
            throwIfNotSuccessful(http);
            return readBody(http, responseClass);
        } catch (ResponseException e) {
            throw e;
        } catch (Exception e) {
            throw new ResponseException(500, e.getMessage());
        }
    }

    private static void writeBody(Object request, HttpURLConnection http) throws IOException {
        if (request != null && !"GET".equals(http.getRequestMethod())) {
            http.addRequestProperty("Content-Type", "application/json");
            String reqData = new Gson().toJson(request);
            try (OutputStream reqBody = http.getOutputStream()) {
                reqBody.write(reqData.getBytes());
            }
        }
    }

    private static <T> T readBody(HttpURLConnection http, Class<T> responseClass) throws IOException {
        T response = null;
        if (http.getContentLength() < 0) {
            try (InputStream respBody = http.getInputStream()) {
                InputStreamReader reader = new InputStreamReader(respBody);
                if (responseClass != null) {
                    response = new Gson().fromJson(reader, responseClass);
                }
            }
        }
        return response;
    }

    private Map<String, String> extractHeaders(Object request) {
        Map<String, String> headers = new HashMap<>();
        final String AUTH_HEADER = "Authorization";

        if (request instanceof LogoutRequest req) {
            headers.put(AUTH_HEADER, req.authToken());
        }
        if (request instanceof CreateRequest req) {
            headers.put(AUTH_HEADER, req.authToken());
        }
        if (request instanceof ListRequest req) {
            headers.put(AUTH_HEADER, req.authToken());
        }
        if (request instanceof JoinRequest req) {
            headers.put(AUTH_HEADER, req.authToken());
        }

        return headers;
    }

    private void throwIfNotSuccessful(HttpURLConnection http) throws IOException, ResponseException{
        var status = http.getResponseCode();
        if (!isSuccessful(status)) {
            try(InputStream respErr = http.getErrorStream()) {
                if (respErr != null) {
                    throw ResponseException.fromJson(respErr);
                }
            }
            throw new ResponseException(status, "other failure: " + status);
        }
    }

    private boolean isSuccessful(int status) {
        return status / 100 == 2;
    }
}
