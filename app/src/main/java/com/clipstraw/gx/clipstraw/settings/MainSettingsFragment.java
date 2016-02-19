package com.clipstraw.gx.clipstraw.settings;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import com.clipstraw.gx.clipstraw.R;
import com.clipstraw.gx.clipstraw.SettingsFragment;
import com.github.jlmd.animatedcircleloadingview.AnimatedCircleLoadingView;

/**
 * Created by Faizzy on 19-01-2016.
 */
public class MainSettingsFragment extends SettingsFragment implements View.OnClickListener {

    private AnimatedCircleLoadingView animatedCircleLoadingView;
    private Fragment mFragment=null;
    LinearLayout spinnerContainer;

    public MainSettingsFragment(){
        super("Settings",R.mipmap.ic_settings_large_white);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.setting_page;
    }

    @Override
    protected void init() {
        fragmentView.findViewById(R.id.tv_about_us).setOnClickListener((View.OnClickListener)getActivity());
        fragmentView.findViewById(R.id.tv_ads).setOnClickListener((View.OnClickListener) getActivity());
        fragmentView.findViewById(R.id.tv_del_account).setOnClickListener((View.OnClickListener) getActivity());
        fragmentView.findViewById(R.id.tv_network_uses).setOnClickListener((View.OnClickListener) getActivity());
        fragmentView.findViewById(R.id.tv_privacy_policy).setOnClickListener((View.OnClickListener) getActivity());
        fragmentView.findViewById(R.id.tv_refresh_history).setOnClickListener((View.OnClickListener) getActivity());
        fragmentView.findViewById(R.id.tv_terms_service).setOnClickListener((View.OnClickListener) getActivity());
        fragmentView.findViewById(R.id.tv_walkthrough).setOnClickListener((View.OnClickListener) getActivity());

        //animatedCircleLoadingView =new AnimatedCircleLoadingView(getActivity());
        //animatedCircleLoadingView=(AnimatedCircleLoadingView)getActivity().getLayoutInflater().inflate(R.layout.animate_circle,spinnerContainer,false);
        //spinnerContainer.addView(animatedCircleLoadingView);
    }

    @Override
    protected void save() {

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_refresh_history:
                AlertDialog.Builder dialogRefresh = new AlertDialog.Builder(getActivity());

                View dialogeView = getActivity().getLayoutInflater().inflate(R.layout.referesh_account_history_popup, null);

                Button btnYes = (Button) dialogeView.findViewById(R.id.btn_yes);
                Button btnNo = (Button) dialogeView.findViewById(R.id.btn_no);

                dialogRefresh.setView(dialogeView);


                final  AlertDialog dialog =dialogRefresh.create();

                dialog.setButton("cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        dialog.cancel();
                    }
                });


                btnYes.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                        spinnerContainer.setVisibility(View.VISIBLE);
                        startLoading();
                        startPercentMockThread();
//                        final ProgressDialog myProgressDialog = new ProgressDialog(getActivity());
//                        myProgressDialog.setMessage("Deleting search history....");
//                        myProgressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
//                        myProgressDialog.setIndeterminate(true);
//                        myProgressDialog.setCancelable(false);
//                        myProgressDialog.show();
//                        new Handler().postDelayed(new Runnable() {
//                            @Override
//                            public void run() {
//                                //myProgressDialog.dismiss();
//
//                            }
//                        }, 3000);



                    }
                });
                btnNo.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });

                dialog.setCancelable(false);
                dialog.show();
                break;
            case R.id.tv_ads:
                mFragment =new AdsOnFragment();
                getFragments(mFragment);
                break;
            case R.id.tv_network_uses:
                mFragment = new NetworkUsesFragment();
                getFragments(mFragment);
                break;
            case R.id.tv_del_account:
                mFragment = new DeleteAccountFragment();
                getFragments(mFragment);
                break;
            case R.id.tv_privacy_policy:
                mFragment = new PrivacyPolicyFragment();
                getFragments(mFragment);
                break;
        }

    }
    private void startLoading() {
        animatedCircleLoadingView.startDeterminate();
    }

    private void startPercentMockThread() {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(10);
                    for (int i = 0; i <= 100; i++) {
                        Thread.sleep(65);
                        changePercent(i);
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
        new Thread(runnable).start();
    }

    private void changePercent(final int percent) {
       getActivity().runOnUiThread(new Runnable() {
           @Override
           public void run() {
               if (percent == 100) {
                   animatedCircleLoadingView.stopOk();
                   Handler mHandler = new Handler();
                   mHandler.postDelayed(new Runnable() {
                       @Override
                       public void run() {
                           resetLoading();

                       }
                   }, 5000);

               }
               animatedCircleLoadingView.setPercent(percent);
           }


       });
    }

    public void resetLoading() {
       getActivity().runOnUiThread(new Runnable() {
           @Override
           public void run() {
               //animatedCircleLoadingView.resetLoading();
               animatedCircleLoadingView.setPercent(0);
               if (spinnerContainer != null) {
                   ViewGroup parent = (ViewGroup) animatedCircleLoadingView.getParent();
                   if (parent != null) {
                       parent.removeView(animatedCircleLoadingView);
                   }
               }

               spinnerContainer.setVisibility(View.GONE);
           }
       });
    }

    private void getFragments(Fragment fragment){
        FragmentManager fragmentManager =getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.setCustomAnimations(R.anim.fragment_roll_out, R.anim.fragment_out).replace(R.id.setting_container, fragment, null).commit();
    }

}
