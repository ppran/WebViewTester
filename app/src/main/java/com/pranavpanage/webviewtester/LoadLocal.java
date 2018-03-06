package com.pranavpanage.webviewtester;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.JavascriptInterface;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Created by Pranav on 1/17/2018.
 */

public class LoadLocal extends Fragment {


    WebView myWebView;
    EditText enteredURL;
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

        View view= inflater.inflate(R.layout.local,container,false);
        myWebView = (WebView) view.findViewById(R.id.webView2);

        //Enabling Javascript
        WebSettings webSettings = myWebView.getSettings();
        webSettings.setJavaScriptEnabled(true);


        myWebView.addJavascriptInterface(new WebAppInterface(getContext()), "Android");
        myWebView.addJavascriptInterface(new WebViewJavaScriptInterface(getContext()), "app");

        enteredURL = (EditText) view.findViewById(R.id.enterHTML);

        //When Load URL Button Clicked
        Button loadURLButton = (Button) view.findViewById(R.id.loadHTMLbutton);

        loadURLButton.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {


                //Toast.makeText(getContext(),enteredURL.getText().toString(),Toast.LENGTH_LONG);
                //myWebView.setWebViewClient(new WebViewClient());
                Toast.makeText(getContext(),myWebView.getSettings().getUserAgentString(),Toast.LENGTH_LONG);
                myWebView.loadData(enteredURL.getText().toString(),"text/html", "UTF-8");
            }
        });


        //When Load URL Button Clicked
        Button loadJavascript = (Button) view.findViewById(R.id.loadJavascript);

        loadJavascript.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {


                myWebView.loadUrl("file:///android_asset/web.html");
                //Toast.makeText(getContext(),enteredURL.getText().toString(),Toast.LENGTH_LONG);
                //myWebView.setWebViewClient(new WebViewClient());
                //Toast.makeText(getContext(),myWebView.getSettings().getUserAgentString(),Toast.LENGTH_LONG);
               // myWebView.loadData(enteredURL.getText().toString(),"text/html", "UTF-8");
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
            Toast.makeText(mContext, "Called from Javascript: " + toast, Toast.LENGTH_SHORT).show();
        }
    }

    /*
 * JavaScript Interface. Web code can access methods in here
 * (as long as they have the @JavascriptInterface annotation)
 */
    public class WebViewJavaScriptInterface{

        private Context context;

        /*
         * Need a reference to the context in order to sent a post message
         */
        public WebViewJavaScriptInterface(Context context){
            this.context = context;
        }

        /*
         * This method can be called from Android. @JavascriptInterface
         * required after SDK version 17.
         */
        @JavascriptInterface
        public void makeToast(String message, boolean lengthLong){
            Toast.makeText(context, "Called from Javascript: "+ message, (lengthLong ? Toast.LENGTH_LONG : Toast.LENGTH_SHORT)).show();
        }
    }

}

