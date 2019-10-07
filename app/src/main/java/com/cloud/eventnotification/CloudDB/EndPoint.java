package com.cloud.eventnotification.CloudDB;
import java.io.*;
import java.net.*;
public class EndPoint {

    public static int getTh(String aid) {
      int th=60;
      try {
          URL url = new URL("https://s3650616-myapi.appspot.com/_ah/api/echo/v1/echo");

          HttpURLConnection conn = (HttpURLConnection) url.openConnection();

          conn.setRequestMethod("POST");

          conn.setRequestProperty("Content-Type", "application/json");
          conn.setRequestProperty("Connection", "Keep-Alive");
          conn.setUseCaches(false);
          conn.setDoOutput(true);
          conn.setDoInput(true);
          String a = "{\"message\":\"" + aid + "\"}";
          OutputStream os = conn.getOutputStream();
          os.write(a.getBytes());

          os.flush();
          os.close();

          conn.connect();

          BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
          StringBuilder sb = new StringBuilder();
          String line;
          while ((line = br.readLine()) != null) {

              sb.append(line);
          }
          String b = sb.toString();
          b = b.substring(14, b.length() - 2);
          th = Integer.parseInt(b);
          br.close();
      }
      catch (Exception e)
      {
          e.printStackTrace();
      }

       return th;
    }
    public static int getRa(String aid) {
        int th=1;
        try {
            URL url = new URL("https://s3650616-myapi.appspot.com/_ah/api/echo/v1/echo");

            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            conn.setRequestMethod("POST");

            conn.setRequestProperty("Content-Type", "application/json");
            conn.setRequestProperty("Connection", "Keep-Alive");
            conn.setUseCaches(false);
            conn.setDoOutput(true);
            conn.setDoInput(true);
            String a = "{\"message\":\"" + aid + "\",\"n\":1}";
            OutputStream os = conn.getOutputStream();
            os.write(a.getBytes());

            os.flush();
            os.close();

            conn.connect();

            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = br.readLine()) != null) {

                sb.append(line);
            }
            String b = sb.toString();
            b = b.substring(14, b.length() - 2);
            th = Integer.parseInt(b);
            br.close();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        return th;
    }
}
