package org.example;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;


public class HttpStatusChecker {


    public String getStatusImage(int code) throws IOException {
       String imageUrl = "https://http.cat/" + code + ".jpg";
       URL url = new URL(imageUrl);
       HttpURLConnection connection = (HttpURLConnection) url.openConnection();
       connection.setRequestMethod("GET");
       int responseCode = connection.getResponseCode();
       connection.disconnect();

       if (responseCode == HttpURLConnection.HTTP_NOT_FOUND){
           throw new IOException("Image not found for status cod " + code);
       }
       return imageUrl;
    }

    public static void main(String[] args) {

        HttpStatusChecker checker = new HttpStatusChecker();

        try {
            String imageUrl1 = checker.getStatusImage(200);
            System.out.println("Image URL for status code 200: " + imageUrl1);
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
        try {
            String imageUrl3 = checker.getStatusImage(300);
            System.out.println("Image URL for status code 300: " + imageUrl3);
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }

        try {
            String imageUrl2 = checker.getStatusImage(10000);
            System.out.println("Image URL for status code 10000: " + imageUrl2);
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }
}
