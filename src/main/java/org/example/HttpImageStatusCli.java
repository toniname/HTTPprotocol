package org.example;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.logging.Logger;

public class HttpImageStatusCli {
    private final HttpStatusImageDownloader downloader;

    Logger logger = Logger .getLogger(getClass().getName());

    public HttpImageStatusCli() {
        this.downloader = new HttpStatusImageDownloader();
    }

    public void askStatus() {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        logger.info("Enter HTTP status code:");
        try {
            String input = reader.readLine();
            if (input.matches("\\d+")) {
                int statusCode = Integer.parseInt(input);
                try {
                    downloader.downloadStatusImage(statusCode);
                    logger.fine("Image downloaded successfully for status code " + statusCode);
                } catch (IOException e) {
                    logger.info("Error: " + e.getMessage());
                }
            } else {
                logger.info("Please enter a valid number.");
            }
        } catch (IOException e) {
            logger.info("Error reading input: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        HttpImageStatusCli cli = new HttpImageStatusCli();
        cli.askStatus();
    }
}
