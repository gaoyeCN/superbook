package com.fy.administrator.superbook.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.fy.administrator.superbook.R;
import com.fy.administrator.superbook.adapter.NoteAdapter;
import com.fy.administrator.superbook.comparator.NoteComparator;
import com.fy.administrator.superbook.values.Constants;
import com.fy.administrator.superbook.db.bean.Note;
import com.fy.administrator.superbook.db.dao.DatabaseHelper;
import com.fy.administrator.superbook.util.ScreenUtil;
import com.fy.administrator.superbook.util.UserUtil;
import com.nineoldandroids.view.ViewHelper;
import com.umeng.update.UmengUpdateAgent;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class NewMainActivity extends BaseActivity implements View.OnClickListener{
    private final String TAG = "com.fy.administrator.superbook.activity.NewMainActivity";
    private ListView lv_main;
    private DatabaseHelper mHelper;
    private List<Note> notes;
    private NoteAdapter adapter;
    private ImageButton ib_add;
    private boolean ibmove;
    private TextView tv_left,tv_middle;
    private float[] positions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        UmengUpdateAgent.setUpdateOnlyWifi(false);
        UmengUpdateAgent.update(this);
        setContentView(R.layout.activity_new_main);

        initView();
        setData();
        setListeners();
    }

    @Override
    protected void initView() {
        lv_main = (ListView) findViewById(R.id.lv_main);
        ib_add = (ImageButton) findViewById(R.id.ib_add);
        tv_left = (TextView) findViewById(R.id.title_left);
        tv_middle = (TextView) findViewById(R.id.title_middle);
        rl_title = (RelativeLayout) findViewById(R.id.title_main);
    }

    @Override
    protected void setData() {
        tv_left.setVisibility(View.GONE);
        tv_middle.setText("超级记事本");
        mHelper = DatabaseHelper.getHelper(this);
        notes = new ArrayList<>();
        adapter = new NoteAdapter(this, notes);
        lv_main.setAdapter(adapter);
        LayoutAnimationController controller = new LayoutAnimationController(AnimationUtils.loadAnimation(this,R.anim.toleft_show));
        lv_main.setLayoutAnimation(controller);
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus){
            positions = UserUtil.getAddPosition(this);
            Log.i(TAG,"onWindowFocusChanged: x:" + positions[0] + " y:" + positions[1]);
            if (positions[0] != -1 && positions[1] != -1){
                ViewHelper.setX(ib_add,positions[0]);
                ViewHelper.setY(ib_add,positions[1]);
            }
        }
    }


    private void getNotes() {
        try {
            notes.removeAll(notes);
            notes.addAll(mHelper.getNoteDao().queryForAll());
            bookSort();
            adapter.notifyDataSetChanged();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void bookSort(){
        Collections.sort(this.notes, new NoteComparator());
    }

    private float startX,startY,endX,endY,ibStartX,ibStartY,distanceX,distanceY;
    @Override
    protected void setListeners() {
        ib_add.setOnClickListener(this);
        /*ib_add.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                ibmove = true;
                ViewHelper.setAlpha(ib_add,0.5f);
                return true;
            }
        });*/
        ib_add.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()){
                    case MotionEvent.ACTION_DOWN:
                        startX = endX = event.getRawX();
                        startY = endY = event.getRawY();
                        ibStartX = ib_add.getX();
                        ibStartY = ib_add.getY();
                        distanceX = distanceY = 0;
                        //Toast.makeText(NewMainActivity.this,"x:" + event.getRawX() + " y:" + event.getRawY(),Toast.LENGTH_SHORT).show();
                        break;
                    case MotionEvent.ACTION_MOVE:
                        endX = event.getRawX();
                        endY = event.getRawY();
                        float mx = endX - startX;
                        float my = endY - startY;
                        distanceX += mx;
                        distanceY += my;
                        if (Math.abs(distanceX) > 10 || Math.abs(endX - startX) > 10 || ibmove){

                            positions[0] = ibStartX + distanceX;
                            positions[1] = ibStartY + distanceY;
                            if (positions[0] < 0){
                                positions[0] = 0;
                            }
                            if (positions[0] > ScreenUtil.getScreenWidth(NewMainActivity.this) - ib_add.getWidth()){
                                positions[0] = ScreenUtil.getScreenWidth(NewMainActivity.this) - ib_add.getWidth();
                            }
                            if (positions[1] < ScreenUtil.dp2px(50,NewMainActivity.this)){
                                positions[1] = ScreenUtil.dp2px(50,NewMainActivity.this);
                            }
                            if (positions[1] > ScreenUtil.getScreenHeight(NewMainActivity.this) - ScreenUtil.getStatusBarHeight(NewMainActivity.this) - ib_add.getHeight()){
                                positions[1] = ScreenUtil.getScreenHeight(NewMainActivity.this) - ScreenUtil.getStatusBarHeight(NewMainActivity.this) - ib_add.getHeight();
                            }

                            ViewHelper.setX(ib_add,positions[0]);
                            ViewHelper.setY(ib_add,positions[1]);
                            startY = endY;
                            startX = endX;

                            ibmove = true;
                            return true;
                        }
                        startY = endY;
                        startX = endX;
                        break;
                    case MotionEvent.ACTION_CANCEL:
                    case MotionEvent.ACTION_UP:
                        if (ibmove){
                            ibmove = false;
                            return true;
                        }

                        ViewHelper.setAlpha(ib_add,1f);
                        break;
                }
                return false;
            }
        });
        lv_main.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Note note = (Note) parent.getItemAtPosition(position);
                Intent intent = new Intent(NewMainActivity.this,InfoActivity.class);
                intent.putExtra(Constants.BOOK,note);
                startActivity(intent);
            }
        });
        lv_main.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                Note book = (Note) parent.getItemAtPosition(position);
                Intent intent = new Intent(NewMainActivity.this, ChoseActivity.class);
                intent.putExtra("choseBook", book);
                intent.putExtra(Constants.TYPE, Constants.TYPE_NOTE);
                startActivity(intent);
                return true;
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        //MobclickAgent.onPageStart("NewMainActivity"); //统计页面(仅有Activity的应用中SDK自动调用，不需要单独写)

        getNotes();
    }

    @Override
    protected void onPause() {
        super.onPause();
        //MobclickAgent.onPageEnd("NewMainActivity"); // （仅有Activity的应用中SDK自动调用，不需要单独写）保证 onPageEnd 在onPause 之前调用,因为 onPause 中会保存信息

        UserUtil.saveAddPosition(this,positions);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.ib_add:
                //Toast.makeText(this,"点击",Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(this,AddActivity.class);
                intent.putExtra(Constants.TYPE,Constants.TYPE_NOTE);
                startActivity(intent);
                break;
        }
    }

}
