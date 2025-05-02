package com.example.p2_declutter_app.declutterStep2;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.example.p2_declutter_app.profile.Profile_page_main;
import com.example.p2_declutter_app.R;
import com.example.p2_declutter_app.WardrobePage;
import com.example.p2_declutter_app.mainMenuPage;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;

import okhttp3.*;

public class Declutter_ClothingDescription extends AppCompatActivity {

    private Bundle bundle;
    private TextView descriptionText;
    private ImageView photoView;

    private static final String TAG = "OpenAI";
    private static final String OPENAI_API_KEY = "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"; // key here bruh (star wars reference)
    private static final String BASE_PROMPT = "Extract a sentimental value " +
            "(ranging from 1-10 with 10 being high sentimental value) a condition" +
            " value (from 1-10 with 10 being best condition) and a short sales" +
            " description of the clothes: [story]";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_declutter_clothing_description);

        Button continueBtn = findViewById(R.id.continueBtn2);
        descriptionText = findViewById(R.id.descriptionText);
        photoView = findViewById(R.id.photoView);

        bundle = getIntent().getExtras();
        setImage();

        continueBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userDescription = descriptionText.getText().toString().trim();

                if (!userDescription.isEmpty()) {

                    if (userDescription.length() < 20){
                        Toast.makeText(Declutter_ClothingDescription.this, "Please write some more", Toast.LENGTH_SHORT).show();
                    } else {
                        // Combine base prompt with the user's input
                        String fullPrompt = BASE_PROMPT.replace("[story]", userDescription);

                        // sends a combined prompt with the userDescription & BASE_PROMPT to the AI
                        callOpenAI(fullPrompt);
                    }
                } else {
                    Toast.makeText(Declutter_ClothingDescription.this, "Please write something", Toast.LENGTH_SHORT).show();
                }
            }
        });
        //      The five buttons for the top/bottom nav
        ImageButton menuBtn = findViewById(R.id.menuBtn);
        menuBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent = new Intent(Declutter_ClothingDescription.this, mainMenuPage.class);
                startActivity(intent);
            }
        });
        ImageButton wardrobeBtn = findViewById(R.id.wardrobeBtn);
        wardrobeBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent = new Intent(Declutter_ClothingDescription.this, WardrobePage.class);
                startActivity(intent);
            }
        });
        ImageButton profileBtn = findViewById(R.id.profileBtn);
        profileBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent = new Intent(Declutter_ClothingDescription.this, Profile_page_main.class);
                startActivity(intent);
            }
        });
        ImageButton backBtn = findViewById(R.id.backBtn);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    private void setImage() {
        if (bundle != null) {
            String currentPhotoPath = bundle.getString("currentPhotoPath");
            File imgFile = new File(currentPhotoPath);

            if (imgFile.exists()) {
                Glide.with(this)
                        .load(imgFile)
                        .apply(new RequestOptions().override(600, 600))
                        .into(photoView);
            } else {
                Toast.makeText(this, "Image file not found", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void callOpenAI(final String prompt) {
        OkHttpClient client = new OkHttpClient();

        MediaType JSON = MediaType.get("application/json; charset=utf-8");

        JSONObject requestBodyJson = new JSONObject();
        try {
            requestBodyJson.put("model", "gpt-4.1");  // Use GPT-4 model
            JSONArray messages = new JSONArray();
            JSONObject message = new JSONObject();
            message.put("role", "user");
            message.put("content", prompt); // Use the user's input as the prompt
            messages.put(message);
            requestBodyJson.put("messages", messages);
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Create the request body with the generated JSON
        RequestBody body = RequestBody.create(requestBodyJson.toString(), JSON);

        // Make the HTTP request
        Request request = new Request.Builder()
                .url("https://api.openai.com/v1/chat/completions") // OpenAI endpoint
                .header("Authorization", "Bearer " + OPENAI_API_KEY)
                .post(body) // POST request with body
                .build();

        // Make the request asynchronously
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(Declutter_ClothingDescription.this,
                                "Failed to get response. Please try again.", Toast.LENGTH_SHORT).show();
                    }
                });
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    final String responseData = response.body().string(); // Get response as string
                    Log.d(TAG, "Response: " + responseData); // Log the raw response

                    // Parse the JSON to extract the generated text
                    try {
                        JSONObject json = new JSONObject(responseData);
                        String text_AI = json
                                .getJSONArray("choices") // Get the choices array
                                .getJSONObject(0) // Get the first choice (as there's usually only one)
                                .getJSONObject("message") // Get the message object
                                .getString("content"); // Extract the content (generated text)

                        //Sends the generated text to the next activity
                        runOnUiThread(() -> {
                            bundle.putString("text_AI", text_AI);

                            Intent intent = new Intent(Declutter_ClothingDescription.this, Declutter_KeepDonateSell.class);
                            intent.putExtras(bundle);
                            startActivity(intent);
                        });
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(Declutter_ClothingDescription.this, "API request failed. Please try again.", Toast.LENGTH_SHORT).show();
                        }
                    });
                    Log.e(TAG, "API request failed: " + response.message());
                }
            }
        });
    }
}