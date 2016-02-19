package com.clipstraw.gx.clipstraw.Chat;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.clipstraw.gx.clipstraw.R;

import java.util.ArrayList;

/**
 * Created by Faizzy on 22-01-2016.
 */
public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.AbstractViewHolder> {
    private ArrayList<ChatItem> mChatItemsList;

    private int chatItemType;
    private final int INDIVIDUAL_CHAT_ITEM = 0;
    private final int GROUP_CHAT_ITEM = 1;
    private final int CALL_LOG_ITEM = 2;


    private static String LOG_TAG = "RecyclerViewAdapter";
    private OnItemClickListener mOnItemClickListener;


    public interface OnItemClickListener {



        public void onItemClick(View view, int position);

        public void onItemLongClick(View view, int position);
    }

    public RecyclerViewAdapter(ArrayList<ChatItem> mChatItemsList, OnItemClickListener onItemClickListener, int chatItemType) {
        this.mChatItemsList = mChatItemsList;
        mOnItemClickListener = onItemClickListener;
        this.chatItemType = chatItemType;
    }

    public abstract class AbstractViewHolder extends RecyclerView.ViewHolder {


        public AbstractViewHolder(View itemView) {
            super(itemView);
        }

        public abstract void bindView(int position);
    }


    //Context mContext;
    public class IndividualChatItemHolder extends AbstractViewHolder implements View.OnClickListener, View.OnLongClickListener {
        TextView tvUserName, tvTime, tvMsg;


        public IndividualChatItemHolder(View itemView) {
            super(itemView);
            tvUserName = (TextView) itemView.findViewById(R.id.tv_user);
            tvMsg = (TextView) itemView.findViewById(R.id.status);
            tvTime = (TextView) itemView.findViewById(R.id.tv_msg_time);
            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);
            Log.i(LOG_TAG, "Adding Listener");

        }

        @Override
        public void bindView(int position) {
            tvMsg.setText(((IndividualChatItem) mChatItemsList.get(position)).getMsg());
            tvTime.setText(((IndividualChatItem) mChatItemsList.get(position)).getTime());
            tvUserName.setText(((IndividualChatItem) mChatItemsList.get(position)).getName());
        }


        @Override
        public void onClick(View v) {
            if (mOnItemClickListener != null) {
                mOnItemClickListener.onItemClick(v, getAdapterPosition());
            }
        }

        @Override
        public boolean onLongClick(View v) {
            if (mOnItemClickListener != null) {
                mOnItemClickListener.onItemLongClick(v, getAdapterPosition());
                return true;
            }
            return false;

        }
    }

    public class GroupChatItemHolder extends AbstractViewHolder implements View.OnClickListener, View.OnLongClickListener {
        TextView tvGroupName, tvTime, tvMsg;


        public GroupChatItemHolder(View itemView) {
            super(itemView);
            tvGroupName = (TextView) itemView.findViewById(R.id.tv_group_name);
            tvMsg = (TextView) itemView.findViewById(R.id.tv_group_msg);
            tvTime = (TextView) itemView.findViewById(R.id.tv_time);
            itemView.setOnClickListener(this);
            Log.i(LOG_TAG, "Adding Listener");

        }

        @Override
        public void bindView(int position) {
            tvMsg.setText(((GroupChatItem) mChatItemsList.get(position)).getMsg());
            tvTime.setText(((GroupChatItem) mChatItemsList.get(position)).getTime());
            tvGroupName.setText(((GroupChatItem) mChatItemsList.get(position)).getName());
        }


        @Override
        public void onClick(View v) {
            if (mOnItemClickListener != null) {
                mOnItemClickListener.onItemClick(v, getAdapterPosition());
            }
        }

        @Override
        public boolean onLongClick(View v) {
            if (mOnItemClickListener != null) {
                mOnItemClickListener.onItemLongClick(v, getAdapterPosition());
                return true;
            }
            return false;

        }

    }

    public class CallLogItemHolder extends AbstractViewHolder implements View.OnClickListener ,View.OnLongClickListener{
        TextView tvName, tvTime;
        ImageView imgCall;


        public CallLogItemHolder(View itemView) {
            super(itemView);
            tvName = (TextView) itemView.findViewById(R.id.tv_user);
            tvTime = (TextView) itemView.findViewById(R.id.txt_date_time);

            imgCall = (ImageView) itemView.findViewById(R.id.img_call);
            imgCall.setOnClickListener(this);
            itemView.setOnClickListener(this);
            Log.i(LOG_TAG, "Adding Listener");

        }

        @Override
        public void bindView(int position) {

            tvTime.setText(((CallLogItem) mChatItemsList.get(position)).getTime());
            tvName.setText(((CallLogItem) mChatItemsList.get(position)).getName());
        }


        @Override
        public void onClick(View v) {
            if (mOnItemClickListener != null) {
                Toast.makeText(v.getContext(), "Call item holder click position " + getPosition(), Toast.LENGTH_SHORT).show();
                mOnItemClickListener.onItemClick(v, getAdapterPosition());
            }
        }
        @Override
        public boolean onLongClick(View v) {
            if(mOnItemClickListener!=null){
                mOnItemClickListener.onItemLongClick(v, getAdapterPosition());
                return true;
            }
            return false;

        }


    }


    @Override
    public AbstractViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = null;
        AbstractViewHolder viewHolder = null;
        switch (chatItemType) {
            case INDIVIDUAL_CHAT_ITEM:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_main_chat_page, parent, false);
                viewHolder = new IndividualChatItemHolder(view);
                break;

            case GROUP_CHAT_ITEM:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_main_group_page, parent, false);
                viewHolder = new GroupChatItemHolder(view);
                break;
            case CALL_LOG_ITEM:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_call, parent, false);
                viewHolder = new CallLogItemHolder(view);
        }
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(AbstractViewHolder holder, int position) {
        holder.bindView(position);
    }

    public void addItem(IndividualChatItem chatItemObj, int index) {
        mChatItemsList.add(chatItemObj);
        notifyItemInserted(index);
    }

    public void deleteItem(int index) {
        mChatItemsList.remove(index);
        notifyItemRemoved(index);
    }

    @Override
    public int getItemCount() {
        return mChatItemsList.size();
    }


}
