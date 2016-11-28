package com.uandme;

import android.app.NotificationManager;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.uandme.dbhandle.DBHandler;
import com.uandme.dbhandle.Messages;
import com.uandme.retrofit.ApiClient;
import com.uandme.retrofit.ApiInterface;
import com.uandme.retrofit.Response_send;
import com.uandme.singleton.AppLocalData;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{


    public ImageView sendimg =null;
    EditText msgtxt = null;

    public List<Messages> msgList = new ArrayList<>();
    public RecyclerView recyclerView;
    public Message_Adapter mAdapter;

    public NotificationManager manager;

    private static MainActivity mainActivity;

    private AppLocalData appLocalData;
    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);

        msgtxt =(EditText) findViewById(R.id.typeTxt);

        sendimg = (ImageView) findViewById(R.id.sendbtn);

        sendimg.setOnClickListener(this);

        mAdapter = new Message_Adapter(msgList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);

        manager = (NotificationManager) getSystemService(getApplicationContext().NOTIFICATION_SERVICE);

        appLocalData = AppLocalData.getInstance(getApplicationContext());

        prepareMessageData();

        //registerForContextMenu(recyclerView);


        mainActivity = this;

    }

    private void prepareMessageData() {

        DBHandler dbHandler = DBHandler.getInstance(getApplicationContext());

         for(Messages messages : dbHandler.getAllMessagess()){
             msgList.add(messages);
         }

            mAdapter.notifyDataSetChanged();
            recyclerView.smoothScrollToPosition(msgList.size());

    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d("Resume" ,"Im Back");

        AppLocalData.getInstance(getApplicationContext()).alive = true;

        manager.cancel(0);
        recyclerView.smoothScrollToPosition(msgList.size());
    }

    @Override
    protected void onPause() {
        super.onPause();
        AppLocalData.getInstance(getApplicationContext()).alive = false;
        Log.d("Pasing" ,"Not Dead");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        AppLocalData.getInstance(getApplicationContext()).alive = false;
        Log.d("Shuting Down" ,"Bye");
    }

    public static MainActivity getInstance(){
        return mainActivity;
    }

    @Override
    public void onClick(View v) {
        Log.d("clicked","###################### SEND ###################");

        if(msgtxt.getText().toString().length() > 0) {


            Date date = new Date();
            final long id = date.getTime();
            final String time = date.getHours() + ":" + date.getMinutes();
            final String msg = msgtxt.getText().toString();
            ApiInterface apiService =
                    ApiClient.getClient().create(ApiInterface.class);
            Call<Response_send> call = apiService.send(
                    id,
                    time,
                    appLocalData.getData(AppLocalData.MY_NAME),
                    msg,
                    appLocalData.getData(AppLocalData.FRD_MAILID)
            );
            msgtxt.setText("");
            call.enqueue(new Callback<Response_send>() {
                @Override
                public void onResponse(Call<Response_send> call, retrofit2.Response<Response_send> response) {
                    Messages newMessage = new Messages(
                            id + ""
                            , time
                            , appLocalData.getData(AppLocalData.MY_NAME)
                            , msg
                            , appLocalData.getData(AppLocalData.FRD_MAILID));

                    DBHandler.getInstance(getApplicationContext()).
                            addMessages(newMessage);


                    msgList.add(newMessage);

                    mAdapter.notifyDataSetChanged();
                    recyclerView.smoothScrollToPosition(msgList.size());

                    Log.d("Send :: ", newMessage.toString());
                    Log.d("result :: ", response.body().getCode());
                }

                @Override
                public void onFailure(Call<Response_send> call, Throwable t) {
                    Log.d("Failed", "failed");
                }
            });
        }
}

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);

        if (v.getId()==R.id.recycler_view) {
            MenuInflater inflater = getMenuInflater();
            inflater.inflate(R.menu.menu_list, menu);
        }
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        switch(item.getItemId()) {

            case R.id.delete:
                // remove stuff here
                return true;
            default:
                return super.onContextItemSelected(item);
        }
    }
}
