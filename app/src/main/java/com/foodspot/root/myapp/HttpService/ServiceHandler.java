package com.foodspot.root.myapp.HttpService;

import android.util.Log;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

/**
 * Created by root on 04/09/16.
 */
public class ServiceHandler {
    static String response = null;
    public final static int GET = 1;
    public final static int POST = 2;

    public ServiceHandler() {
    }

    public String makeServiceCall(String url, int method) {
        try {
            // http client
            DefaultHttpClient httpClient = new DefaultHttpClient();
            HttpEntity httpEntity = null;
            HttpResponse httpResponse = null;

            // Checking http request method type
            if (method == POST) { // Jika Method = POST
                HttpPost httpPost = new HttpPost(url);
                // mengeksekusi request
                httpResponse = httpClient.execute(httpPost);

            } else if (method == GET) { // Jika Method = GET
                HttpGet httpGet = new HttpGet(url);
                // mengeksekusi request
                httpResponse = httpClient.execute(httpGet);

            }

            httpEntity = httpResponse.getEntity();
            // Mendapatkan hasil dari request
            response = EntityUtils.toString(httpEntity);


        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Log.d("Response", "Response : " + response);
        return response; // mengembalikan hasil
    }
}
