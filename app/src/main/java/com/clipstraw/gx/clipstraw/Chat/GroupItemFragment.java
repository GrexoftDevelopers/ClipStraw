package com.clipstraw.gx.clipstraw.Chat;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.clipstraw.gx.clipstraw.R;

import java.util.ArrayList;

/**
 * Created by Faizzy on 28-01-2016.
 */
public class GroupItemFragment extends Fragment {
    View fragmentView;
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        fragmentView = inflater.inflate(R.layout.fragment_group_item, container, false);
        mRecyclerView = (RecyclerView) fragmentView.findViewById(R.id.recyclerview_group_item);

        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(getActivity());

        mRecyclerView.setLayoutManager(mLayoutManager);
        mAdapter = new RecyclerViewAdapter(getGroupItem(), new RecyclerViewAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {

                Toast.makeText(getActivity(),"group item clicked "+position ,Toast.LENGTH_SHORT).show();

                TextView groupName = (TextView)view.findViewById(R.id.tv_group_name);
                String group= groupName.getText().toString();
                Intent intent = new Intent(getActivity() ,IndividualChatActivitry.class);


                intent.putExtra("group", group);
                intent.putExtra("isGroup", true);
                startActivity(intent);

            }

            @Override
            public void onItemLongClick(View view, int position) {
                Toast.makeText(getActivity(), "group item long clicked " + position, Toast.LENGTH_SHORT).show();
            }
        },1);

        mRecyclerView.setAdapter(mAdapter);

        return fragmentView;
    }

    private ArrayList<ChatItem> getGroupItem() {
        ArrayList results = new ArrayList<GroupChatItem>();
        for (int index = 0; index < 24; index++) {

            GroupChatItem groupChatItem = new GroupChatItem("Friends"+index ,"11:00"+index ,null ,"Hanging out" ,null);
            results.add(index,groupChatItem);
        }
        return results;
    }
}
