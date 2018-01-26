package com.xjh.search;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.xjh.search.adapter.NewSearchSaveAdapter;
import com.xjh.search.db.SearchName;
import com.xjh.search.db.search.DaoMaster;
import com.xjh.search.db.search.DaoSession;
import com.xjh.search.db.search.SearchNameDao;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    TextView mTvCancel;
    TextView mTvSave;
    EditText mEtWrite;
    RecyclerView mRvSearchSave;
    LinearLayout mLlRecord;
    private NewSearchSaveAdapter saveAdapter;
    List<SearchName> searchNameList;
    SearchNameDao nameDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
        initDao();
        initData();
        EditSend();
    }

    private void init() {
        mTvCancel = findViewById(R.id.tv_cancel_save);
        mTvSave = findViewById(R.id.tv_search_save);
        mEtWrite = findViewById(R.id.et_search);
        mRvSearchSave = findViewById(R.id.rv_search_save);
        mLlRecord = findViewById(R.id.ll_search_save);
        mTvCancel.setOnClickListener(this);
        mTvSave.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_cancel_save:
                nameDao.deleteAll();
                searchNameList = nameDao.queryBuilder().list();
                saveAdapter = new NewSearchSaveAdapter(this, searchNameList);
                mRvSearchSave.setAdapter(saveAdapter);
                break;
            case R.id.tv_search_save:
                startActivity(new Intent(this, Main2Activity.class));
                if (mEtWrite.getText().length() > 0) {
                    SearchName name = new SearchName(null, mEtWrite.getText().toString());
                    nameDao.insert(name);
                    mLlRecord.setVisibility(View.GONE);
                }
                finish();
                break;
            default:
                break;
        }
    }

    private void initDao() {
        //操作数据库
        DaoMaster.DevOpenHelper devOpenHelper = new DaoMaster.DevOpenHelper(this, "serch.db");
        DaoMaster daoMaster = new DaoMaster(devOpenHelper.getWritableDatabase());
        DaoSession daoSession = daoMaster.newSession();
        nameDao = daoSession.getSearchNameDao();
        searchNameList = new ArrayList<>();
        //清除数据库中重复的文字，赋值给历史记录
        if (mEtWrite.getText().length() < 1) {
            mLlRecord.setVisibility(View.VISIBLE);
            Set set = new HashSet();
            for (Iterator iter = nameDao.queryBuilder().list().iterator(); iter.hasNext(); ) {
                SearchName element = (SearchName) iter.next();
                if (set.add(element.getName())) {
                    searchNameList.add(element);
                }
            }
        }
    }

    private void initData() {
        //获取搜索的值
        saveAdapter = new NewSearchSaveAdapter(this, searchNameList);
        mRvSearchSave.setAdapter(saveAdapter);
        mRvSearchSave.setLayoutManager(new GridLayoutManager(this, 3));
    }

    //虚拟键盘的回车键
    private void EditSend() {
        mEtWrite.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent event) {
                if (i == EditorInfo.IME_ACTION_SEND) {
                    if (mEtWrite.getText().length() > 0) {
                        SearchName name = new SearchName(null, mEtWrite.getText().toString());
                        nameDao.insert(name);
                        mLlRecord.setVisibility(View.GONE);
                    }
                }
                return false;
            }
        });
    }

}
