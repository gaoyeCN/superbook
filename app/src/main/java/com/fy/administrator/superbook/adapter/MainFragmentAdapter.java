package com.fy.administrator.superbook.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * 创建者：Administrator
 * 时间：2015/7/23  16:35
 * 类描述：
 * 修改人：
 * 修改时间：
 * 修改备注：
 */
public class MainFragmentAdapter extends FragmentPagerAdapter {
    List<Fragment> mList;
    FragmentManager fm;

    public MainFragmentAdapter(FragmentManager fm, List<Fragment> mList) {
        super(fm);
        this.fm = fm;
        this.mList = mList;
    }

    //清空内存fragment
    public void clearFragments() {
        if (this.mList != null) {
            FragmentTransaction ft = fm.beginTransaction();
            for (Fragment f : this.mList) {
                ft.remove(f);
            }
            ft.commit();
            ft = null;
            fm.executePendingTransactions();
        }
    }

    @Override
    public Fragment getItem(int position) {
        return mList.get(position);
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object obj) {
        return view == ((Fragment) obj).getView();
    }

    // 初始化每个页卡选项
    @Override
    public Object instantiateItem(ViewGroup arg0, int arg1) {
        // TODO Auto-generated method stub
        return super.instantiateItem(arg0, arg1);
    }
}
