package com.uandme;

import android.annotation.TargetApi;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.uandme.dbhandle.DBHandler;
import com.uandme.dbhandle.Messages;
import com.uandme.singleton.AppLocalData;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by anand-3985 on 25/11/16.
 */
public class Message_Adapter extends RecyclerView.Adapter<Message_Adapter.MessageHolder>{

    private List<Messages> messagesList;

    private String myname ;
    public class MessageHolder extends RecyclerView.ViewHolder {
        public TextView name, msg;
        public RelativeLayout relativeLayout;
        public View view;
        public MessageHolder(View view) {
            super(view);
            this.view = view;
            name = (TextView) view.findViewById(R.id.name);
            msg = (TextView) view.findViewById(R.id.msg);
            relativeLayout = (RelativeLayout)view.findViewById(R.id.lay);
            //  year = (TextView) view.findViewById(R.id.year);
         //   img = (ImageView)view.findViewById(R.id.img);
        }
    }

    public Message_Adapter(List<Messages> messagesList) {
        this.messagesList = messagesList;
    }

    @Override
    public MessageHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_content_msg, parent, false);

        myname =  AppLocalData.getInstance(parent.getContext()).getData(AppLocalData.MY_NAME);
        return new MessageHolder(itemView);
        //return null;
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    @Override
    public void onBindViewHolder(final MessageHolder holder, final int position) {

        Messages message = messagesList.get(position);

        holder.name.setText(message.getTimestamp());
        holder.msg.setText(message.getMsg());

        holder.relativeLayout.setGravity(message.getSender().equals(myname)?Gravity.RIGHT:Gravity.LEFT);

        holder.view.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                final CharSequence[] items = { "Delete" ,"Delete All"};
                final MainActivity activity =  MainActivity.getInstance();
                AlertDialog.Builder builder = new AlertDialog.Builder(holder.view.getContext());

               // builder.setTitle("Action:");
                builder.setItems(items, new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int item) {
                   DBHandler dbhandler = DBHandler.getInstance(holder.view.getContext());
                        switch (item){
                            case 0:
                               dbhandler.deleteMessages(activity.msgList.get(position));

                                activity.msgList.remove(position);
                                activity.mAdapter.notifyDataSetChanged();

                                break;
                            case 1:
                                activity.msgList.clear();
                               // activity.msgList.removeAll(dbhandler.getAllMessagess());
                                dbhandler.deleteAllMessages();

                                activity.mAdapter.notifyDataSetChanged();
                                break;
                        }



                    }

                });

                AlertDialog alert = builder.create();

                alert.show();
                //do your stuff here
return true;
            }
        });
    }

    @Override
    public int getItemCount() {
        return messagesList.size();
    }


}
