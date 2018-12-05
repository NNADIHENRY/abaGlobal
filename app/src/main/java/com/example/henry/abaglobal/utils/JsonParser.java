package com.example.henry.abaglobal.utils;

/**
 * Created by nnenna on 11/20/18.
 */

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;

/**
 * Created by samuel on 2/23/18.
 */

public class JsonParser {
    String charset = "UTF-8";
    HttpURLConnection conn;
    DataOutputStream wr;
    StringBuffer result;
    URL urlObj;
    JSONObject jObj = null;
    StringBuilder sbParams;
    String paramsString;

    public JSONObject makeHttpRequest(String url, String method,
                                      HashMap<String, String> params) {

        sbParams = new StringBuilder();
        int i = 0;
        for (String key : params.keySet()) {
            try {
                if (i != 0) {
                    sbParams.append("&");
                }
                sbParams.append(key).append("=")
                        .append(URLEncoder.encode(params.get(key), charset));

            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            i++;
        }

        // request method is POST
        if (method.equals("POST")) {

            try {
                urlObj = new URL(url);

                conn = (HttpURLConnection) urlObj.openConnection();

                conn.setDoOutput(true);
                conn.setDoInput(true);

                conn.setRequestMethod("POST");

                conn.setRequestProperty("Accept-Charset", charset);

                conn.setReadTimeout(15000);
                conn.setConnectTimeout(15000);

                conn.connect();

                paramsString = sbParams.toString();

                wr = new DataOutputStream(conn.getOutputStream());
                wr.writeBytes(paramsString);
                wr.flush();
                wr.close();

                int responseCode = conn.getResponseCode();

                if (responseCode == HttpURLConnection.HTTP_OK) {

                    InputStream in = new BufferedInputStream(conn.getInputStream());
                    BufferedReader reader = new BufferedReader(new InputStreamReader(in));
                    result = new StringBuffer();
                    String line;
                    while ((line = reader.readLine()) != null) {
                        result.append(line);

                        break;
                    }

                    reader.close();

                    jObj = new JSONObject(result.toString());

                } else {
                    //return new JSONObject("false " + responseCode);
                    return jObj = null;
                }

            } catch (IOException e) {
                e.printStackTrace();
                Log.i("Timeout", "connection time-out");
            } catch (JSONException e) {
                e.printStackTrace();
            } finally {
                conn.disconnect();
            }
        } else if (method.equals("PUT")) {
            try {
                urlObj = new URL(url);

                conn = (HttpURLConnection) urlObj.openConnection();

                conn.setDoOutput(true);
                conn.setDoInput(true);

                conn.setRequestMethod("PUT");

                conn.setRequestProperty("Accept-Charset", charset);

                conn.setReadTimeout(15000);
                conn.setConnectTimeout(15000);

                conn.connect();

                paramsString = sbParams.toString();

                wr = new DataOutputStream(conn.getOutputStream());
                wr.writeBytes(paramsString);
                wr.flush();
                wr.close();

                int responseCode = conn.getResponseCode();

                if (responseCode == HttpURLConnection.HTTP_OK) {

                    InputStream in = new BufferedInputStream(conn.getInputStream());
                    BufferedReader reader = new BufferedReader(new InputStreamReader(in));
                    result = new StringBuffer();
                    String line;
                    while ((line = reader.readLine()) != null) {
                        result.append(line);

                        break;
                    }

                    reader.close();

                    jObj = new JSONObject(result.toString());

                } else {
                    //return new JSONObject("false " + responseCode);
                    return jObj = null;
                }

            } catch (IOException e) {
                e.printStackTrace();
                Log.i("Timeout", "connection time-out");
            } catch (JSONException e) {
                e.printStackTrace();
            } finally {
                conn.disconnect();
            }
        }

        return jObj;
    }


}