package com.example.retrofit_api;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.example.retrofit_api.data.model.model.Item;
import com.example.retrofit_api.data.model.model.SOAnswersResponse;
import com.example.retrofit_api.data.model.remote.SOService;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Single;
import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    private AnswersAdapter mAdapter;
    private RecyclerView mRecyclerView;
    private SOService mService;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mService = ApiUtils.getSOService();
        mRecyclerView = (RecyclerView) findViewById(R.id.recycleView);
        mAdapter = new AnswersAdapter(this, new ArrayList<Item>(0), new AnswersAdapter.PostItemListener() {

            @Override
            public void onPostClick(long id) {
                Toast.makeText(MainActivity.this, "Post id is" + id, Toast.LENGTH_SHORT).show();
            }
        });

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setHasFixedSize(true);
        RecyclerView.ItemDecoration itemDecoration = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        mRecyclerView.addItemDecoration(itemDecoration);

        loadAnswers();
    }

//    public void loadAnswers() {
//        mService.getAnswers().enqueue(new Callback<SOAnswersResponse>() {
//            @Override
//            public void onResponse(Call<SOAnswersResponse> call, Response<SOAnswersResponse> response) {
//                if(response.isSuccessful()){
//                    mAdapter.updateAnswers(response.body().getItems());
//                    Log.d("MainActivity","post loaded from API");
//                }
//                else {
//                    int statusCode = response.code();
//                }
//            }
//
//            @Override
//            public void onFailure(Call<SOAnswersResponse> call, Throwable t) {
//                Log.d("MainActivity", "error loading from API");
//            }
//        });
//    }
//    public void loadAnswers(){
//        Single<Response<List<SOAnswersResponse>>> testObservable = mService.getAnswers();
//        testObservable.subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new SingleObserver<Response<List<SOAnswersResponse>>>() {
//                    @Override
//                    public void onSubscribe(Disposable d) {
//                    }
//
//                    @Override
//                    public void onSuccess(Response<List<SOAnswersResponse>> listResponse) {
//                        if(listResponse.isSuccessful()){
//                            Log.d("MainActivity","post loaded from API");
//                        }
//                    }
//
//                    @Override
//                    public void onError(Throwable e) {
//                        Log.d("MainActivity", "error loading from API");
//                    }
//                });
//
//    }
        public void loadAnswers(){
        Single<Response<SOAnswersResponse>> testObservable = mService.getAnswers();
        testObservable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<Response<SOAnswersResponse>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onSuccess(Response<SOAnswersResponse> response) {
                        if (response.isSuccessful()){
                            mAdapter.updateAnswers(response.body().getItems());
                        }
                    }

                    @Override
                    public void onError(Throwable e) {

                    }
                });
        }
}
