package com.fy.administrator.superbook.view;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RelativeLayout;

import com.fy.administrator.superbook.R;
import com.fy.administrator.superbook.util.StringUtil;

import static android.view.ViewGroup.*;

/**
 * 创建者：Administrator
 * 时间：2015/7/24  09:27
 * 类描述：
 * 修改人：
 * 修改时间：
 * 修改备注：
 */
public class DelEdit extends RelativeLayout{
    private Context context;
    private AttributeSet attrs;
    private LayoutInflater inflater;
    private EditText et;
    private DelView dv;
    private float mWidth;
    private float mHeigth;
    private boolean firstTag;


    public DelEdit(Context context) {
        this(context, null);
    }

    public DelEdit(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        this.attrs = attrs;

        initView();
        setListeners();
    }


    private void setListeners() {
        dv.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                et.setText("");
            }
        });
        et.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                setDvVisibility();
            }
        });
    }

    private void initView() {
        inflater = LayoutInflater.from(context);
        View v = inflater.inflate(R.layout.layout_deledit, null);
        v.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.MATCH_PARENT));
        addView(v);

        et = (EditText) findViewById(R.id.et);
        dv = (DelView) findViewById(R.id.dv);
        setDvVisibility();
    }

    private void setDvVisibility() {
        if (StringUtil.isEmpty(et.getText().toString().trim())){
            dv.setVisibility(View.GONE);
        }else{
            dv.setVisibility(View.VISIBLE);
        }
    }
}
