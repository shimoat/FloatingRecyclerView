package com.example.administrator.recyclerviewsuspension;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;

import com.example.administrator.recyclerviewsuspension.api.RequireApi;
import com.example.administrator.recyclerviewsuspension.base.BaseResponse;
import com.example.administrator.recyclerviewsuspension.bean.ComingBean;
import com.example.administrator.recyclerviewsuspension.bean.MovieBean;
import com.example.administrator.recyclerviewsuspension.util.Constant;
import com.example.administrator.recyclerviewsuspension.view.FloatingItemDecoration;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;

    private List<ComingBean> datas;
    private Map<Integer, String> keys = new HashMap<>();//存放所有key的位置和内容
    private MyAdapter adapter;
    private Context mContext = MainActivity.this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerView_main);
//        initView();
        initData();
    }

    private void initData() {
        datas = new ArrayList<>();
        requestNetwork();
    }

    private void requestNetwork() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constant.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
        RequireApi movieApiService = retrofit.create(RequireApi.class);
        movieApiService.getMovieDatas()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<BaseResponse<MovieBean>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(BaseResponse<MovieBean> movieBeanBaseResponse) {
                        datas.addAll(movieBeanBaseResponse.getData().getComing());
                        initView();
//                        adapter.notifyDataSetChanged();
                    }
                });

    }

    private void initView() {

        GridLayoutManager manager = new GridLayoutManager(this, 1);
        mRecyclerView.setLayoutManager(manager);

        keys.put(0, "欧美大片");
        keys.put(2, "国产剧透");
        keys.put(4, "印度神剧");

        final FloatingItemDecoration floatingItemDecoration =
                new FloatingItemDecoration(this, Color.GRAY, 1, 1);
        floatingItemDecoration.setKeys(keys);
        floatingItemDecoration.setmTitleHeight((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 50, getResources().getDisplayMetrics()));
        mRecyclerView.addItemDecoration(floatingItemDecoration);

        adapter = new MyAdapter(mContext, datas);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setAdapter(adapter);
    }
}
