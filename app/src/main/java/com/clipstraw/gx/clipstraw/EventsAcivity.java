package com.clipstraw.gx.clipstraw;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewStub;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.clipstraw.gx.clipstraw.helper.MyMenuItemStuffListener;

import widgets.IconEditText;

public class EventsAcivity extends AppCompatActivity {

    private Toolbar toolbar;
    private View vShare, vUpload, vBlog, vEdit, vDelete, vGallrey;
    private boolean hasPublished;
    private ViewStub viewStub;

    private View inflatedFooter;
    private IconEditText vFindPlace;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_events);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        viewStub = (ViewStub) findViewById(R.id.view_stub_footer);
        vFindPlace = (IconEditText) findViewById(R.id.et_find_location);

        hasPublished = getIntent().getExtras().getBoolean("is_published");
        init(hasPublished);

        setSupportActionBar(toolbar);
        assert getSupportActionBar() != null;
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);


    }

    @SuppressLint("NewApi")
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {


        getMenuInflater().inflate(R.menu.menu_events_header_gray, menu);
        MenuItem itemLike = menu.findItem(R.id.action_likes);
        MenuItem itemFav = menu.findItem(R.id.action_fav);
        View view = itemLike.getActionView();

        TextView tvLikesCounter = (TextView) view.findViewById(R.id.tv_likes_count);
        itemFav.setEnabled(hasPublished);
        view.setEnabled(hasPublished);

        if (hasPublished) {

            ImageView imageLike = (ImageView) view.findViewById(R.id.img_like);
            imageLike.setImageResource(R.mipmap.ic_like_head_small_red);
            itemFav.setIcon(R.mipmap.ic_star_small_red);
            vFindPlace.setIconId(R.mipmap.ic_location_small_red);
            tvLikesCounter.setTextColor(getResources().getColor(R.color.red));

            tvLikesCounter.setText("15");

            tvLikesCounter.setBackground(getDrawable(R.drawable.dr_likes_counter_red));
            addStuffListener(view, "Likes");
        }


        return super.onCreateOptionsMenu(menu);
    }

    private void addStuffListener(View view, String message) {
        new MyMenuItemStuffListener(view, message) {
            @Override
            public void onClick(View v) {

                if (hasPublished) {
                    switch (v.getId()) {

                        case R.id.action_likes:
                            Toast.makeText(getApplicationContext(), "Likes", Toast.LENGTH_SHORT).show();
                            break;
                        case R.id.event_blog:
                            Toast.makeText(getApplicationContext(), "Blog", Toast.LENGTH_SHORT).show();

                            break;
                        case R.id.event_edit:
                            Toast.makeText(getApplicationContext(), "editing..", Toast.LENGTH_SHORT).show();

                            break;
                        case R.id.event_delete:
                            Toast.makeText(getApplicationContext(), "Deleting", Toast.LENGTH_SHORT).show();

                            break;
                        case R.id.event_share:
                            Toast.makeText(getApplicationContext(), "Shared", Toast.LENGTH_SHORT).show();

                            break;
                        case R.id.event_upload:
                            Toast.makeText(getApplicationContext(), "Uploading", Toast.LENGTH_SHORT).show();

                            break;
                        case R.id.event_gallery:
                            Toast.makeText(getApplicationContext(), "Media", Toast.LENGTH_SHORT).show();

                            break;
                    }
                }

            }

            @Override
            public boolean onLongClick(View v) {
                return super.onLongClick(v);
            }
        };
    }

    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        switch (id) {
            case android.R.id.home:
                onBackPressed();
                return true;
            case R.id.action_fav:
                Toast.makeText(getApplicationContext(), "favourite", Toast.LENGTH_SHORT).show();

                return true;
            default:
                return super.onOptionsItemSelected(item);
        }


    }


    private void init(boolean hasPublished) {


        if (hasPublished) {

            viewStub.setLayoutResource(R.layout.event_footer_red);
            inflatedFooter = viewStub.inflate();
            findIdsAndEnabelize(hasPublished);
            addStuffListener(vBlog, "Event Blog");
            addStuffListener(vEdit, "Edit Event");
            addStuffListener(vDelete, "Delete Event");
            addStuffListener(vShare, "Share Event");
            addStuffListener(vUpload, "Upload Event");
            addStuffListener(vGallrey, "View Media");
        } else {
            viewStub.setLayoutResource(R.layout.event_footer_gray);
            inflatedFooter = viewStub.inflate();
            findIdsAndEnabelize(hasPublished);
//            vBlog = findViewById(R.id.event_blog);
//            vEdit = findViewById(R.id.event_edit);
//            vDelete = findViewById(R.id.event_delete);
//            vShare = findViewById(R.id.event_share);
//            vUpload = findViewById(R.id.event_upload);

        }


    }


    private void findIdsAndEnabelize(boolean hasPublished) {
        vBlog = findViewById(R.id.event_blog);
        vEdit = findViewById(R.id.event_edit);
        vDelete = findViewById(R.id.event_delete);
        vShare = findViewById(R.id.event_share);
        vUpload = findViewById(R.id.event_upload);
        if (hasPublished) {
            vGallrey = findViewById(R.id.event_gallery);


        }

        vBlog.setEnabled(hasPublished);
        vUpload.setEnabled(hasPublished);
        vEdit.setEnabled(hasPublished);
        vShare.setEnabled(hasPublished);
        vDelete.setEnabled(hasPublished);

    }
}
