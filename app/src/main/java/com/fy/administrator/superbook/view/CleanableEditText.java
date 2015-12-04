package com.fy.administrator.superbook.view;


import android.content.Context;
import android.content.res.TypedArray;
import android.text.Editable;
import android.text.InputFilter;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.fy.administrator.superbook.R;

public class CleanableEditText extends RelativeLayout {
	Context context;
	LayoutInflater inflater;
	EditText et;
	RelativeLayout rl;
	AttributeSet attrs;
	TextView tv;
	CleanableEditTextChangeListener editTextChangeListener;
	private boolean first;
	private EditTextTouchListener editTextTouchListener;
	private float mWidth,mHeigth;
	

	public CleanableEditText(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		this.context = context;
		this.attrs = attrs;
		init();
	}

	public CleanableEditText(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.context = context;
		this.attrs = attrs;
		init();
	}


	private void init() {
		inflater = LayoutInflater.from(context);
		View view = inflater.inflate(R.layout.layout_info, null);
		addView(view);
		et = (EditText) findViewById(R.id.et);
		rl = (RelativeLayout) findViewById(R.id.rl);
		tv = (TextView) findViewById(R.id.tv);
		
		TypedArray a = context.obtainStyledAttributes(attrs,     
                R.styleable.infoEdit);  
		
		String left = a.getString(R.styleable.infoEdit_infoEdit_left);
		String hint = a.getString(R.styleable.infoEdit_infoEdit_hint);
		int length = a.getInteger(R.styleable.infoEdit_infoEdit_length, -1);

		int type = a.getInteger(R.styleable.infoEdit_infoEdit_type,1);

		a.recycle();
		tv.setText(left);
		et.setHint(hint);
		
		rl.setVisibility(View.GONE);
		
		et.setOnTouchListener(new OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				if(editTextTouchListener != null){
					return editTextTouchListener.onTouch(v, event);
				}
				return false;
			}
		});
		
		if(length != -1){
			et.setMaxWidth(length);
		}

		if (type == 2){
			et.setInputType(InputType.TYPE_CLASS_NUMBER);
		}

		et.addTextChangedListener(new TextWatcher() {
			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
				if(editTextChangeListener != null){
					editTextChangeListener.onTextChanged(s, start, before, count);
				}
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				if(editTextChangeListener != null){
					editTextChangeListener.beforeTextChanged(s, start, count, after);
				}
			}

			@Override
			public void afterTextChanged(Editable s) {
				if (s.length() == 0) {
					rl.setVisibility(View.GONE);
				} else {
					rl.setVisibility(View.VISIBLE);
				}
				
				et.removeTextChangedListener(this);
				et.setText(s.toString().toUpperCase());
				et.setSelection(s.toString().length());
				et.addTextChangedListener(this);
				
				if(editTextChangeListener != null){
					editTextChangeListener.afterTextChanged(s);
				}
			}
		});
		rl.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				et.setText("");
			}
		});
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		if (!first){
			mWidth = MeasureSpec.getSize(widthMeasureSpec);
			mHeigth = MeasureSpec.getSize(heightMeasureSpec);
			setMeasuredDimension((int)mWidth,(int)mHeigth);
		}
	}

	public void setText(String s) {
		if (s != null && !s.equals("")) {
			et.setText(s);
			rl.setVisibility(View.VISIBLE);
		}
	}
	public void setHint(String s) {
		if (s != null && !s.equals("")) {
			et.setHint(s);
		}
	}

	public String getText() {
		return et.getText().toString().trim();
	}
	
	public void setMaxLength(int length) {
		if (length > 0) {
			et.setFilters(new InputFilter[]{new InputFilter.LengthFilter(length)});
		}
	}
	
	public interface CleanableEditTextChangeListener{
		public void onTextChanged(CharSequence s, int start, int before, int count);
		public void beforeTextChanged(CharSequence s, int start, int count,
									  int after);
		public void afterTextChanged(Editable s);
	}
	
	
	public interface EditTextTouchListener{
		public boolean onTouch(View v, MotionEvent event);
	}

	public void addCleanableEditTextChangeListener(CleanableEditTextChangeListener editTextChangeListener){
		this.editTextChangeListener = editTextChangeListener;
	}
	public void setEditTextTouchListener(EditTextTouchListener editTextTouchListener){
		this.editTextTouchListener = editTextTouchListener;
	}

}
