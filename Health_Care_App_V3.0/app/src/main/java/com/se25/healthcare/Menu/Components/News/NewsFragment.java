package com.se25.healthcare.Menu.Components.News;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.se25.healthcare.R;

public class NewsFragment extends Fragment {

    private WebView webView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_news, container, false);
        webView = view.findViewById(R.id.webViewNews);

        // Cấu hình WebView
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true); // Cho phép JavaScript
        webSettings.setDomStorageEnabled(true); // Cho phép lưu trữ dữ liệu

        // Đảm bảo WebView mở trong ứng dụng thay vì trình duyệt ngoài
        webView.setWebViewClient(new WebViewClient());

        // Tải trang tin tức
        webView.loadUrl("https://suckhoedoisong.vn/");

        return view;
    }
}
