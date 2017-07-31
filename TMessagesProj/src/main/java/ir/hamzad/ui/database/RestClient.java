package org.telegram.ui.database;

import android.util.Log;

import org.apache.http.Header;
import org.apache.http.HeaderElement;
import org.apache.http.HttpEntity;
import org.apache.http.HttpRequest;
import org.apache.http.HttpRequestInterceptor;
import org.apache.http.HttpResponse;
import org.apache.http.HttpResponseInterceptor;
import org.apache.http.HttpVersion;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.HttpEntityWrapper;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.DefaultHttpRequestRetryHandler;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.HttpContext;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.zip.GZIPInputStream;

import org.telegram.messenger.BuildConfig;

public class RestClient {
    public static final Integer CONNECTION_TIMEOUT;
    public static final int HTTP_ERROR = 500;
    public static final int HTTP_OK = 200;
    public static final int RETRY_COUNT = 3;
    private static final Integer SOCKET_TIMEOUT;
    private static final String TAG;
    private DefaultHttpClient httpClient;
    private String webServiceUrl;

    private static class GzipHttpRequestInterceptor implements HttpRequestInterceptor {
        private GzipHttpRequestInterceptor() {
        }

        public void process(HttpRequest request, HttpContext context) {
            if (!request.containsHeader("Accept-Encoding")) {
                request.addHeader("Accept-Encoding", "gzip");
            }
        }
    }

    private static class GzipHttpResponseInterceptor implements HttpResponseInterceptor {
        private GzipHttpResponseInterceptor() {
        }

        public void process(HttpResponse response, HttpContext context) {
            Header encoding = response.getEntity().getContentEncoding();
            if (encoding != null) {
                inflateGzip(response, encoding);
            }
        }

        private void inflateGzip(HttpResponse response, Header encoding) {
            for (HeaderElement element : encoding.getElements()) {
                if (element.getName().equalsIgnoreCase("gzip")) {
                    response.setEntity(new GzipInflatingEntity(response.getEntity()));
                    return;
                }
            }
        }
    }

    private static class GzipInflatingEntity extends HttpEntityWrapper {
        public GzipInflatingEntity(HttpEntity wrapped) {
            super(wrapped);
        }

        public InputStream getContent() throws IOException {
            return new GZIPInputStream(this.wrappedEntity.getContent());
        }

        public long getContentLength() {
            return -1;
        }
    }

    static {
        TAG = RestClient.class.getSimpleName();
        CONNECTION_TIMEOUT = Integer.valueOf(30000);
        SOCKET_TIMEOUT = Integer.valueOf(30000);
    }

    public DefaultHttpClient getHttpClientInstance() {
        if (this.httpClient != null) {
            return this.httpClient;
        }
        HttpParams params = new BasicHttpParams();
        params.setParameter("http.connection.timeout", CONNECTION_TIMEOUT);
        params.setParameter("http.socket.timeout", SOCKET_TIMEOUT);
        params.setParameter("http.protocol.version", HttpVersion.HTTP_1_1);
        params.setParameter("http.useragent", "Apache-HttpClient/Android");
        params.setParameter("http.connection.stalecheck", Boolean.valueOf(false));
        this.httpClient = new DefaultHttpClient(params);
        this.httpClient.addRequestInterceptor(new GzipHttpRequestInterceptor());
        this.httpClient.addResponseInterceptor(new GzipHttpResponseInterceptor());
        this.httpClient.setHttpRequestRetryHandler(new DefaultHttpRequestRetryHandler(0, false));
        return this.httpClient;
    }

    public RestClient(String webServiceUrl) {
        this.webServiceUrl = webServiceUrl;
    }


    private String createRandParam() {
        return "?rand=" + System.currentTimeMillis();
    }



    private void callRESTurl(String data, HttpPut httpPut) {
    }



    private String convertStreamToString(InputStream is) {
        String line = BuildConfig.FLAVOR;
        StringBuilder total = new StringBuilder();
        BufferedReader rd = new BufferedReader(new InputStreamReader(is));
        while (true) {
            try {
                line = rd.readLine();
                if (line == null) {
                    break;
                }
                total.append(line);
            } catch (Exception e) {
                Log.e(TAG, "There was an input stream read error.", e);
            }
        }
        return total.toString();
    }

    private void logInfo(String info) {
    }

    public void clearCookies() {
        getHttpClientInstance().getCookieStore().clear();
    }

    public void setWebServiceUrl(String webServiceUrl) {
        this.webServiceUrl = webServiceUrl;
    }
}
