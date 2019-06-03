package com.example.parsingjsonretrofit;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    TextView tvPostResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvPostResult = (TextView) findViewById(R.id.tv_post);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://jsonplaceholder.typicode.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        JsonPlaceholderAPI jsonPlaceholderAPI = retrofit.create(JsonPlaceholderAPI.class);

        Call<List<Posts>> call = jsonPlaceholderAPI.getPosts();
        call.enqueue(new Callback<List<Posts>>() {
            @Override
            public void onResponse(Call<List<Posts>> call, Response<List<Posts>> response) {

                if (!response.isSuccessful()) {
                    tvPostResult.setText("Code: " + response.code());
                    return;
                }

                List<Posts> postsList = response.body();
                for (Posts post : postsList) {
                    String result = "";
                    result += "userID: " + post.getUserId() + "\n";
                    result += "ID: " + post.getId() + "\n";
                    result += "title: " + post.getTitle() + "\n";
                    result += "body: " + post.getText() + "\n\n";


                    tvPostResult.append(result);
                }

            }

            @Override
            public void onFailure(Call<List<Posts>> call, Throwable t) {
                tvPostResult.setText(t.getMessage());
            }
        });
    }
}
