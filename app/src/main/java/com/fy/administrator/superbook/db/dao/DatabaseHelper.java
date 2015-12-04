package com.fy.administrator.superbook.db.dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.fy.administrator.superbook.db.bean.ManyBook;
import com.fy.administrator.superbook.db.bean.Note;
import com.fy.administrator.superbook.db.bean.SingleBook;
import com.fy.administrator.superbook.util.AppUtil;
import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.sql.SQLException;


public class DatabaseHelper extends OrmLiteSqliteOpenHelper {
	private static final String DB_NAME = "sqlite-test.db";
	private static final int VERSIONCODE = 4;

	private Dao<ManyBook, Integer> manyBookDao = null;
	private Dao<SingleBook, Integer> singleBookDao = null;
	private Dao<Note, Integer> noteDao = null;
	
	private static DatabaseHelper instance;

	private DatabaseHelper(Context context) {
		super(context, DB_NAME, null, VERSIONCODE);
	}
	
	public static synchronized DatabaseHelper getHelper(Context context){
        if (instance == null){
            synchronized (DatabaseHelper.class){
                if (instance == null)
                    instance = new DatabaseHelper(context);
            }
        }
        return instance;
    }

	@Override
	public void onCreate(SQLiteDatabase sqliteDatabase,
			ConnectionSource connectionSource) {
		try {
			TableUtils.createTable(connectionSource, ManyBook.class);
			TableUtils.createTable(connectionSource, SingleBook.class);
			TableUtils.createTable(connectionSource, Note.class);
		} catch (Exception e) {
			Log.e(DatabaseHelper.class.getName(), "Unable to create datbases",
					e);
		}
	}

	@Override
	public void onUpgrade(SQLiteDatabase sqliteDatabase,
			ConnectionSource connectionSource, int oldVer, int newVer) {
		try {
			TableUtils.dropTable(connectionSource, ManyBook.class, true);
			TableUtils.dropTable(connectionSource, SingleBook.class, true);
			TableUtils.dropTable(connectionSource, Note.class, true);
			onCreate(sqliteDatabase, connectionSource);
		} catch (SQLException e) {
			Log.e(DatabaseHelper.class.getName(),
					"Unable to upgrade database from version " + oldVer
							+ " to new " + newVer, e);
		}
	}

	public Dao<ManyBook, Integer> getManyBookDao() throws SQLException {
		if (manyBookDao == null) {
			manyBookDao = getDao(ManyBook.class);
		}
		return manyBookDao;
	}
	
	public Dao<SingleBook, Integer> getSingleBookDao() throws SQLException {
		if (singleBookDao == null) {
			singleBookDao = getDao(SingleBook.class);
		}
		return singleBookDao;
	}
	
	public Dao<Note, Integer> getNoteDao() throws SQLException {
		if (noteDao == null) {
			noteDao = getDao(Note.class);
		}
		return noteDao;
	}

}
