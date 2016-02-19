package com.clipstraw.gx.clipstraw.Chat;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.clipstraw.gx.clipstraw.R;

import java.util.ArrayList;

public class CallLogItemFragment extends Fragment {


    private View fragmentView;
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        fragmentView = inflater.inflate(R.layout.fragment_call_log_item, container, false);

        mRecyclerView = (RecyclerView) fragmentView.findViewById(R.id.call_recycler_view);

        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(getActivity());

        mRecyclerView.setLayoutManager(mLayoutManager);

        mAdapter = new RecyclerViewAdapter(getCallLogItem(), new RecyclerViewAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                if (view.getId() == R.id.img_call){

                    Intent intent = new Intent(view.getContext(), CallActivity.class);
                    startActivity(intent);
                    return;
                }
                Toast.makeText(getActivity(), "call item clicked " + position, Toast.LENGTH_SHORT).show();


            }

            @Override
            public void onItemLongClick(View view, int position) {
                Toast.makeText(getActivity(), "call item long clicked " + position, Toast.LENGTH_SHORT).show();

            }
        },2);

        mRecyclerView.setAdapter(mAdapter);

        return fragmentView;
    }


    private ArrayList<ChatItem> getCallLogItem() {
        ArrayList results = new ArrayList<CallLogItem>();
        for (int index = 0; index < 24; index++) {

            CallLogItem callLogItem = new CallLogItem("Sarah Parker", "10:0"+index,  null ,null,0,0,false);
            results.add(index,callLogItem);
        }
        return results;
    }

    }





