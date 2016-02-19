package com.clipstraw.gx.clipstraw.Chat;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

import com.clipstraw.gx.clipstraw.R;

/**
 * Created by Rehan on 01-02-2016.
 */
public class CallReceive extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.friend_voice_call);

        ImageView callEnd = (ImageView) findViewById(R.id.img_call_end);
        callEnd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent();
                setResult(Activity.RESULT_CANCELED, intent);
                finish();
            }
        });

    }
}
