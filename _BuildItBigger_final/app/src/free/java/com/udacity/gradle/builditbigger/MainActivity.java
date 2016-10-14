package com.udacity.gradle.builditbigger;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;


public class MainActivity extends AppCompatActivity {
    public static final String JOKE_RECEIVER_FILTER = "JOKE_RECEIVER_FILTER";
    public boolean mTestingMode = false;
    private Button mBtnTellJoke;
    private ProgressBar mProgressBar_FetchJoke;

    /*
     *In Java we use the equals method to compare whether two String objects represent the same
     * sequence of characters. The == sign is used to check whether two objects are the same,
     * i.e. refer to the same place in memory. == sometimes may return true because a compiler
     * is smart enough to optimise references but usually it does not work as we want.
     * e.g. str1.equals(str2)
     */
    private BroadcastReceiver mBroadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if(intent.getAction().equals(JOKE_RECEIVER_FILTER)){  // see comment above regarding == sign
                JokeResponse jokeResponse = intent.getParcelableExtra(JokeResponse.EXTRA_JOKE_RESPONSE);
                String joke = jokeResponse.getJokeResponse();
                handleJokeReturned(joke);
            }
        }
    };

    private View.OnClickListener mBtnClickListener = new View.OnClickListener(){
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.btn_tell_joke:
                    mProgressBar_FetchJoke.setVisibility(View.VISIBLE);
                    executeAsyncTask();
                    break;
                default:
                    break;
            }
        }
    };

    private void handleJokeReturned(String joke){
        //Log.d(LOG_TAG,">>>Jokre Returned: " + joke);
        mProgressBar_FetchJoke.setVisibility(View.GONE);

        if(mTestingMode){
            mTestSyncCalback.onReturnJoke(joke);  //comment this out for testing
            mTestingMode = false;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mBtnTellJoke = (Button)findViewById(R.id.btn_tell_joke);
        mBtnTellJoke.setOnClickListener(mBtnClickListener);
        mProgressBar_FetchJoke = (ProgressBar) findViewById(R.id.progress_bar_fetch_joke);
    }


    //////////////////////////////////////////////////////////////////////////////////////////
    private TestSyncCallback mTestSyncCalback;


    public interface TestSyncCallback {
        void onReturnJoke(String joke);
    }

    public void setTestSyncCallback(TestSyncCallback testSyncCallback) {
        mTestSyncCalback = testSyncCallback;
        mTestingMode = true;
    }

    public void executeAsyncTask(){
        IntentFilter intentFilter = new IntentFilter(JOKE_RECEIVER_FILTER);
        LocalBroadcastManager localBroadcastManager = LocalBroadcastManager.getInstance(this);
        localBroadcastManager.registerReceiver(mBroadcastReceiver,intentFilter);

        new EndPointsAsyncTask().execute(this);

    }


}
