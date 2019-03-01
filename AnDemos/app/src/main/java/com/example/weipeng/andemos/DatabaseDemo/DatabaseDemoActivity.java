package com.example.weipeng.andemos.DatabaseDemo;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.weipeng.andemos.DemoActivity;
import com.example.weipeng.andemos.R;

import java.util.ArrayList;
import java.util.List;

public class DatabaseDemoActivity extends DemoActivity {

    List<Button> mAllButtons = new ArrayList<>();
    OnClickListener mOnAllBtnClkListener = new OnClickListener();
    EditText mAddTextName;
    EditText mAddTextAge;
    EditText mDelTextName;
    EditText mUpTextName;
    EditText mUpTextAge;
    EditText mFindTextName;
    TextView mFindTextAge;
    TextView mFindTextAll;

    MyDBHelper mMyDBHelper;

    @Override
    public String getDemoTitle() {
        return "DatabaseDemo";
    }

    @Override
    public String getDemoContent() {
        return "数据库操作示例";
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.database_demo);
        initViews();

        mMyDBHelper = new MyDBHelper(this, "students.db", null, 1);
    }

    private void initViews() {
        mAddTextName = findViewById(R.id.text_add_name);
        mAddTextAge = findViewById(R.id.text_add_age);
        mDelTextName = findViewById(R.id.text_delete_name);
        mUpTextName = findViewById(R.id.text_update_name);
        mUpTextAge = findViewById(R.id.text_update_age);
        mFindTextName = findViewById(R.id.text_find_name);
        mFindTextAge = findViewById(R.id.text_find_age);
        mFindTextAll = findViewById(R.id.text_find_all);

        mAllButtons.add((Button) findViewById(R.id.button_add));
        mAllButtons.add((Button) findViewById(R.id.button_delete));
        mAllButtons.add((Button) findViewById(R.id.button_update));
        mAllButtons.add((Button) findViewById(R.id.button_find));
        mAllButtons.add((Button) findViewById(R.id.button_find_all));

        for (Button button: mAllButtons) {
            button.setOnClickListener(mOnAllBtnClkListener);
        }
    }

    class OnClickListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            // NOTE:以下为不规范操作，io/db操作最好放在后台线程处理，防止ui被阻塞，为了清晰演示db的使用，这里就偷个懒直接在主线程调用
            SQLiteDatabase rdDB = mMyDBHelper.getReadableDatabase();
            SQLiteDatabase wrDB = mMyDBHelper.getWritableDatabase();

            switch (v.getId()) {
                case R.id.button_add: {
                    if (mAddTextName.getText().toString().isEmpty() || mAddTextAge.getText().toString().isEmpty())
                        break;
                    ContentValues values = new ContentValues();
                    values.put("name", mAddTextName.getText().toString());
                    values.put("age", Integer.valueOf(mAddTextAge.getText().toString()));
                    rdDB.insert("students", null, values);
                }break;
                case R.id.button_find_all: {
                    Cursor cursor = rdDB.query(
                            "students", null, null, null, null, null, null);
                    String info = new String("all data:\n");

                    while (cursor.moveToNext()) {
                        String name = cursor.getString(cursor.getColumnIndex("name"));
                        int age = cursor.getInt(cursor.getColumnIndex("age"));
                        info = info + name + ":" + age + "\n";
                    }
                    cursor.close();
                    mFindTextAll.setText(info);
                }break;
                case R.id.button_update: {
                    if (mUpTextName.getText().toString().isEmpty() || mUpTextAge.getText().toString().isEmpty())
                        break;
                    ContentValues values = new ContentValues();
                    //values.put("name", mUpTextName.getText().toString());
                    values.put("age", Integer.valueOf(mUpTextAge.getText().toString()));
                    wrDB.update("students", values, "name=?", new String[] {mUpTextName.getText().toString()});
                }break;
                case R.id.button_delete: {
                    if (mDelTextName.getText().toString().isEmpty())
                        break;
                    wrDB.delete("students", "name=?", new String[] {mDelTextName.getText().toString()});
                }break;
                case R.id.button_find: {
                    if (mFindTextName.getText().toString().isEmpty())
                        break;
                    Cursor cursor = rdDB.query(
                            "students",
                            null,
                            "name=\""+mFindTextName.getText().toString()+"\"",
                            null,
                            null,
                            null,
                            null);
                    if (cursor.moveToNext()) {
                        int age = cursor.getInt(cursor.getColumnIndex("age"));
                        mFindTextAge.setText("" + age);
                    }

                    cursor.close();
                }break;
            }
            rdDB.close();
            wrDB.close();
        }
    }
}
