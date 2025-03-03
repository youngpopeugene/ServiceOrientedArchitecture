package soa.spring.teamservice;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.SneakyThrows;
import org.apache.hc.core5.http.HttpHeaders;

import javax.net.ssl.*;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.Properties;

@AllArgsConstructor
@Data
public class HTTPClient {

    private HttpClient httpClient;

    static {
        //for localhost testing only
        javax.net.ssl.HttpsURLConnection.setDefaultHostnameVerifier((hostname, sslSession) -> true);
    }

    public HTTPClient() throws NoSuchAlgorithmException, KeyManagementException {
        // Create a trust manager that does not validate certificate chains
        TrustManager[] trustAllCerts = new TrustManager[]{
                new X509TrustManager() {
                    public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                        return null;
                    }

                    public void checkClientTrusted(
                            java.security.cert.X509Certificate[] certs, String authType) {
                    }

                    public void checkServerTrusted(
                            java.security.cert.X509Certificate[] certs, String authType) {
                    }
                }
        };

        SSLContext sc = SSLContext.getInstance("SSL");
        sc.init(null, trustAllCerts, new java.security.SecureRandom());
        HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());

        // Create all-trusting host name verifier
        HostnameVerifier allHostsValid = new HostnameVerifier() {
            public boolean verify(String hostname, SSLSession session) {
                return true;
            }
        };

        // Install the all-trusting host verifier
        HttpsURLConnection.setDefaultHostnameVerifier(allHostsValid);

        Properties props = System.getProperties();
        props.setProperty("jdk.internal.httpclient.disableHostnameVerification", Boolean.TRUE.toString());

        this.httpClient = HttpClient.newBuilder()
                .sslContext(sc)
                .build();
    }

    @SneakyThrows
    public Response GET(String url, String data) {
        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .method("GET", HttpRequest.BodyPublishers.ofString(data))
                    .setHeader(HttpHeaders.CONTENT_TYPE, "application/xml")
                    .header(HttpHeaders.ACCEPT, "application/xml")
                    .build();
            var response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            var code = response.statusCode();
            var content = response.body();
            return new Response(content, code);
        } catch (UnsupportedEncodingException e) {
            System.out.println(e.fillInStackTrace());
            throw new ServiceException("Unsupported encoding", 400);
        } catch (Exception e) {
            System.out.println(e.fillInStackTrace());
            throw new ServiceException("External service unavailable", 503);
        }
    }

    @SneakyThrows
    public Response POST(String url, String data) {
        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .method("POST", HttpRequest.BodyPublishers.ofString(data))
                    .setHeader(HttpHeaders.CONTENT_TYPE, "application/xml")
                    .header(HttpHeaders.ACCEPT, "application/xml")
                    .build();
            var response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            var code = response.statusCode();
            var content = response.body();
            return new Response(content, code);
        } catch (UnsupportedEncodingException e) {
            System.out.println(e.fillInStackTrace());
            throw new ServiceException("Unsupported encoding", 400);
        } catch (Exception e) {
            System.out.println(e.fillInStackTrace());
            throw new ServiceException("External service unavailable", 503);
        }
    }

    @SneakyThrows
    public Response PUT(String url, String data) {
        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .method("PUT", HttpRequest.BodyPublishers.ofString(data))
                    .setHeader(HttpHeaders.CONTENT_TYPE, "application/xml")
                    .header(HttpHeaders.ACCEPT, "application/xml")
                    .build();
            var response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            var code = response.statusCode();
            var content = response.body();
            return new Response(content, code);
        } catch (UnsupportedEncodingException e) {
            System.out.println(e.fillInStackTrace());
            throw new ServiceException("Unsupported encoding", 400);
        } catch (Exception e) {
            System.out.println(e.fillInStackTrace());
            throw new ServiceException("External service unavailable", 503);
        }
    }

    @SneakyThrows
    public Response PATCH(String url, String data) {
        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .method("PATCH", HttpRequest.BodyPublishers.ofString(data))
                    .setHeader(HttpHeaders.CONTENT_TYPE, "application/xml")
                    .header(HttpHeaders.ACCEPT, "application/xml")
                    .build();
            var response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            var code = response.statusCode();
            var content = response.body();
            return new Response(content, code);
        } catch (UnsupportedEncodingException e) {
            System.out.println(e.fillInStackTrace());
            throw new ServiceException("Unsupported encoding", 400);
        } catch (Exception e) {
            System.out.println(e.fillInStackTrace());
            throw new ServiceException("External service unavailable", 503);
        }
    }

    @SneakyThrows
    public Response DELETE(String url) {
        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .method("DELETE", HttpRequest.BodyPublishers.ofString(""))
                    .setHeader(HttpHeaders.CONTENT_TYPE, "application/xml")
                    .header(HttpHeaders.ACCEPT, "application/xml")
                    .build();
            var response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            var code = response.statusCode();
            var content = response.body();
            return new Response(content, code);
        } catch (UnsupportedEncodingException e) {
            System.out.println(e.fillInStackTrace());
            throw new ServiceException("Unsupported encoding", 400);
        } catch (Exception e) {
            System.out.println(e.fillInStackTrace());
            throw new ServiceException("External service unavailable", 503);
        }
    }
}


