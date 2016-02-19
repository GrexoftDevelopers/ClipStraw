package com.clipstraw.gx.clipstraw;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.EditText;

public class PopularActivity extends AppCompatActivity {

    public static final int MOST_POPULAR_TIMELINE = 0;
    public static final int MOST_POPULAR_EVENT = 1;
    public static final int MOST_POPULAR_FRIEND_EVENT = 2;

    private int fragmentId;

    private Fragment fragment;
    private EditText etSearch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_popular);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Bundle extras = getIntent().getExtras();
        if(extras != null){
            fragmentId = extras.getInt("fragment_id");
        }
        else{
            fragmentId = 0;
        }

        etSearch = (EditText)findViewById(R.id.et_search);

        switch (fragmentId){
            case MOST_POPULAR_TIMELINE:
                fragment = new MostPopularTimelineFragment();
                getSupportActionBar().setTitle("Most Popular Timeline");
                etSearch.setHint("Search most popular timeline events");
                break;
            case MOST_POPULAR_FRIEND_EVENT:
                fragment = new MostPopularFriendEventFragment();
                getSupportActionBar().setTitle("Most Popular Friend Event");
                etSearch.setHint("Search most popular friend events");
                break;
            case MOST_POPULAR_EVENT:
                fragment = new MostPopularEventFragment();
                getSupportActionBar().setTitle("Most Popular Event");
                etSearch.setHint("Search most popular events");
                break;
        }

        getSupportFragmentManager().beginTransaction().add(R.id.fragment_container,fragment).commit();




    }

}
