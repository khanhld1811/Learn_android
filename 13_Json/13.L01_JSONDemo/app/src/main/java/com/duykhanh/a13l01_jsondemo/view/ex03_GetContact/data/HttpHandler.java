package com.duykhanh.a13l01_jsondemo.view.ex03_GetContact.data;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class HttpHandler {

    public HttpHandler() {
    }

    public String makeServiceCall(String reqUrl){
        String response = null;
        try {
                URL url = new URL(reqUrl);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            InputStream in = new BufferedInputStream(conn.getInputStream());
            response = convertStreamToString(in);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }catch (IOException e) {
            e.printStackTrace();
        }
        return response;
    }

    private String convertStreamToString(InputStream in) {
        BufferedReader read = new BufferedReader(new InputStreamReader(in));
        StringBuilder sb = new StringBuilder();
        String line;
        try{
            while((line = read.readLine()) != null){
                sb.append(line).append("\n");
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        finally {
            try {
                in.close();
            }
            catch (Exception ex){
                ex.printStackTrace();
            }
        }
        return sb.toString();
    }
}
