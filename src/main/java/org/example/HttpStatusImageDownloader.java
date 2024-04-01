package org.example;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class HttpStatusImageDownloader {

    public void downloadStatusImage(int code) throws IOException {
        HttpStatusChecker checker = new HttpStatusChecker();
        String imageUrl = checker.getStatusImage(code);

        if (imageUrl != null) {
            URL url = new URL(imageUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            int responseCode = connection.getResponseCode();

            if (responseCode == HttpURLConnection.HTTP_OK) {
                try (InputStream inputStream = connection.getInputStream();
                     OutputStream outputStream = new FileOutputStream("status_" + code + ".jpg")) {

                    byte[] buffer = new byte[1024];
                    int bytesRead;
                    while ((bytesRead = inputStream.read(buffer)) != -1) {
                        outputStream.write(buffer, 0, bytesRead);
                    }
                }
            } else {
                throw new IOException("Failed to download image for status code " + code);
            }

            connection.disconnect();
        } else {
            throw new IOException("Image not found for status code " + code);
        }
    }

    public static void main(String[] args) {
        HttpStatusImageDownloader downloader = new HttpStatusImageDownloader();

        try {
            downloader.downloadStatusImage(200);
            System.out.println("Image downloaded successfully for status code 200");
        } catch (IOException e) {
            System.err.println("Error: " + e.getMessage());
        }

        try {
            downloader.downloadStatusImage(10000);
            System.out.println("Image downloaded successfully for status code 10000");
        } catch (IOException e) {
            System.err.println("Error: " + e.getMessage());
        }
    }
}
