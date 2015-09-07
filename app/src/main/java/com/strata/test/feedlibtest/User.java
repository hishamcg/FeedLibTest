package com.strata.test.feedlibtest;

import android.content.res.Resources;
import android.os.AsyncTask;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.util.Log;

import com.linkedin.android.spyglass.mentions.Mentionable;
import com.linkedin.android.spyglass.suggestions.interfaces.Suggestible;
import com.linkedin.android.spyglass.tokenization.QueryToken;

import org.json.JSONArray;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringWriter;
import java.io.Writer;
import java.lang.ref.WeakReference;
import java.lang.reflect.Member;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by nagashree on 2/9/15.
 */
public class User implements Mentionable {
    String name;

    public User(String name) {
        this.name = name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @NonNull
    @Override
    public String getTextForDisplayMode(MentionDisplayMode mentionDisplayMode) {
         switch (mentionDisplayMode) {
            case FULL:
                return name;
            case PARTIAL:
            case NONE:
            default:
                return "";
        }
    }

    @Override
    public MentionDeleteStyle getDeleteStyle() {
        return MentionDeleteStyle.PARTIAL_NAME_DELETE;
    }

    @Override
    public int getId() {
        return name.hashCode();
    }

    @Override
    public String getPrimaryText() {
        return name;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
    }

    public User(Parcel in) {
        name = in.readString();
    }

    public static final Parcelable.Creator<User> CREATOR
            = new Parcelable.Creator<User>() {
        public User createFromParcel(Parcel in) {
            return new User(in);
        }

        public User[] newArray(int size) {
            return new User[size];
        }
    };

    public static class UserLoader {
        protected User[] mData;

        private static final String TAG = UserLoader.class.getSimpleName();

        public UserLoader(Resources res) {
//            super(res, R.raw.users);
            new LoadJSONArray(res, R.raw.users).execute();
        }

        public List<User> getSuggestions(QueryToken queryToken) {
            String prefix = queryToken.getKeywords().toLowerCase();
            List<User> suggestions = new ArrayList<>();
            if (mData != null) {
                for (User suggestion : mData) {
                    String name = suggestion.getPrimaryText().toLowerCase();
                    if (name.startsWith(prefix)) {
                        suggestions.add(suggestion);
                    }
                }
            }
            return suggestions;
        }

        public User[] loadData(JSONArray arr){

            User[] data = new User[arr.length()];
            try {
                for (int i = 0; i < arr.length(); i++) {
                    data[i] = new User(arr.getString(i));
                }
            } catch (Exception e) {
                Log.e(TAG, "Unhandled exception while parsing city JSONArray", e);
            }
            return data;
        }

        private class LoadJSONArray extends AsyncTask<Void, Void, JSONArray> {

            private final WeakReference<Resources> mRes;
            private final int mResId;

            public LoadJSONArray(Resources res, int resId) {
                mRes = new WeakReference<>(res);
                mResId = resId;
            }

            @Override
            protected JSONArray doInBackground(Void... params) {
                InputStream fileReader = mRes.get().openRawResource(mResId);
                Writer writer = new StringWriter();
                JSONArray arr = null;
                try {
                    BufferedReader reader = new BufferedReader(new InputStreamReader(fileReader, "UTF-8"));
                    String line = reader.readLine();
                    while (line != null) {
                        writer.write(line);
                        line = reader.readLine();
                    }
                    String jsonString = writer.toString();
                    arr = new JSONArray(jsonString);
                } catch (Exception e) {
                    Log.e(TAG, "Unhandled exception while reading JSON", e);
                } finally {
                    try {
                        fileReader.close();
                    } catch (Exception e) {
                        Log.e(TAG, "Unhandled exception while closing JSON file", e);
                    }
                }
                return arr;
            }

            @Override
            protected void onPostExecute(JSONArray arr) {
                super.onPostExecute(arr);
                mData = loadData(arr);
            }
        }
    }
}

