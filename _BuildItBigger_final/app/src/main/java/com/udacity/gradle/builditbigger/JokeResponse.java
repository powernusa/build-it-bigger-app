package com.udacity.gradle.builditbigger;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Andy on 10/14/2016.
 */

public class JokeResponse implements Parcelable {
    public static final String EXTRA_JOKE_RESPONSE = "EXTRA_JOKE_RESPONSE";
    private String jokeResponse;
    public JokeResponse(){}

    protected JokeResponse(Parcel in) {
        jokeResponse = in.readString();
    }

    public static final Creator<JokeResponse> CREATOR = new Creator<JokeResponse>() {
        @Override
        public JokeResponse createFromParcel(Parcel in) {
            return new JokeResponse(in);
        }

        @Override
        public JokeResponse[] newArray(int size) {
            return new JokeResponse[size];
        }
    };

    public String getJokeResponse() {
        return jokeResponse;
    }

    public void setJokeResponse(String jokeResponse) {
        this.jokeResponse = jokeResponse;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(jokeResponse);
    }
}
