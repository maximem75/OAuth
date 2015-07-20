package com.oauth.maxime.oauth;

import android.net.Uri;
import android.util.Log;
import android.webkit.WebView;
import android.webkit.WebViewClient;


/**
 * Created by Maxime on 19/07/2015.
 */


public class AuthWebView extends WebViewClient {
    String oauth_token, oauth_verifier;
    MainActivity _mainActivity;

    public AuthWebView(MainActivity mainActivity)
    {
        _mainActivity = mainActivity;
    }
    @Override
    public boolean shouldOverrideUrlLoading(WebView view, String url)
    {
        if(url.startsWith(Variables.App_Callback))
        {
            Uri uri= Uri.parse(url);
            oauth_token = uri.getQueryParameter("oauth_token");
            oauth_verifier = uri.getQueryParameter("oauth_verifier");
            Log.d("oauth_token : ", oauth_token);
            Log.d("oauth_verifier : ", oauth_verifier);
            _mainActivity.startSession();
        }
        return false;
    }

    public String getOauthToken()
    {
        return oauth_token;
    }

    public String getOauthVerifier()
    {
        return oauth_verifier;
    }
}
