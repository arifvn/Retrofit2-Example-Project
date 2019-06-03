package com.example.parsingjsonretrofit;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    TextView tvPostResult;
    ProgressBar pbLoadingData;

    JsonPlaceholderAPI jsonPlaceholderAPI;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvPostResult = (TextView) findViewById(R.id.tv_post);
        pbLoadingData = (ProgressBar) findViewById(R.id.pb_loading_data);

        showLoading();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://jsonplaceholder.typicode.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        jsonPlaceholderAPI = retrofit.create(JsonPlaceholderAPI.class);

        getComments();

    }

    private void getPosts(){
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
                    hideLoding();
                }

            }

            @Override
            public void onFailure(Call<List<Posts>> call, Throwable t) {
                tvPostResult.setText(t.getMessage());
                hideLoding();
            }
        });
    }

    private void getComments(){
        Call<List<Comments>> call = jsonPlaceholderAPI.getComments(1);
        call.enqueue(new Callback<List<Comments>>() {
            @Override
            public void onResponse(Call<List<Comments>> call, Response<List<Comments>> response) {

                if (!response.isSuccessful()) {
                    tvPostResult.setText("Code: " + response.code());
                    return;
                }

                List<Comments> comments = response.body();
                for (Comments comment : comments) {
                    String result = "";
                    result += "postId: " + comment.getPostId() + "\n";
                    result += "ID: " + comment.getId() + "\n";
                    result += "name: " + comment.getName() + "\n";
                    result += "email: " + comment.getEmail() + "\n";
                    result += "body: " + comment.getText() + "\n\n";

                    tvPostResult.append(result);
                    hideLoding();
                }
            }

            @Override
            public void onFailure(Call<List<Comments>> call, Throwable t) {
                tvPostResult.setText(t.getMessage());
                hideLoding();
            }
        });
    }


    private void showLoading() {
        tvPostResult.setVisibility(View.INVISIBLE);
        pbLoadingData.setVisibility(View.VISIBLE);
    }

    private void hideLoding(){
        tvPostResult.setVisibility(View.VISIBLE);
        pbLoadingData.setVisibility(View.INVISIBLE);
    }

}
