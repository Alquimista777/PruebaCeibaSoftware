package co.com.ceiba.mobile.pruebadeingreso.view;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.List;

import co.com.ceiba.mobile.pruebadeingreso.R;
import co.com.ceiba.mobile.pruebadeingreso.view.Interface.JsonPlaceHolderApi;
import co.com.ceiba.mobile.pruebadeingreso.view.Model.Posts;
import co.com.ceiba.mobile.pruebadeingreso.view.Model.Users;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class PostActivity extends Activity {
    private RecyclerView myRecyclerView;
    private MyAdapterPost myAdapterPost;
    private MyAdapter.ViewHolderDatos v;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);

        myRecyclerView = findViewById(R.id.recyclerViewPostsResults);
        myRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        getPosts();
    }
    public void getPosts(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://jsonplaceholder.typicode.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        JsonPlaceHolderApi jsonPlaceHolderApi = retrofit.create(JsonPlaceHolderApi.class);

        Call<List<Posts>> call = jsonPlaceHolderApi.getPostByUser(1);
        call.enqueue(new Callback<List<Posts>>() {
            @Override
            public void onResponse(Call<List<Posts>> call, Response<List<Posts>> response) {
                if(!response.isSuccessful()){

                    return;
                }

                List<Posts> postList = response.body();
                myAdapterPost = new MyAdapterPost(postList);
                myRecyclerView.setAdapter( myAdapterPost);
            }

            @Override
            public void onFailure(Call<List<Posts>> call, Throwable t) {

            }
        });


    }

    @Override
    protected void onStart() {
        super.onStart();
    }


}
