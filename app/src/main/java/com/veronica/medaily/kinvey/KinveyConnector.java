package com.veronica.medaily.kinvey;

import android.content.Context;
import android.util.Log;

import com.kinvey.android.Client;
import com.kinvey.android.callback.KinveyPingCallback;
import com.kinvey.android.callback.KinveyUserCallback;
import com.veronica.medaily.dbmodels.User;


public class KinveyConnector implements KinveyUserCallback{

    public static String APP_KEY_KINVEY  = "kid_ByYhrHG0";
    public static String APP_SECRET_KINVEY = "a8f1ea64b34b4b94814dfcbde32ff594";

    private static KinveyConnector mInstance;
    private static Context mContext;

    private Client myKinveyClient;


    public static void setupContext(Context context){
        if(mContext==null){
            mContext = context;
        }
    }

    public synchronized static KinveyConnector getInstance(){
        if(mInstance==null){
            mInstance = new KinveyConnector(mContext);
        }
        return mInstance;
    }

    private KinveyConnector(Context context){
        final Client client = new Client.Builder(APP_KEY_KINVEY, APP_SECRET_KINVEY,mContext).build();
        client.ping(new KinveyPingCallback() {
            public void onFailure(Throwable t) {
                Log.d("DEBG", "Kinvey Ping Failed");
            }
            public void onSuccess(Boolean b) {
                Log.d("DEBUG", "Kinvey Ping Success");
            }
        });

        myKinveyClient = client;

    }

    public void exportData(User currentUser){

        myKinveyClient.user().create(currentUser.getEmail(), currentUser.getPassword(), new KinveyUserCallback() {
            @Override
            public void onSuccess(com.kinvey.java.User user) {
                Log.d("DEBUG", "success");
            }

            @Override
            public void onFailure(Throwable throwable) {
                Log.d("DEBUG", throwable.getMessage());
            }
        });
        myKinveyClient.user().put("user_id", currentUser.getId());
        myKinveyClient.user().put("user_password", currentUser.getPassword());
        myKinveyClient.user().put("user_pic", currentUser.getPhotoUri());
        myKinveyClient.user().put("user_name", currentUser.getName());

        logoutUser();
    }

    public void importData(User currentUser){
        myKinveyClient.user().login(currentUser.getEmail(), currentUser.getPassword(),this);
    }

    public void logoutUser(){
        myKinveyClient.user().logout().execute();
    }

    public void getUser(){

    }

    public void addNotes(){

    }

    @Override
    public void onSuccess(com.kinvey.java.User user) {
        Log.d("DEBUG", "success");
    }

    @Override
    public void onFailure(Throwable throwable) {
        Log.d("DEBUG", throwable.getMessage());
    }
}
