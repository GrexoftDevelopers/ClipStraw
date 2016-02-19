package com.clipstraw.gx.clipstraw.settings;

import android.content.Context;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.ScaleAnimation;
import android.widget.LinearLayout;

import com.clipstraw.gx.clipstraw.R;
import com.clipstraw.gx.clipstraw.SettingsFragment;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by Faizzy on 08-02-2016.
 */
public class AboutUsFragment extends SettingsFragment {
    LinearLayout thumbnailLayout;

    public static final long TOTAL_LOADING_DURATION = 300;



    public AboutUsFragment() {
        super(null,R.mipmap.ic_timeline_large_white);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.about_us_page;
    }

    @Override
    protected void init() {

        thumbnailLayout = (LinearLayout)fragmentView.findViewById(R.id.thumbnail_container);

        new ThumbnailAnimation().execute("");

    }

    @Override
    protected void save() {

    }

    class ThumbnailAnimation extends AsyncTask<String, View, String> {

        private long interval;

        private ArrayList<View> thumbnails;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            ArrayList<Integer> images = getImages();
            thumbnails = new ArrayList<View>();
            LinearLayout thumbnailRow;
            int j = 0, i = 0;
            System.out.println("images : " + images.size());
            while (i < 7) {
                LayoutInflater inflater = (LayoutInflater)getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                thumbnailRow = (LinearLayout) inflater.inflate(R.layout.thumbnail_row, thumbnailLayout, true);
                i++;
            }
            for (i = 0; i < ((ViewGroup) thumbnailLayout).getChildCount(); i++) {
                thumbnailRow = (LinearLayout) (((ViewGroup) thumbnailLayout).getChildAt(i));
                addToImageList(thumbnailRow.findViewById(R.id.thumbnail_col_1));
                addToImageList(thumbnailRow.findViewById(R.id.thumbnail_col_2));
                addToImageList(thumbnailRow.findViewById(R.id.thumbnail_col_3));
                addToImageList(thumbnailRow.findViewById(R.id.thumbnail_col_4));
            }
            interval = TOTAL_LOADING_DURATION / thumbnails.size();
            //Toast.makeText(MainActivity.this, "thumbnail size : " + thumbnails.size(), Toast.LENGTH_SHORT).show();
        }

        private void addToImageList(View img) {
            if (thumbnails != null) {
                System.out.println("view is : " + img);
                img.setVisibility(View.INVISIBLE);
                thumbnails.add(img);
            }
        }

        @Override
        protected String doInBackground(String... params) {

            while (thumbnails.size() > 0) {
                try {
                    Thread.currentThread().sleep(interval);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                int rand = new Random().nextInt(thumbnails.size());
                publishProgress(thumbnails.get(rand));
                thumbnails.remove(rand);

            }
            return null;
        }

        @Override
        protected void onProgressUpdate(View... values) {
            super.onProgressUpdate(values);
            ScaleAnimation scaleAnimation = new ScaleAnimation(0.0f, 1.0f, 0.0f, 1.0f, values[0].getMeasuredWidth() / 2, values[0].getMeasuredHeight() / 2);
            scaleAnimation.setFillAfter(true);
            scaleAnimation.setDuration(200);
            scaleAnimation.setInterpolator(new AccelerateInterpolator());
            values[0].startAnimation(scaleAnimation);
            //layerMask.setAlpha(layerMask.getAlpha() + 0.03f);
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
//            logoAnimation(false);


        }

        private ArrayList<Integer> getImages() {

            ArrayList<Integer> images = new ArrayList<Integer>();
            for (int i = 0; i < 40; i++) {
                images.add(R.drawable.fr1);
            }
            return images;
        }

    }

}
