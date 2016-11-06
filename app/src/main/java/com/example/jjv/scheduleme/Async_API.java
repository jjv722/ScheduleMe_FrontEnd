package com.example.jjv.scheduleme;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by jjv on 11/5/16.
 */

public class Async_API extends AsyncTask<URL, Void, JSONObject> {
        public interface callback{
            void start();
            void success(JSONObject jsonObject);
            void error();
        }
        private callback cb;
        private String username;
        private String password;

        public Async_API(final callback cb, final JSONObject info) {
            Log.d("constructor:", info.toString());
            this.cb = cb;
            try {
                this.username = (String) info.get("u");
                this.password = (String) info.get("p");
                Log.d("username", (String) info.get("u"));
                Log.d("password", (String) info.get("p"));
            }
            catch(Exception e) {

            }

        }

        @Override
        protected void onPostExecute(JSONObject jsonObject) {
            super.onPostExecute(jsonObject);
            if (jsonObject != null){
                cb.success(jsonObject);
            } else {
                cb.error();
            }
        }

        @Override
        protected void onCancelled(JSONObject jsonObject) {
            super.onCancelled(jsonObject);
            cb.error();
        }

        @Override
        protected void onCancelled() {
            super.onCancelled();
            cb.error();
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            cb.start();
        }

        @Override
        protected JSONObject doInBackground(URL... urls) {
            Log.d("AsyncMe", urls[0].toString());
            URL url = urls[0];
            HttpURLConnection urlConnection;
            JSONObject result = null;
            try {
                JSONObject json = new JSONObject();
                json.put("u", this.username);
                json.put("p", this.password);

                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestProperty("Content-Type", "application/json");

                urlConnection.setDoOutput(true);
                urlConnection.setRequestMethod("POST");
                OutputStreamWriter writer = new OutputStreamWriter(urlConnection.getOutputStream());
                writer.write(json.toString());
                writer.close();

                Log.d("status", ""+urlConnection.getResponseCode());
                Log.d("status", ""+urlConnection.getResponseMessage());

                if (urlConnection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                    InputStream in = urlConnection.getInputStream();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(in));
                    StringBuilder sb = new StringBuilder();
                    String line;
                    while((line = reader.readLine()) != null) {
                        sb.append(line);
                    }
                    Log.d("sb", sb.toString());
                    result = new JSONObject(sb.toString());
                }
            } catch(Exception e){
                Log.d("error", e.toString());
            }
            return result;
        }
}

