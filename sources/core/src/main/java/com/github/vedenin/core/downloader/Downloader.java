package com.github.vedenin.core.downloader;

import com.github.vedenin.core.downloader.utils.DownloadConstants;
import com.github.vedenin.thirdpartylib.htmlparser.DocumentAtom;
import com.github.vedenin.thirdpartylib.htmlparser.HTMLParserAtom;
import com.github.vedenin.core.common.exceptions.DownloadException;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.io.File;


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

    public DocumentAtom getPage(String url) {
        try {
            Thread.sleep(200);
            initHTTPSDownload();
            if (url.startsWith("file:")) {
                File file = new File(url.replace("file:", ""));
                return HTMLParserAtom.parseFile(file);
            } else {
                return HTMLParserAtom.parseUrl(url, DownloadConstants.USER_AGENT, 30000);
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
