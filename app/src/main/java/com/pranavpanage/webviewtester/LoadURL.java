package com.pranavpanage.webviewtester;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.JavascriptInterface;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import java.util.StringTokenizer;

/**
 * Created by Pranav on 1/17/2018.
 */

public class LoadURL extends Fragment{

    private WebView myWebView;
    private EditText enteredURL;

    /**
     * Called to have the fragment instantiate its user interface view.
     * This is optional, and non-graphical fragments can return null (which
     * is the default implementation).  This will be called between
     * {@link #onCreate(Bundle)} and {@link #onActivityCreated(Bundle)}.
     * <p>
     * <p>If you return a View from here, you will later be called in
     * {@link #onDestroyView} when the view is being released.
     *
     * @param inflater           The LayoutInflater object that can be used to inflate
     *                           any views in the fragment,
     * @param container          If non-null, this is the parent view that the fragment's
     *                           UI should be attached to.  The fragment should not add the view itself,
     *                           but this can be used to generate the LayoutParams of the view.
     * @param savedInstanceState If non-null, this fragment is being re-constructed
     *                           from a previous saved state as given here.
     * @return Return the View for the fragment's UI, or null.
     */
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.loadurl,container,false);
        myWebView = (WebView) view.findViewById(R.id.webView);

        //Enabling Javascript
        WebSettings webSettings = myWebView.getSettings();
        webSettings.setJavaScriptEnabled(true);


        myWebView.addJavascriptInterface(new WebAppInterface(getContext()), "Android");
        //Setting Debugging Mode on/off
        ToggleButton toggle = (ToggleButton) view.findViewById(R.id.toggleButton);
        toggle.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    // The toggle is enabled

                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {

                        try {
                            myWebView.setWebContentsDebuggingEnabled(true);
                            Toast.makeText(getContext(), "Debugging Mode Enabled", Toast.LENGTH_LONG).show();
                        }catch (Exception e){
                            Toast.makeText(getContext(), "Failed to Enable debugging mode. Error :" + e.toString(), Toast.LENGTH_LONG).show();
                        }

                    }
                } else {
                    // The toggle is disabled
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                        try {
                            myWebView.setWebContentsDebuggingEnabled(false);
                            Toast.makeText(getContext(), "Debugging Mode Disabled", Toast.LENGTH_LONG).show();
                        }catch (Exception e){
                            Toast.makeText(getContext(), "Failed to Enable debugging mode. Error :" + e.toString(), Toast.LENGTH_LONG).show();
                        }
                    }
                }
            }
        });

        enteredURL = (EditText) view.findViewById(R.id.EnterdURL);
        enteredURL.setSelection(enteredURL.getText().length());

        //When Load URL Button Clicked
        Button loadURLButton = (Button) view.findViewById(R.id.button);

        loadURLButton.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                //Toast.makeText(getContext(),enteredURL.getText().toString(),Toast.LENGTH_LONG);
                myWebView.setWebViewClient(new WebViewClient());

                /*
                String[] result = new WebView(getContext()).getSettings().getUserAgentString().split("\\s");
                for (int x=0; x<result.length; x++) {
                    if (result[x].contains("Chrome"))
                        Toast.makeText(getContext(), result[x], Toast.LENGTH_LONG).show();

                }

                // if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                    //Toast.makeText(getContext(),new WebView(getContext()).getSettings().getUserAgentString(),Toast.LENGTH_LONG);
                Log.i("Pranav", new WebView(getContext()).getSettings().getUserAgentString());
               // }
                //Toast.makeText(getContext(), "test", Toast.LENGTH_LONG).show();
                */
                myWebView.loadUrl(enteredURL.getText().toString());
            }
        });

        return view;
    }

    public class WebAppInterface {
        Context mContext;

        /** Instantiate the interface and set the context */
        WebAppInterface(Context c) {
            mContext = c;
        }

        /** Show a toast from the web page */
        @JavascriptInterface
        public void showToast(String toast) {
            Toast.makeText(mContext, toast, Toast.LENGTH_SHORT).show();
        }
    }

}
