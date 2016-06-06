package com.github.vedenin.useful_links.downloader;

import com.github.vedenin.useful_links.exceptions.DownloadException;
import com.google.common.base.Charsets;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.io.File;

import static com.github.vedenin.useful_links.Constants.USER_AGENT;

/**
 * Class to simple download resources
 *
 * Created by vvedenin on 6/6/2016.
 */
public class Downloader {
    private boolean isSSLEnabled = false;

    private void initHTTPSDownload() {
        if (!isSSLEnabled) {
            // Create a new_version trust manager that trust all certificates
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

            // Activate the new_version trust manager
            try {
                SSLContext sc = SSLContext.getInstance("SSL");
                sc.init(null, trustAllCerts, new java.security.SecureRandom());
                HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
            } catch (Exception e) {
                System.out.print(e.getMessage());
            }
            isSSLEnabled = true;
        }
    }

    public Document getPage(String url) {
        try {
            initHTTPSDownload();
            if (url.startsWith("file:")) {
                File file = new File(url.replace("file:", ""));
                return Jsoup.parse(file, Charsets.UTF_8.name());
            } else {
                return Jsoup.connect(url).userAgent(USER_AGENT).timeout(30000).get();
            }
        } catch (Exception e) {
            throw new DownloadException("Can't download url = " + url, e);
        }
    }

    private static Downloader download;

    public static Downloader instance() {
        if(download == null) {
            download = new Downloader();
        }
        return download;
    }
}
