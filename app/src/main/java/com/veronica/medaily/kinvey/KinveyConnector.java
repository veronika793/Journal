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

    public void createUser(User user){

        logoutUser();

        myKinveyClient.user().create(user.getEmail(), user.getPassword(), new KinveyUserCallback() {
            @Override
            public void onSuccess(com.kinvey.java.User user) {
                Log.d("DEBUG", "success");
            }

            @Override
            public void onFailure(Throwable throwable) {
                Log.d("DEBUG", throwable.getMessage());
            }
        });
        myKinveyClient.user().put("user_id", user.getId());
        myKinveyClient.user().put("user_password", user.getPassword());
        myKinveyClient.user().put("user_pic", user.getPhotoUri());
        myKinveyClient.user().put("user_name", user.getName());

        logoutUser();
    }

    public void loginUser(User user){
        myKinveyClient.user().login(user.getEmail(), user.getPassword(),this);
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
