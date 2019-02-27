package com.example.weipeng.andemos;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;
import java.util.zip.Inflater;


public class MainActivity extends AppCompatActivity {

    private RecyclerView mRView;
    private RViewAdapter mRVAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();
    }

    private void initViews() {
        // mRView init
        mRView = findViewById(R.id.recycler_view_main);
        mRVAdapter = new RViewAdapter(this);

        // mRView setting
        mRView.setAdapter(mRVAdapter);
        mRView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

        // mRVAdapter setting
        mRVAdapter.add("OpenGL", "OpenGL示例");
        mRVAdapter.add("Camera1", "Camera1示例");
        mRVAdapter.add("Camera2", "Camera2示例");
        mRVAdapter.add("OkHttp", "OkHttp示例");
        mRVAdapter.add("RxJava", "RxJava示例");
        mRVAdapter.add("DataBase", "数据库示例");
        mRVAdapter.add("CustomView", "自定义控件示例");
        mRVAdapter.add("jni", "jni示例");
        mRVAdapter.add("service", "四大组件-service示例");
        mRVAdapter.notifyDataSetChanged();

        mRVAdapter.setClickListener(new RViewAdapter.OnClickListener() {
            @Override
            public void onItemClicked(int index, String title) {
                if (title.equals("OpenGL")) {
                    Intent intent = new Intent();
                    startActivity(intent);
                }
            }
        });
    }

    static class RViewAdapter extends RecyclerView.Adapter<RViewAdapter.ViewHolder> {

        class ViewHolder extends RecyclerView.ViewHolder {

            TextView title;
            TextView content;

            ViewHolder(View view) {
                super(view);
                title = view.findViewById(R.id.text_title);
                content = view.findViewById(R.id.text_content);
            }
        }

        public interface OnClickListener {
            void onItemClicked(int index, String title);
        }

        Context mContext;

        List<String> mTitleList = new ArrayList<>();
        List<String> mContentList = new ArrayList<>();

        OnClickListener mClickListener;

        RViewAdapter(Context context) {
            mContext = context;
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            View view = LayoutInflater.from(mContext).inflate(R.layout.item_main, viewGroup, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder viewHolder, final int i) {
            viewHolder.title.setText(mTitleList.get(i));
            viewHolder.content.setText(mContentList.get(i));
            viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mClickListener != null)
                        mClickListener.onItemClicked(i, mTitleList.get(i));
                }
            });
            if (i % 2 == 1) {
                viewHolder.itemView.setBackgroundColor(Color.parseColor("#eeeeee"));
            }
        }

        @Override
        public int getItemCount() {
            return mTitleList.size();
        }

        public void add(String title, String content) {
            mTitleList.add(title);
            mContentList.add(content);
        }

        public void setClickListener(OnClickListener listener) {
            mClickListener = listener;
        }
    }
}
