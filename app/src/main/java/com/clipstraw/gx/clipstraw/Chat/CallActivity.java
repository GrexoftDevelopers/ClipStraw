package com.clipstraw.gx.clipstraw.Chat;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.clipstraw.gx.clipstraw.R;

import widgets.DialDotsProgrees;

/**
 * Created by Rehan on 01-02-2016.
 */
public class CallActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.incoming_call);
        DialDotsProgrees dialDotsProgrees = (DialDotsProgrees) findViewById(R.id.dots);
        dialDotsProgrees.show();
        //dialDotsProgrees.setGrowthSpeed(10);

        View callEnd = (View) findViewById(R.id.img_call_end);
        callEnd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();

                Toast.makeText(getApplicationContext(), "call ended", Toast.LENGTH_SHORT).show();
            }
        });

        ImageView callReceive = (ImageView) findViewById(R.id.img_call_receive);
        callReceive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), CallReceive.class);
                startActivityForResult(intent, 1);
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == 1){
        if(resultCode == Activity.RESULT_CANCELED){
            finish();
        }
        }
    }
}
