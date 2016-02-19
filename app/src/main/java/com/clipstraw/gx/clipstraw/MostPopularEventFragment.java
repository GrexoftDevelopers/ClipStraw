package com.clipstraw.gx.clipstraw;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;

/**
 * Created by tahir on 2/1/2016.
 */
public class MostPopularEventFragment extends Fragment {

    private View fragmentView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        fragmentView = inflater.inflate(R.layout.layout_most_liked_event,container,false);

        ((GridView)fragmentView).setAdapter(new MostPopularEventAdapter());
        return fragmentView;
    }

    class MostPopularEventAdapter extends BaseAdapter{

        @Override
        public int getCount() {
            return 50;
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            View eventView = null;
            if(convertView != null){
               eventView = convertView;
            }
            else{
                eventView = ((LayoutInflater)getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.item_most_liked_event,null);
            }
            return eventView;
        }
    }
}
