package com.tomlar.va.appchat;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by huynhphihau on 3/18/18.
 */

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.MessageViewHoler> {
    ArrayList<String> messages;
    Context mContext;

    public MessageAdapter(Context mContext) {
        this.mContext = mContext;
        this.messages = new ArrayList<>();
    }

    /**
     * set data
     *
     * @param message
     */
    public void addMessage(String message) {
        this.messages.add(message);
        notifyDataSetChanged();
    }

    class MessageViewHoler extends RecyclerView.ViewHolder {
        public TextView txtMessage;
        public Context context;

        public MessageViewHoler(View itemView) {
            super(itemView);
            txtMessage = (TextView) itemView.findViewById(R.id.txtMessage);
            context = itemView.getContext();
        }
    }


    @Override
    public MessageViewHoler onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.line_message, parent, false);
        MessageViewHoler holder = new MessageAdapter.MessageViewHoler(itemView);
        return holder;
    }

    @Override
    public void onBindViewHolder(MessageViewHoler holder, int position) {
        // Check parameter
        if (position == RecyclerView.NO_POSITION) {
            return;
        }

        String message = this.messages.get(position);
        if (TextUtils.isEmpty(message)) {
            return;
        }
        holder.txtMessage.setText(message);
    }

    @Override
    public int getItemCount() {
        return messages.size();
    }
}