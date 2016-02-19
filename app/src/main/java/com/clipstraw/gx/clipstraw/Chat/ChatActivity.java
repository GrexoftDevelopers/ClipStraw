package com.clipstraw.gx.clipstraw.Chat;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.clipstraw.gx.clipstraw.R;
import com.clipstraw.gx.clipstraw.helper.MyMenuItemStuffListener;

import java.util.ArrayList;
import java.util.List;

public class ChatActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private ViewPager mViewPager;
    private TabLayout mTabLayout;
    private int[] tabIcons = {
            R.drawable.dr_icon_call,
            R.drawable.dr_chat_icon,
            R.drawable.dr_icon_group
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        toolbar = (Toolbar) findViewById(R.id.toolbar_layout);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mViewPager = (ViewPager) findViewById(R.id.view_pager);
        setupViewPager(mViewPager);

        mTabLayout = (TabLayout) findViewById(R.id.tab_layout);
        mTabLayout.setupWithViewPager(mViewPager);
        mTabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                mViewPager.setCurrentItem(tab.getPosition());
                switch (tab.getPosition()) {
                    case 0:
                        Toast.makeText(getApplicationContext(), "first", Toast.LENGTH_SHORT).show();
                        break;
                    case 1:
                        Toast.makeText(getApplicationContext(), "second", Toast.LENGTH_SHORT).show();
                        break;
                    case 2:
                        Toast.makeText(getApplicationContext(), "third", Toast.LENGTH_SHORT).show();
                        break;
                }

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                Toast.makeText(getApplicationContext(), "PopUp should be here", Toast.LENGTH_SHORT).show();


            }
        });

        setupTabIcons();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_chat, menu);


        final MenuItem item = menu.findItem(R.id.action_msg);
        View view = item.getActionView();
        new MyMenuItemStuffListener(view, "Massage") {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MessageActivity.class);
                startActivity(intent);

                Toast.makeText(getApplicationContext(), "msg", Toast.LENGTH_SHORT).show();
            }

            @Override
            public boolean onLongClick(View v) {
                return super.onLongClick(v);
            }
        };

        TextView tv = (TextView) view.findViewById(R.id.tv_message_count);
        tv.setText("20");


        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent intent;
        switch (item.getItemId()) {
            case android.R.id.home:
                // API 5+ solution
                onBackPressed();
                return true;

//            case R.id.action_msg:
//                Toast.makeText(getApplicationContext(), "massage", Toast.LENGTH_SHORT).show();
//
//                return true;

            case R.id.action_search:
                Toast.makeText(getApplicationContext(), "search", Toast.LENGTH_SHORT).show();
                intent = new Intent(getApplicationContext(), SearchActivity.class);
                startActivity(intent);
                return true;
            case R.id.action_follower:
                Toast.makeText(getApplicationContext(), "follow", Toast.LENGTH_SHORT).show();

                Toast.makeText(getApplicationContext(), "search", Toast.LENGTH_SHORT).show();
                intent = new Intent(getApplicationContext(), FreindsAndFollwer.class);
                startActivity(intent);
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }

    }

    private void setupViewPager(ViewPager viewPager) {
        FragmentTabAdapter adapter = new FragmentTabAdapter(getSupportFragmentManager());
        adapter.addFrag(new CallLogItemFragment());
        adapter.addFrag(new ChatItemFragment());
        adapter.addFrag(new GroupItemFragment());
        viewPager.setAdapter(adapter);

    }

    private void setupTabIcons() {
        mTabLayout.getTabAt(0).setIcon(tabIcons[0]);
        mTabLayout.getTabAt(1).setIcon(tabIcons[1]);
        mTabLayout.getTabAt(2).setIcon(tabIcons[2]);
    }


    class FragmentTabAdapter extends FragmentPagerAdapter {

        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public FragmentTabAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFrag(Fragment fragment) {
            mFragmentList.add(fragment);
            //mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {

            //return mFragmentTitleList.get(position);
            return null;
        }
    }




}
