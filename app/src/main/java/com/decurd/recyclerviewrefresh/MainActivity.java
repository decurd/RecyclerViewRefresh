package com.decurd.recyclerviewrefresh;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements AdapterItem.OnLoadMoreListener {

        private AdapterItem mAdapter;
        private ArrayList<Item> itemList;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main);

            itemList = new ArrayList<Item>();
            RecyclerView mRecyclerView = (RecyclerView) findViewById(R.id.rvList);
            LinearLayoutManager mLayoutManager = new LinearLayoutManager(this);
            mRecyclerView.setLayoutManager(mLayoutManager);
            mAdapter = new AdapterItem(this);
            mAdapter.setLinearLayoutManager(mLayoutManager);
            mAdapter.setRecyclerView(mRecyclerView);
            mRecyclerView.setAdapter(mAdapter);

        }

        @Override
        protected void onStart() {
            super.onStart();
            Log.d("MainActivity_", "onStart");
            loadData();
        }


        //스크롤이 끝에 도달하였을 때 실행 내용
        @Override
        public void onLoadMore() {
            Log.d("MainActivity_", "onLoadMore");
            mAdapter.setProgressMore(true);
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    itemList.clear();
                    mAdapter.setProgressMore(false);

                    ///////이부분에을 자신의 프로젝트에 맞게 설정하면 됨
                    //다음 페이지? 내용을 불러오는 부분
                    int start = mAdapter.getItemCount();
                    int end = start + 15;
                    for (int i = start + 1; i <= end; i++) {
                        itemList.add(new Item("Item " + i));
                    }
                    //////////////////////////////////////////////////
                    mAdapter.addItemMore(itemList);
                    mAdapter.setMoreLoading(false);
                }
            }, 2000);
        }

        private void loadData() {
            itemList.clear();
            for (int i = 1; i <= 20; i++) {
                itemList.add(new Item("Item " + i));
            }
            mAdapter.addAll(itemList);
        }

}
