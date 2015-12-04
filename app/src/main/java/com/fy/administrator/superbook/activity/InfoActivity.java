package com.fy.administrator.superbook.activity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.fy.administrator.superbook.R;
import com.fy.administrator.superbook.values.Constants;
import com.fy.administrator.superbook.db.bean.Note;
import com.fy.administrator.superbook.db.dao.DatabaseHelper;

import java.sql.SQLException;
import java.text.SimpleDateFormat;

public class InfoActivity extends BaseActivity implements View.OnClickListener {
    private TextView title_left,title_middle,title_right,tv_location;
    private TextView tv_info_date;
    private EditText et_info_info;
    private Note note;
    private SimpleDateFormat sf= new SimpleDateFormat("yyyy/MM/dd HH:mm");
    private DatabaseHelper mHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);


        initType();
        initView();
        setData();
        setListeners();
    }

    private void initType() {
        note = (Note) getIntent().getSerializableExtra(Constants.BOOK);
    }

    protected void initView() {
        title_left = (TextView) findViewById(R.id.title_left);
        title_middle = (TextView) findViewById(R.id.title_middle);
        title_right = (TextView) findViewById(R.id.title_right);

        tv_info_date = (TextView) findViewById(R.id.tv_info_date);
        tv_location = (TextView) findViewById(R.id.tv_location);
        et_info_info = (EditText) findViewById(R.id.et_info_info);
    }

    protected void setData() {
        title_left.setText("返回");
        title_middle.setText("记事本信息");
        title_right.setText("修改");

        title_right.setVisibility(View.GONE);
        mHelper = DatabaseHelper.getHelper(this);
        tv_info_date.setText(sf.format((note.getDate())));
        et_info_info.setText(note.getInfo());
        et_info_info.setSelection(et_info_info.getText().length());
        tv_location.setText(note.getAddress());
    }

    protected void setListeners() {
        title_left.setOnClickListener(this);
        title_right.setOnClickListener(this);

        et_info_info.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                note.setInfo(s.toString());
                title_right.setVisibility(View.VISIBLE);
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.title_left:
                finish();
                break;
            case R.id.title_right:
                confirm();
                break;
        }
    }

    private void confirm() {
        try {
            mHelper.getNoteDao().update(note);
            finish();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
