package com.example.p2_declutter_app.declutterStep3;

import android.content.SharedPreferences;
import android.webkit.WebViewClient;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import com.example.p2_declutter_app.R;
import android.webkit.WebView;
import android.webkit.WebSettings;

public class declutterKeep2 extends AppCompatActivity {

    private void loadVideo(WebView webView, String videoID) {
        WebSettings settings = webView.getSettings();
        settings.setJavaScriptEnabled(true);
        webView.setWebViewClient(new WebViewClient());

        String html = "<html><body style='margin:0'><iframe width='100%' height='100%' " +
                "src='https://www.youtube.com/embed/" + videoID + "' " +
                "frameborder='0' allow='accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture' allowfullscreen>" +
                "</iframe></body></html>";

        webView.loadData(html, "text/html", "utf-8");
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_declutter_keep2);

        WebView youtube1 = findViewById(R.id.youtube1);
        WebView youtube2 = findViewById(R.id.youtube2);
        WebView youtube3 = findViewById(R.id.youtube3);

        loadVideo(youtube1, "HPuEym9DQQo"); // Example ID 1
        loadVideo(youtube2, "Lpc5_1896ro"); // Example ID 2
        loadVideo(youtube3, "a-TFbunklsQ"); // Example ID 3

        ImageButton finishButton = findViewById(R.id.finish_button_keep);

        finishButton.setOnClickListener(v -> {
            SharedPreferences prefs = getSharedPreferences("app_prefs", MODE_PRIVATE);
            prefs.edit().putBoolean("declutterKeep_finished", true).apply();

            Intent intent = new Intent(declutterKeep2.this, dc_step3.class);
            startActivity(intent);
            finish(); // optional: closes this page
        });
    }
}

