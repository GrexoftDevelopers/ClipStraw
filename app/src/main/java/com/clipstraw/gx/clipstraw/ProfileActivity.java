package com.clipstraw.gx.clipstraw;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.clipstraw.gx.clipstraw.model.session.ClipstrawSession;
import com.clipstraw.gx.clipstraw.helper.session.SessionManager;
import com.clipstraw.gx.clipstraw.settings.MainSettingsFragment;

import java.util.ArrayList;

import widgets.FooterView;

/**
 * Created by Faizzy on 18-01-2016.
 */
public class ProfileActivity extends FragmentActivity implements NavigationView.OnClickListener{

    private DrawerLayout mDrawerLayout;
    private NavigationView navigationView;
    private Button btnMyProfile, btnEditProfile, btnNotification, btnSetting, btnActivity, btnLogOut,btnTimeline;
    private LinearLayout include;
    private Fragment fragment = null;
    private int topOfFragment=-1;
    private ArrayList<Fragment>fragmentsArrayList;

    private FooterView footer;

    ClipstrawSession session;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        session = SessionManager.getInstance().getActiveSession();

        if (session == null){
            finish();
        }

        initializeFragments();

        firstFragment();

        topOfFragment=0;

        navigationView = (NavigationView) findViewById(R.id.nav_view);

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);

        View view = getLayoutInflater().inflate(R.layout.navigation_drawer, null);

        navigationView.addView(view);

        btnTimeline  = (Button)view.findViewById(R.id.btn_timeline);

        btnMyProfile = (Button) view.findViewById(R.id.btn_my_profile);

        btnEditProfile = (Button) view.findViewById(R.id.btn_edit_profile);

        btnActivity = (Button) view.findViewById(R.id.btn_activity);

        btnNotification = (Button) view.findViewById(R.id.btn_notification);

        btnSetting = (Button) view.findViewById(R.id.btn_setting);

        btnLogOut = (Button) view.findViewById(R.id.btn_logout);


        btnTimeline.setOnClickListener(this);

        btnMyProfile.setOnClickListener(this);

        btnEditProfile.setOnClickListener(this);

        btnNotification.setOnClickListener(this);

        btnSetting.setOnClickListener(this);

        btnActivity.setOnClickListener(this);

        btnLogOut.setOnClickListener(this);

        footer = (FooterView)findViewById(R.id.footer);
        footer.setFooterListener(new FooterView.FooterListener() {
            @Override
            public void onStartActivityCommand(Intent intent) {
                startActivity(intent);
            }
        });


    }
    private void initializeFragments(){
        fragmentsArrayList = new ArrayList<Fragment>();
        fragmentsArrayList.add(new TimelineFragment());
        fragmentsArrayList.add(new MyProfileFragment());
        fragmentsArrayList.add(new EditProfileFragment());
        fragmentsArrayList.add(new NotificationFragment());
        fragmentsArrayList.add(new MainSettingsFragment());
        fragmentsArrayList.add(new ActivityFragment());
    }


    @Override
    public void onClick(View v) {


        switch (v.getId()) {

            case R.id.btn_timeline:
                Toast.makeText(getApplicationContext(), "my timeline", Toast.LENGTH_SHORT).show();
                fragment = fragmentsArrayList.get(0);
                topOfFragment=0;
                replaceFragment(topOfFragment);
                mDrawerLayout.closeDrawers();
                break;

            case R.id.btn_my_profile:
                Toast.makeText(getApplicationContext(), "myProfile", Toast.LENGTH_SHORT).show();
                fragment = fragmentsArrayList.get(1);
                topOfFragment=1;
                replaceFragment(topOfFragment);
                mDrawerLayout.closeDrawers();
                break;
            case R.id.btn_edit_profile:
                Toast.makeText(getApplicationContext(), " editProfile", Toast.LENGTH_SHORT).show();
                fragment = fragmentsArrayList.get(2);
                topOfFragment=2;
                replaceFragment(topOfFragment);
                mDrawerLayout.closeDrawers();
                break;
            case R.id.btn_notification:
                Toast.makeText(getApplicationContext(), "notification", Toast.LENGTH_SHORT).show();
                fragment = fragmentsArrayList.get(3);
                topOfFragment=3;
                replaceFragment(topOfFragment);
                mDrawerLayout.closeDrawers();
                break;
            case R.id.btn_setting:
                Toast.makeText(getApplicationContext(), "setting", Toast.LENGTH_SHORT).show();
                //fragment =fragmentsArrayList.get(4);
                //topOfFragment=4;
                //replaceFragment(topOfFragment);
                mDrawerLayout.closeDrawers();
                startActivity(new Intent(getApplicationContext(),SettingsActivity.class));
                break;
            case R.id.btn_activity:
                Toast.makeText(getApplicationContext(), "activity", Toast.LENGTH_SHORT).show();
                fragment =  fragmentsArrayList.get(5);
                topOfFragment=5;
                replaceFragment(topOfFragment);
                mDrawerLayout.closeDrawers();
                break;
            case R.id.btn_logout:
                Toast.makeText(getApplicationContext(), "logout", Toast.LENGTH_SHORT).show();
                mDrawerLayout.closeDrawers();
                SessionManager.getInstance().clearSession();
                final ProgressDialog mProgressDialog = new ProgressDialog(this);
                mProgressDialog.setMessage("Logging out....");
                mProgressDialog.setIndeterminate(true);
                mProgressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                mProgressDialog.setCancelable(false);
                mProgressDialog.show();
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    public void run() {
                        mProgressDialog.dismiss();
                    }
                }, 3000);

                mProgressDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                    @Override
                    public void onDismiss(DialogInterface dialog) {
                        //String logout="LOGOUT";
                        Intent logOutIntent = new Intent();
                        //logOutIntent.putExtra("logout", logout);
                        setResult(Activity.RESULT_OK, logOutIntent);
                        finish();
                        overridePendingTransition(R.anim.fragment_roll_out, R.anim.fragment_out);
                    }
                });

                break;

        }

    }

    private void replaceFragment(int tof) {

        FragmentManager mFragmentManager = getSupportFragmentManager();
        FragmentTransaction mFragmentTransaction = mFragmentManager.beginTransaction();
        //include.setVisibility(View.VISIBLE);
        mFragmentTransaction.setCustomAnimations(R.anim.fragment_roll_out, R.anim.fragment_out).replace(R.id.fragment_container, fragmentsArrayList.get(tof)).commit();
    }
    private void firstFragment(){
        FragmentManager mFragmentManager = getSupportFragmentManager();
        FragmentTransaction mFragmentTransaction = mFragmentManager.beginTransaction();
        mFragmentTransaction.add(R.id.fragment_container, fragmentsArrayList.get(0)).commit();
    }

    @Override
    public void onBackPressed() {

        Intent returnIntent = new Intent();
        setResult(Activity.RESULT_CANCELED, returnIntent);

        if(topOfFragment>0){
            replaceFragment(0);
            if(mDrawerLayout.isDrawerOpen(GravityCompat.START)){
                mDrawerLayout.closeDrawer(Gravity.LEFT);
            }
            topOfFragment=0;
        }else{
            finish();
        }

    }


}
