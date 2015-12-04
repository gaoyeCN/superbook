package com.fy.administrator.superbook.comparator;

import com.fy.administrator.superbook.db.bean.SingleBook;

import java.util.Comparator;

/**
 * 创建者：Administrator
 * 时间：2015/8/5  13:39
 * 类描述：
 * 修改人：
 * 修改时间：
 * 修改备注：
 */
public class SingleBookComparator implements Comparator<SingleBook> {

    @Override
    public int compare(SingleBook book1, SingleBook book2) {
        if (book1 != null && book2 != null){
            if (book1.getDate() > book2.getDate()){
                return -1;
            }else if(book1.getDate() < book2.getDate()){
                return 1;
            }else {
                return 0;
            }
        }
        return 0;
    }
}
