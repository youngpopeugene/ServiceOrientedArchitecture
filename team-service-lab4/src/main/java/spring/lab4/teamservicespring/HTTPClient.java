package spring.lab4.teamservicespring;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.SneakyThrows;
import org.apache.hc.core5.http.HttpHeaders;
import spring.lab4.teamservicespring.exception.ServiceFault;
import spring.lab4.teamservicespring.exception.ServiceFaultException;
import spring.lab4.teamservicespring.model.Response;

import javax.net.ssl.*;
import java.io.IOException;
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
        HttpsURLConnection.setDefaultHostnameVerifier((hostname, sslSession) -> true);
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
    public Response PATCH(String url, String data) throws ServiceFaultException {
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
            if (code == 404) {
                throw new ServiceFaultException("Error", new ServiceFault("404", "Not Found"));
            }
            if (code == 400) {
                throw new ServiceFaultException("Error", new ServiceFault("400", "Validation Error"));
            }
            return new Response(code, content);
        } catch (UnsupportedEncodingException e) {
            System.out.println(e.fillInStackTrace());
            throw new ServiceFaultException("Error", new ServiceFault("400", "Unsupported encoding"));
        } catch (InterruptedException | IOException e) {
            System.out.println(e.fillInStackTrace());
            throw new ServiceFaultException("Error", new ServiceFault("503", "External service unavailable"));
        }
    }

    @SneakyThrows
    public Response DELETE(String url) throws ServiceFaultException {
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
            if (content.contains("HumanBeing does not belong to the specified team")) {
                throw new ServiceFaultException("Error", new ServiceFault("404", "HumanBeing does not belong to the specified team"));
            }
            if (code == 404) {
                throw new ServiceFaultException("Error", new ServiceFault("404", "Not Found"));
            }
            if (code == 400) {
                throw new ServiceFaultException("Error", new ServiceFault("400", "Validation Error"));
            }
            return new Response(code, content);
        } catch (UnsupportedEncodingException e) {
            System.out.println(e.fillInStackTrace());
            throw new ServiceFaultException("Error", new ServiceFault("400", "Unsupported encoding"));
        } catch (InterruptedException | IOException e) {
            System.out.println(e.fillInStackTrace());
            throw new ServiceFaultException("Error", new ServiceFault("503", "External service unavailable"));
        }
    }
}


