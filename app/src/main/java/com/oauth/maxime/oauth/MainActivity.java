package com.oauth.maxime.oauth;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import android.os.StrictMode;

import android.util.Log;

import android.webkit.WebView;
import android.webkit.WebViewClient;

import oauth.signpost.OAuthProvider;
import oauth.signpost.basic.DefaultOAuthProvider;
import oauth.signpost.commonshttp.CommonsHttpOAuthConsumer;
import oauth.signpost.exception.OAuthCommunicationException;
import oauth.signpost.exception.OAuthExpectationFailedException;
import oauth.signpost.exception.OAuthMessageSignerException;
import oauth.signpost.exception.OAuthNotAuthorizedException;



public class MainActivity extends ActionBarActivity {

    private OAuthProvider provider;
    private CommonsHttpOAuthConsumer consumer;
    WebView _webView;
    AuthWebView _authWebView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        _webView = (WebView) findViewById(R.id.webView);
        _webView.setWebViewClient(new WebViewClient());
        _webView.loadUrl("http://www.google.fr");
        authenticate();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void authenticate() {

        provider = new DefaultOAuthProvider(
                Variables.App_Host + "get_request_token",
                Variables.App_Host + "get_access_token",
                "");

        consumer = new CommonsHttpOAuthConsumer(
                Variables.App_Key,
                Variables.App_Secret);


        String uri = null;
        try {
            uri = provider.retrieveRequestToken(consumer, Variables.App_Callback);
        } catch (OAuthMessageSignerException e) {
            e.printStackTrace();
        } catch (OAuthNotAuthorizedException e) {
            e.printStackTrace();
        } catch (OAuthExpectationFailedException e) {
            e.printStackTrace();
        } catch (OAuthCommunicationException e) {
            e.printStackTrace();
        }
        Log.d("Token_authent", uri);


        if (uri != null) {
            _authWebView = new AuthWebView(this);
            _webView.setWebViewClient(_authWebView);
            _webView.loadUrl(Variables.App_Auth + uri);
        }
    }

    public void startSession()
    {

    }
}
