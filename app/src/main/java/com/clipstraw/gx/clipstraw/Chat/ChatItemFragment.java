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
 * Created by Faizzy on 21-01-2016.
 */
public class ChatItemFragment extends Fragment {
    View fragmentView;
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private static String LOG_TAG = "RecyclerViewActivity";

    @Nullable
    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        mRecyclerView = (RecyclerView) inflater.inflate(R.layout.chat_fragment, container, false);
        // mRecyclerView = (RecyclerView) fragmentView.findViewById(R.id.recycleView);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(getActivity());

        mRecyclerView.setLayoutManager(mLayoutManager);





        mAdapter = new RecyclerViewAdapter(getChat(), new RecyclerViewAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                TextView userName = (TextView)view.findViewById(R.id.tv_user);
                String user= userName.getText().toString();

                Toast.makeText(getActivity(),"chat item click positon"+position,Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getActivity() ,IndividualChatActivitry.class);

                intent.putExtra("user",user);
                startActivity(intent);
            }

            @Override
            public void onItemLongClick(View view, int position) {
                Toast.makeText(getActivity(), "chat item clicked for long "+position, Toast.LENGTH_SHORT).show();
            }
        }, 0);
        mRecyclerView.setAdapter(mAdapter);

        return mRecyclerView;
    }

    private ArrayList<ChatItem> getChat() {
        ArrayList results = new ArrayList<IndividualChatItem>();
        for (int index = 0; index < 20; index++) {
            IndividualChatItem individualChatItem = new IndividualChatItem(" Sarah " + index, "12:00" + index, " " , "Hello"+index ,"10",0,false);

            results.add(index, individualChatItem);
        }
        return results;
    }


}
