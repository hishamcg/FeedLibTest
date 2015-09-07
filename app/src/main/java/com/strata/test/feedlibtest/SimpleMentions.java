package com.strata.test.feedlibtest;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.MultiAutoCompleteTextView;

import com.linkedin.android.spyglass.mentions.MentionSpan;
import com.linkedin.android.spyglass.suggestions.SuggestionsResult;
import com.linkedin.android.spyglass.tokenization.QueryToken;
import com.linkedin.android.spyglass.tokenization.interfaces.QueryTokenReceiver;
import com.linkedin.android.spyglass.ui.RichEditorView;
import com.strata.firstmyle_lib.utils.LibShowToast;

import java.lang.reflect.Member;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class SimpleMentions extends ActionBarActivity implements QueryTokenReceiver {

    private static final String BUCKET = "members-network";

    private RichEditorView editor;
    private Button button;
    private User.UserLoader users;
    private static final String[] COUNTRIES = new String[] {
            "Belgium", "France", "Italy", "Germany", "Spain"
    };

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.simple_mentions);
        editor = (RichEditorView) findViewById(R.id.editor);
        button = (Button) findViewById(R.id.save);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LibShowToast.setText(editor.getText().toString());
                System.out.println(editor.getText().toString());
                List<MentionSpan> mentionSpans = editor.getMentionSpans();
                User user = (User) mentionSpans.get(0).getMention();
                System.out.println(user.getId());
                System.out.println(user.getName());
            }
        });
        editor.setQueryTokenReceiver(this);
        editor.setHint("Type name");
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_dropdown_item_1line, COUNTRIES);
        MultiAutoCompleteTextView textView = (MultiAutoCompleteTextView) findViewById(R.id.edit);
        textView.setAdapter(adapter);
        textView.setTokenizer(new MultiAutoCompleteTextView.CommaTokenizer());
        users = new User.UserLoader(getResources());
    }

    @Override
    public List<String> onQueryReceived(final @NonNull QueryToken token) {
        List<String> buckets = Collections.singletonList(BUCKET);
        List<User> suggestions = users.getSuggestions(token);
        SuggestionsResult result = new SuggestionsResult(token, suggestions);
        editor.onReceiveSuggestionsResult(result, BUCKET);
        return buckets;
    }
}