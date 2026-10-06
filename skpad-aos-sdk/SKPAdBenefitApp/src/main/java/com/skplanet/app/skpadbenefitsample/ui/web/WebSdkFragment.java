package com.skplanet.app.skpadbenefitsample.ui.web;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.skplanet.app.skpadbenefitsample.Constants;
import com.skplanet.app.skpadbenefitsample.R;
import com.skplanet.skpad.benefit.core.js.SKPAdBenefitJavascriptInterface;

/**
 * Web SDK 연동 화면
 *
 * WebView에 SKPAdBenefitJavascriptInterface를 등록하여 웹 페이지의 Web SDK가 앱 SDK와 통신하도록 합니다.
 */
public class WebSdkFragment extends Fragment {

    private static final String DEFAULT_URL = Constants.WEB_SDK_TEST_URL;

    @Nullable private WebView webView;
    @Nullable private EditText etUrl;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_web_sdk, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        etUrl = view.findViewById(R.id.et_url);
        etUrl.setText(DEFAULT_URL);
        webView = view.findViewById(R.id.web_view);
        initWebView();

        Button btnLoad = view.findViewById(R.id.btn_load);
        btnLoad.setOnClickListener(v -> {
            String url = etUrl != null ? etUrl.getText().toString().trim() : "";
            if (url.isEmpty()) {
                url = DEFAULT_URL;
            }
            if (webView != null) {
                webView.loadUrl(url);
            }
        });
    }

    @SuppressLint("SetJavaScriptEnabled")
    private void initWebView() {
        WebView wv = webView;
        if (wv == null) return;
        wv.clearCache(true);
        WebSettings settings = wv.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setDomStorageEnabled(true);
        settings.setMediaPlaybackRequiresUserGesture(false);
        WebView.setWebContentsDebuggingEnabled(true);
        settings.setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);

        SKPAdBenefitJavascriptInterface jsInterface = new SKPAdBenefitJavascriptInterface(wv);
        wv.addJavascriptInterface(jsInterface, SKPAdBenefitJavascriptInterface.INTERFACE_NAME);
        wv.loadUrl(DEFAULT_URL);
    }

    @Override
    public void onDestroyView() {
        if (webView != null) {
            webView.destroy();
            webView = null;
        }
        etUrl = null;
        super.onDestroyView();
    }
}
