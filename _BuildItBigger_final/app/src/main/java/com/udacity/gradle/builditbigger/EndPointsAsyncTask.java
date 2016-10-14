package com.udacity.gradle.builditbigger;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v4.content.LocalBroadcastManager;

import com.example.andy.myapplication.backend.myApi.MyApi;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;
import com.powernusa.andy.displayjoke.DisplayJokeActivity;

import java.io.IOException;

/**
 * Created by Andy on 10/4/2016.
 */
public class EndPointsAsyncTask extends AsyncTask<Context, Void, String> {
    private static MyApi myApiService = null;
    private Context context;

    @Override
    protected String doInBackground(Context...params){
        if(myApiService == null) {  // Only do this once
            MyApi.Builder builder = new MyApi.Builder(AndroidHttp.newCompatibleTransport(),
                    new AndroidJsonFactory(), null)
                    // options for running against local devappserver
                    // - 10.0.2.2 is localhost's IP address in Android emulator
                    // - turn off compression when running against local devappserver
                    //.setRootUrl("http://10.0.2.2:8080/_ah/api/");
                    .setRootUrl("https://jokeproject-145422.appspot.com/_ah/api/");

            myApiService = builder.build();
        }


        context = params[0].getApplicationContext();

        try {
            //return myApiService.sayHi(name).execute().getData();
            return myApiService.tellJoke().execute().getData();

        } catch (IOException e) {
            return e.getMessage();
       }


    }
    //@VisibleForTesting
    @Override
    protected void onPostExecute(String result) {
        Intent responseIntent = new Intent();
        responseIntent.setAction(MainActivity.JOKE_RECEIVER_FILTER);
        JokeResponse jokeResponse = new JokeResponse();
        jokeResponse.setJokeResponse(result);
        responseIntent.putExtra(JokeResponse.EXTRA_JOKE_RESPONSE,jokeResponse);
        LocalBroadcastManager localBroadcastManager = LocalBroadcastManager.getInstance(context);
        localBroadcastManager.sendBroadcast(responseIntent);

        //Log.d("ASYNCTASKNAME",">>>Name onPostExecute: " + result);
        //Toast.makeText(context, result, Toast.LENGTH_LONG).show();
        Intent intent = new Intent(context,DisplayJokeActivity.class);
        intent.putExtra(DisplayJokeActivity.EXTRA_JOKE_KEY,result);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }
}