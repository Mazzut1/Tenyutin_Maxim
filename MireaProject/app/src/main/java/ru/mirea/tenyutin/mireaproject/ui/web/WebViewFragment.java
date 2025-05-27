package ru.mirea.tenyutin.mireaproject.ui.web;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import ru.mirea.tenyutin.mireaproject.R;

public class WebViewFragment extends Fragment {
    private static final String DEFAULT_URL = "https://mirea.ru/";
    private WebView webView;

    @SuppressLint("SetJavaScriptEnabled")
    @Override public View onCreateView(@NonNull LayoutInflater inflater,
                                       ViewGroup container,
                                       Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_web_view, container, false);
        webView = root.findViewById(R.id.webview);

        WebSettings ws = webView.getSettings();
        ws.setJavaScriptEnabled(true);
        ws.setDomStorageEnabled(true);
        ws.setCacheMode(WebSettings.LOAD_NO_CACHE);      // <-- чтобы избежать ERR_CACHE_MISS
        ws.setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);

        webView.setWebViewClient(new WebViewClient(){
            @Override public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest req) {
                return false;
            }
        });

        webView.loadUrl(DEFAULT_URL);
        return root;
    }

    @Override public void onDestroyView() {
        super.onDestroyView();
        if (webView != null) webView.destroy();
    }
}
