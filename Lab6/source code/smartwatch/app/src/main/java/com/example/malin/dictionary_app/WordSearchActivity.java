package com.example.malin.dictionary_app;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class WordSearchActivity extends AppCompatActivity {
    String sourceText;
    TextView outputTextView;
    TextView sourceTextView;
    TextView partOfSpeechView;
    TextView partOfSpeechLabel;
    Button clickme;
    ArrayList<WordData> wordDataList=new ArrayList<>();
    ProgressDialog progressDialog;
    String API_KEY = "88170567b3f6c2e0";
    String HOURLY_URL = "/hourly/q/";
    String BASE_URL = "https://wordsapiv1.p.mashape.com/words/" ;
    String FINAL_URL = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search);
        //outputTextView = (TextView) findViewById(R.id.response);
        //partOfSpeechView = (TextView) findViewById(R.id.partOfSpeech);
        partOfSpeechLabel = (TextView) findViewById(R.id.textlabel);
        clickme = (Button) findViewById(R.id.clickme);
        sourceTextView = (TextView) findViewById(R.id.txt_search);
        clickme.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                sourceText = sourceTextView.getText().toString();
                //findMeaning(sourceText);
                FINAL_URL = BASE_URL+sourceText+"/frequency";
                new WordTask().execute(FINAL_URL);
            }
        });
    }

    class WordTask extends AsyncTask<String, String, ArrayList<WordData>> {

        @Override
        protected ArrayList<WordData> doInBackground(String... urls) {

            HttpURLConnection connection = null;
            BufferedReader reader = null;
            InputStream inputStream;

            try {
                URL url = new URL(FINAL_URL);
                connection = (HttpURLConnection) url.openConnection();
                connection.setRequestProperty("X-Mashape-Key", "SEx2h4btKAmshMaOcdfuPisFqoKdp16iyDEjsnGKLV0pxu4sOJ");
                connection.setRequestProperty("Accept", "application/json");
                connection.connect();
                inputStream = connection.getInputStream();
                reader = new BufferedReader(new InputStreamReader(inputStream));
                StringBuffer buffer = new StringBuffer();
                String line = "";
                while ((line = reader.readLine()) != null) {
                    buffer.append(line);
                }

                String finalJson = buffer.toString();

                JSONObject parentObject = new JSONObject(finalJson);
                JSONObject frequencyObject = parentObject.getJSONObject("frequency");

                wordDataList = new ArrayList<>();
                //JSONArray hourlyArray = parentArray.getJSONArray(i);

                WordData wordData = new WordData();
                wordData.setZipf(frequencyObject.getString("zipf"));
                wordData.setPerMillion(frequencyObject.getString("perMillion"));
                wordData.setDiversity(frequencyObject.getString("diversity"));

                wordDataList.add(wordData);
                return wordDataList;
                //Log.v(TAG, finalJson);

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            } finally {
                if (connection != null)
                    connection.disconnect();
                try {
                    if (reader != null)
                        reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return null;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new ProgressDialog(WordSearchActivity.this);
            progressDialog.setMessage("Loading Data");
            progressDialog.setIndeterminate(false);
            progressDialog.setCancelable(false);
            progressDialog.show();
        }

        @Override
        protected void onPostExecute(final ArrayList<WordData> result) {
            super.onPostExecute(result);
            progressDialog.dismiss();

            outputTextView.setText(result.get(0).getZipf());
            partOfSpeechView.setText(result.get((0)).getPerMillion());
            partOfSpeechLabel.setText(result.get(0).getDiversity());


        }
    }
}
