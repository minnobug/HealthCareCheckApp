package com.se25.healthcare.Models.APIs;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.widget.EditText;
import android.widget.TextView;

import androidx.fragment.app.FragmentActivity;

import com.se25.healthcare.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Objects;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public abstract class NutritionixAPI {
    private final static String appId = "08b6718c";
    private final static String appKey = "0b803ada718756ec72e1905f6cf209ac";
    private final static String foodUrl = "https://trackapi.nutritionix.com/v2/natural/nutrients";
    private final static String exerciseUrl = "https://trackapi.nutritionix.com/v2/natural/exercise";
    @SuppressLint("SetTextI18n")
    public static void requestFood(FragmentActivity main, TextView result_place, EditText query) {
        AsyncTask.execute(() -> {
            OkHttpClient client = new OkHttpClient();
            MediaType mediaType = MediaType.parse("application/x-www-form-urlencoded");
            RequestBody body = RequestBody.create(mediaType, "query=" + query.getText().toString() + "&timezone=US/Eastern");

            Response response = null;
            try {
                response = client.newCall(createRequest(body, foodUrl)).execute();
                JSONArray data = new JSONObject(Objects.requireNonNull(response.body()).string()).getJSONArray("foods");

                StringBuilder temp = new StringBuilder();
                float sum = 0;
                for (int i = 0; i < data.length(); i++) {
                    JSONObject object = data.getJSONObject(i);
                    String name = object.getString("food_name");
                    float weight = Float.parseFloat(object.getString("serving_weight_grams"));
                    float calo = Float.parseFloat(object.getString("nf_calories"));
                    sum += calo;
                    temp.append(weight).append(" gram ").append(name).append("\n");
                }
                float finalSum = sum;
                main.runOnUiThread((Runnable)()->{
                    query.setText(temp.toString());
                    result_place.setText(String.valueOf(finalSum));
                });

            } catch (IOException | JSONException e) {
                main.runOnUiThread((Runnable)()->{
                    result_place.setText(R.string.toast_fail);
                });
            }
        });
    }

    @SuppressLint("SetTextI18n")
    public static void requestExercise(FragmentActivity main, TextView result_place, EditText query, float weight) {
        AsyncTask.execute(() -> {
            OkHttpClient client = new OkHttpClient();
            MediaType mediaType = MediaType.parse("application/x-www-form-urlencoded");
            RequestBody body = RequestBody.create(mediaType, "query=" + query.getText().toString());

            Response response = null;
            try {
                response = client.newCall(createRequest(body, exerciseUrl)).execute();
                String str_data = Objects.requireNonNull(response.body()).string();
                JSONArray data = new JSONObject(str_data).getJSONArray("exercises");

                StringBuilder temp = new StringBuilder();
                float sum = 0;
                for (int i = 0; i < data.length(); i++) {
                    JSONObject object = data.getJSONObject(i);
                    String name = object.getString("user_input");
                    float time = Float.parseFloat(object.getString("duration_min"))/60;
                    float met = Float.parseFloat(object.getString("met"));
                    sum += met*weight*time;
                    temp.append(time).append(" hour ").append(name).append("\n");
                }
                float finalSum = sum;
                main.runOnUiThread((Runnable)()->{
                    query.setText(temp.toString());
                    result_place.setText(String.valueOf(finalSum));
                });

            } catch (IOException | JSONException e) {
                main.runOnUiThread((Runnable)()->{
                    result_place.setText(R.string.toast_fail);
                });
            }
        });
    }


    private static Request createRequest(RequestBody body, String url) {
        return new Request.Builder()
                .url(url)
                .method("POST", body)
                .addHeader("x-app-id", appId)
                .addHeader("x-app-key", appKey)
                .addHeader("content", "application/json")
                .addHeader("Content-Type", "application/x-www-form-urlencoded")
                .build();
    }

}
