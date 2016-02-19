package com.clipstraw.gx.clipstraw.Chat;

import android.os.Bundle;
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
import com.clipstraw.gx.clipstraw.model.user.User;
import com.clipstraw.gx.clipstraw.timeline.ClipstrawEvent;
import com.clipstraw.gx.clipstraw.timeline.Message;

import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by Rehan on 01-02-2016.
 */
public class MessageActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private ArrayList<Message> allMsg;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_message);

        toolbar = (Toolbar) findViewById(R.id.toolbar_msg);
        recyclerView = (RecyclerView)findViewById(R.id.recycleView_msg);


        recyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        mAdapter = new MessageActivityRecyclerViewAdapter();
        recyclerView.setAdapter(mAdapter);
        setSupportActionBar(toolbar);
        allMsg = getAllMsg();

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setTitle("My messages");


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.message_menu, menu);

        return super.onCreateOptionsMenu(menu);
    }

    private  ArrayList<Message> getAllMsg(){
        ArrayList<Message> messages = new ArrayList<Message>();
        for(int i=0; i<12; i++){
            messages.add(i,new Message("This is a message",new User(""+0,"Sarah "+i ,null),new ClipstrawEvent(null,Calendar.getInstance().getTime(),"Johns Party",null,null,null)));


        }
        return  messages;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {

            case android.R.id.home:
                // API 5+ solution
                onBackPressed();
                return true;

            case R.id.action_search:
                Toast.makeText(getApplicationContext(), "search user", Toast.LENGTH_SHORT).show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }

    class  MessageActivityRecyclerViewAdapter extends RecyclerView.Adapter<MessageActivityRecyclerViewAdapter.ViewHolder>{


        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

            View msgView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_my_message,parent,false);
            return new ViewHolder(msgView);
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {

            holder.tvMsg.setText(allMsg.get(position).getMsgContent().toString()+position);
            holder.tvName.setText(allMsg.get(position).getUser().getUserName().toString());
           // holder.tvDate.setText(allMsg.get(position).getEvent().getCreationDate().toString());
            holder.tvDate.setText(position + 1 + "\n" + "Jan");
            holder.tvEvent.setText(allMsg.get(position).getEvent().getTitle().toString());

        }

        @Override
        public int getItemCount() {
            return allMsg.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {

            TextView tvName, tvMsg, tvDate, tvEvent;

            public ViewHolder(View itemView) {
                super(itemView);

                tvName = (TextView)itemView.findViewById(R.id.tv_user_name);
                tvMsg = (TextView)itemView.findViewById(R.id.tv_msg);
                tvDate = (TextView)itemView.findViewById(R.id.tv_event_date);
                tvEvent = (TextView)itemView.findViewById(R.id.tv_event_name);
            }
        }
    }

}
