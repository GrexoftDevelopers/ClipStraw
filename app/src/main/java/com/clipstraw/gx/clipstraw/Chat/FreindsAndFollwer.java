package com.clipstraw.gx.clipstraw.Chat;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.clipstraw.gx.clipstraw.R;
import com.clipstraw.gx.clipstraw.helper.MyMenuItemStuffListener;

public class FreindsAndFollwer extends AppCompatActivity {


    private SectionsPagerAdapter mSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_freinds_and_follwer);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();

        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);

        actionBar.setTitle("Clipstraw");
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setTabGravity(TabLayout.GRAVITY_CENTER);
        tabLayout.setupWithViewPager(mViewPager);


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_freinds_and_follwer, menu);
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
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        switch (id) {
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
                Intent intent = new Intent(getApplicationContext(), SearchActivity.class);
                startActivity(intent);
                return true;


            default:
                return super.onOptionsItemSelected(item);
        }
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {

        private  static final int SECTION_FRIENDS=1;
        private  static  final  int SECTION_ACTIVITES=2;

        int sectionNumber;
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";

        public PlaceholderFragment() {
        }

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_freinds_and_follwer, container, false);
            RecyclerView recyclerView = (RecyclerView) rootView.findViewById(R.id.section_label);

            sectionNumber = getArguments().getInt(ARG_SECTION_NUMBER);
            Toast.makeText(getActivity(),sectionNumber+"",Toast.LENGTH_SHORT).show();
            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
            recyclerView.setLayoutManager(mLayoutManager);
            RecyclerView.Adapter mAdapter = null;

            mAdapter = new FriendsAndFollwerRecyclerViewAdapter(sectionNumber) ;


            recyclerView.setAdapter(mAdapter);

            return rootView;
        }
    }

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            return PlaceholderFragment.newInstance(position + 1);
        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return 2;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "Friends & Followers";
                case 1:
                    return "My Activity";
            }
            return null;
        }
    }



    static class FriendsAndFollwerRecyclerViewAdapter extends RecyclerView.Adapter<FriendsAndFollwerRecyclerViewAdapter.ViewHolder> {

        int viewType;
        int i;


        public FriendsAndFollwerRecyclerViewAdapter(int type) {
            viewType = type;
        }

        @Override
        public int getItemViewType(int position) {

            i=position;
            return viewType;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View chatView = null;
            if (viewType == PlaceholderFragment.SECTION_FRIENDS) {

                    chatView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_activity_friend, parent, false);


                System.out.println("section friends" + viewType);
            } else {
                if (i/2==0){
                    chatView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_activity_event, parent, false);
                }else if(i==7 || i==5){
                    chatView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_activity_friend, parent, false);
                }
                else{
                    chatView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_activity_like, parent, false);
                }


                System.out.println("section activity" + viewType);
            }
            return new ViewHolder(chatView);
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {


        }

        @Override
        public int getItemCount() {
            return 10;
        }

        public class ViewHolder extends RecyclerView.ViewHolder {


            TextView tvSendar, tvMsgContent, tvTime, tvSeen;

            public ViewHolder(View itemView) {
                super(itemView);


//                tvSendar = (TextView)itemView.findViewById(R.id.txt_msg_sender);
//                tvMsgContent =(TextView)itemView.findViewById(R.id.txt_msg);
//                tvTime = (TextView)itemView.findViewById(R.id.txt_msg_time);
//                tvSeen = (TextView)itemView.findViewById(R.id.txt_seen);


            }
        }
    }
}
