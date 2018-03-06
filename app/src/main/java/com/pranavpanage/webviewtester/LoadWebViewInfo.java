package com.pranavpanage.webviewtester;

import android.app.Fragment;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.zip.Inflater;

/**
 * Created by Pranav on 2/16/2018.
 */

public class LoadWebViewInfo extends android.support.v4.app.Fragment{

    private ListView mainListView ;
    private ArrayAdapter<String> listAdapter ;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.web_view_info, container, false);


        // Find the ListView resource.
        mainListView = (ListView) view.findViewById( R.id.myListView );

        ArrayList<String> planetList = new ArrayList<String>();



        PackageManager pm = getContext().getPackageManager();
        ApplicationInfo appInfo = null;
        String error = "";
        try {
            appInfo = pm.getApplicationInfo("com.android.webview", 0);
        } catch (PackageManager.NameNotFoundException e) {
            Log.d("Pranav", e.toString());
            error = e.toString();
            //e.printStackTrace();

        }

        String lastModified= "";
        if (error.contains("NameNotFoundException")) {
            lastModified = "Package \"com.android.webview\" was not found";
        }else{

            try {
                String appFile = appInfo.sourceDir;
                long installed = new File(appFile).lastModified();


                Date date = new Date(installed);
                DateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH-mm-ss");
                String dateFormatted = formatter.format(date);


                lastModified = "Package \"com.android.webview\" was updated last at " + dateFormatted;
                //lastModified = Long.toString(installed);
            }
            catch (Exception e){
                lastModified = "Could not load package info for  \"com.android.webview\"";
            }

        }

        //planetList.add(lastModified);
        // Create and populate a List

        String[] result = new WebView(getContext()).getSettings().getUserAgentString().split("\\s");
        String current = "";


        boolean openbracket = false; // To handle whitespaces inside bracket
        for (int x=0; x<result.length; x++) {
            if (result[x].contains("(")){
                openbracket = true;


                //remove last element from Array List to append extra info
                current=planetList.get(planetList.size()-1);
                planetList.remove(current);
                if (openbracket) {
                   current= current.concat(" "+ result[x]);
                }


            }
            else if (result[x].contains(")")){
                current = current.concat(result[x]);
                planetList.add(current);
                current="";
                openbracket = false;
            }
            else{
                if (openbracket){
                    current = current.concat(result[x]+" ");
                }
                else{
                    planetList.add(result[x]);
                }
            }


        }



        // Create ArrayAdapter using the planet list.

        listAdapter = new ArrayAdapter<String>(getContext(), R.layout.simplerow, planetList);


        //Add header
        View header = inflater.inflate(R.layout.listheader,null);
        mainListView.addHeaderView(header, null,false);

        //Add Footer
        TextView tv = new TextView(getContext());
        tv.setText(lastModified);
        mainListView.addFooterView(tv);


        // Set the ArrayAdapter as the ListView's adapter.
       mainListView.setAdapter( listAdapter );
        return view;
    }

}
