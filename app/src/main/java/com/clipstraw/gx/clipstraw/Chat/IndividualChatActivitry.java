package com.clipstraw.gx.clipstraw.Chat;

import android.content.Intent;
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
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.clipstraw.gx.clipstraw.R;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Random;

/**
 * Created by Faizzy on 30-01-2016.
 */

public class IndividualChatActivitry extends AppCompatActivity {
    private Toolbar toolbar;
    private View include_indi_chat_header;
    private View include_group_chat_header;
    private ImageView  imgUserPic, imgRingOnline;
    TextView tvUserName, tvStatus,tvGroupName;
    RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private ArrayList<ChatMsgItem> allChats;
    String userName;
    private boolean isGroup;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_individual_chat);



        toolbar = (Toolbar) findViewById(R.id.toolbar);

        include_indi_chat_header = (View) findViewById(R.id.include_conversion_header);
        include_group_chat_header = (View) findViewById(R.id.include_conversion_group_header);

       // include_group_chat_header.setVisibility(View.GONE);
       // include_indi_chat_header.setVisibility(View.GONE);


        tvUserName = (TextView)findViewById(R.id.action_bar_user_name);
        tvGroupName = (TextView)findViewById(R.id.action_bar_group_name);


        allChats = getAllChat();


        recyclerView = (RecyclerView)findViewById(R.id.recycleView_chat_activity);
        recyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(getApplicationContext());

        recyclerView.setLayoutManager(mLayoutManager);
        mAdapter = new ChatActivityRecyclerViewAdapter();

        recyclerView.setAdapter(mAdapter);


        setSupportActionBar(toolbar);


        Intent intent =getIntent();

       isGroup= intent.getExtras().getBoolean("isGroup");

        if(isGroup){
           include_indi_chat_header.setVisibility(View.GONE);
            include_group_chat_header.setVisibility(View.VISIBLE);
            String groupName=   intent.getExtras().getString("group");
            tvGroupName.setText(groupName);


        }else{
            include_indi_chat_header.setVisibility(View.VISIBLE);
            include_group_chat_header.setVisibility(View.GONE);



            userName=   intent.getExtras().getString("user");
            tvUserName.setText(userName);
        }
        ActionBar actionBar = getSupportActionBar();

        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setDisplayShowTitleEnabled(false);

    }

    private ArrayList<ChatMsgItem> getAllChat(){
        ArrayList<ChatMsgItem> chatMsgItems = new ArrayList<ChatMsgItem>();
        for(int i=0; i<10; i++){
            boolean incoming = false;
            int random = new Random().nextInt(10);
            if(random > 5) incoming = true;
            chatMsgItems.add(new ChatMsgItem("content of message " + i, Calendar.getInstance().getTime().toString(), true, incoming));
        }

        return  chatMsgItems;
    } 


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.individual_menu_chat, menu);
        if(isGroup){
            MenuItem itemCall =menu.findItem(R.id.action_call);
            MenuItem itemDelete =menu.findItem(R.id.action_delete_chat);
            itemCall.setTitle("Leave group");
            itemDelete.setTitle("View and edit group info");


        }

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                // API 5+ solution
                onBackPressed();
                return true;

            case R.id.action_attach:
                Toast.makeText(getApplicationContext(), "attach", Toast.LENGTH_SHORT).show();

                return true;
            case R.id.action_invite:
                Toast.makeText(getApplicationContext(), "Invite", Toast.LENGTH_SHORT).show();

                return true;
            case R.id.action_call:
                if(isGroup){
                    Toast.makeText(getApplicationContext(), "leave group", Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(getApplicationContext(), "Call", Toast.LENGTH_SHORT).show();
                }
                return true;
            case R.id.action_delete_chat:
                if(isGroup){
                    Toast.makeText(getApplicationContext(), "View and edit group info", Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(getApplicationContext(), "delete", Toast.LENGTH_SHORT).show();
                }
                return true;
            case R.id.action_conatct_view:
                Toast.makeText(getApplicationContext(), "contact", Toast.LENGTH_SHORT).show();

                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

   class ChatActivityRecyclerViewAdapter extends RecyclerView.Adapter<ChatActivityRecyclerViewAdapter.ViewHolder> {

       private static final int MSG_INCOMING = 0;
       private static final int MSG_OUTGOING = 1;

       @Override
       public int getItemViewType(int position) {
           if (allChats.get(position).isIncomming()) return MSG_INCOMING;
           else return MSG_OUTGOING;
       }

       @Override
       public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
           View chatView = null;
           if(viewType == MSG_INCOMING){
               chatView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_individual_chat_page_in,parent,false);
           }
           else if(viewType == MSG_OUTGOING){
               chatView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_individual_chat_page_out,parent,false);
           }
           return new ViewHolder(chatView);
       }

       @Override
       public void onBindViewHolder(ViewHolder holder, int position) {
           if(allChats.get(position).isIncomming()){
               if(isGroup){

                       holder.tvSendar.setText("Sarah");
                   }

               else{
                   holder.tvSendar.setText(userName);
               }

               holder.tvTime.setText("5 hours ago");
               holder.tvMsgContent.setText(allChats.get(position).getMsgContent());
           }
           else{
               holder.tvSendar.setText("You");
               holder.tvTime.setText("5 hours ago");//allChats.get(position).getTime());
               holder.tvMsgContent.setText(allChats.get(position).getMsgContent());
               if(allChats.get(position).isHasSeen()){
                   holder.tvSeen.setText("seen it");
               }else{
                   holder.tvSeen.setText("pending");
               }

           }


       }

       @Override
       public int getItemCount() {
           return allChats.size();
       }

       public class ViewHolder extends RecyclerView.ViewHolder {



           TextView tvSendar,tvMsgContent,tvTime,tvSeen;

           public ViewHolder(View itemView) {
               super(itemView);


               tvSendar = (TextView)itemView.findViewById(R.id.txt_msg_sender);
               tvMsgContent =(TextView)itemView.findViewById(R.id.txt_msg);
               tvTime = (TextView)itemView.findViewById(R.id.txt_msg_time);
               tvSeen = (TextView)itemView.findViewById(R.id.txt_seen);


           }
       }
   }



}
