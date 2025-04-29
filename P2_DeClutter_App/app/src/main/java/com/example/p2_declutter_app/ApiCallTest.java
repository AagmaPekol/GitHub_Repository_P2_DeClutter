package com.example.p2_declutter_app;

import android.os.Bundle;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import android.view.View;
import androidx.core.view.WindowInsetsCompat;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import okhttp3.*;
import org.json.JSONArray;
import org.json.JSONObject;
import java.io.IOException;

public class ApiCallTest extends AppCompatActivity {

    private static final String TAG = "OpenAI";
    private static final String OPENAI_API_KEY = ""; // key here bruh (star wars reference)

    // UI elements
    private EditText editTextInput1;
    private Button btnGenerateResponse1;
    private TextView textViewResponse1;

    // Pre-defined prompt (with a placeholder)
    private static final String BASE_PROMPT = "Extract a sentimental value (ranging from 1-10 with 10 being high sentimental value) a condition value (from 1-10 with 10 being best condition) and a short sales description of the clothes: [story]";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_api_call_test);

        // Initialize UI elements
        editTextInput1 = findViewById(R.id.editTextInput1);
        btnGenerateResponse1 = findViewById(R.id.btnGenerateResponse1);
        textViewResponse1 = findViewById(R.id.textViewResponse1);

        // Set click listener for the button
        btnGenerateResponse1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userInput = editTextInput1.getText().toString().trim();
                if (!userInput.isEmpty()) {
                    // Combine base prompt with the user's input
                    String fullPrompt = BASE_PROMPT.replace("[story]", userInput);
                    // Send this full prompt to the AI
                    callOpenAI(fullPrompt);
                } else {
                    textViewResponse1.setText("Please tell me a story.");
                }
            }
        });
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
                .header("Authorization", "Bearer " + OPENAI_API_KEY) // Hardcoded API key for testing
                .post(body) // POST request with body
                .build();

        // Make the request asynchronously
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        textViewResponse1.setText("Failed to get response. Please try again.");
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
                        String text = json
                                .getJSONArray("choices") // Get the choices array
                                .getJSONObject(0) // Get the first choice (as there's usually only one)
                                .getJSONObject("message") // Get the message object
                                .getString("content"); // Extract the content (generated text)

                        // Update the UI with the generated text
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                textViewResponse1.setText(text); // Display the response
                            }
                        });
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            textViewResponse1.setText("API request failed. Please try again.");
                        }
                    });
                    Log.e(TAG, "API request failed: " + response.message());
                }
            }
        });
    }
}