package com.veronica.medaily.fragments;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.veronica.medaily.MainApplication;
import com.veronica.medaily.R;
import com.veronica.medaily.helpers.NotificationHandler;
import com.veronica.medaily.kinvey.KinveyConnector;

public class SynchronizeFragment extends BaseFragment implements View.OnClickListener{

    private Button mBtnExport;
    private Button mBtnImport;
    private TextView mTxtProgressRemoteData;

    private KinveyConnector connector;
    private NotificationHandler notificationHandler;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        notificationHandler = new NotificationHandler(getContext());
        connector = KinveyConnector.getInstance();
        connector.setupCurrentUser(mCurrentUser);
    }

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_synchronize,container,false);

        mBtnExport = (Button) view.findViewById(R.id.btn_export);
        mBtnImport = (Button) view.findViewById(R.id.btn_import);
        mTxtProgressRemoteData = (TextView) view.findViewById(R.id.txt_progress_remote_data);

        if(mBtnExport!=null){
            mBtnExport.setOnClickListener(this);
        }
        if(mBtnImport!=null){
            mBtnImport.setOnClickListener(this);
        }

        return view;
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager)getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.btn_import){
            if(isNetworkAvailable()){
                connector.importData();
            }else{
                notificationHandler.toastNeutralNotificationBottom("No network connection");
            }
        }else if(v.getId()==R.id.btn_export){
            if(isNetworkAvailable()){
                connector.exportData();
            }else{
                notificationHandler.toastNeutralNotificationBottom("No network connection");
            }
        }
    }
}
