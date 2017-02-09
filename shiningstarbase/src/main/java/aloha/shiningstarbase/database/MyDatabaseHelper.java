package aloha.shiningstarbase.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.HashMap;

/**
 * @author  Aloha
 * @version 2015-11-29 下午8:16:35
 * @explain 试题本地缓存功能
 * @see     ":[{"qid":1,"id":532,"questiontypeid":3,"dicname":"单选题","testpaperid":2,"title":"电路中（     ）定律指出：流入任意一节点的电流必定等于流出该节点的电流。","content":"<p>A、基尔霍夫第一<\/p><p>B、基尔霍夫第二<\/p><p>C、楞次","optionnum":3,"answer":"A"},
 *                      {"qid":2,"id":533,"questiontypeid":3,"dicname":"单选题","testpaperid":2,"title":"在串联电路中每个电阻上流过的电流（     ）。","content":"<p>A、相同<\/p><p>B、靠前的电阻电流大<\/p><p>C、靠后的电阻电流大","optionnum":3,"answer":"A"},
 *
 */
public class MyDatabaseHelper extends SQLiteOpenHelper {

	private Context mContext;

	/**
	 * @param context	上下文操作
	 * @param name		数据库名称
	 * @param factory	产生Cursor对象的工厂类
	 * @param version	版本号
	 */
	public MyDatabaseHelper(Context context, String name,
							CursorFactory factory, int version) {
		super(context, name, factory, version);
		mContext = context;
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub

	}

	/**
	 * @param  db
	 * @param  tableName
	 * @return  trueOrfalse
	 * @explain 判断数据库中是否存在该表
	 */
	public boolean tableIsExist(SQLiteDatabase db, String tableName){
		Boolean result = false;
		Cursor cursor = db.rawQuery("select name from sqlite_master where type='table';", null);
		while(cursor.moveToNext()){
		    //遍历出表名
			String name = cursor.getString(0);
			Log.i("QAQ", "tabIsExist-"+name);
			if (tableName.equals(name)) {
				result = true;
				break;
			}
		}
		return result;
	}

	/**
	 * @param db	数据库对象
	 * @param tableName	试卷信息表名
	 * @explain  往数据库中创建新的试卷信息表
	 */
	public void createTable(SQLiteDatabase db, String tableName){
		/*IF NOT EXISTS判断，如果表存在的时候，便不会重复创建表格。除非当前的表名和某一索引名冲突，就将会有错误抛出。*/
		/*db.execSQL("create table if not exists " + tablename
				+ "(id integer primary key autoincrement, " 	integer-整型    primary key-主键   autoincrement-表示自动增长
				+ "author text, "								text-文本类型
				+ "price real, "								real-浮点类型
				+ "pages integer, " 							blob-二进制类型
				+ "name text)");*/
		db.execSQL("create table if not exists " + tableName
				+ "(qid integer primary key autoincrement, "
				+ " id text, "
				+ " questiontypeid text, "
				+ " dicname text, "
				+ " testpaperid text, "
				+ " title1 text, "
				+ " title2 text, "
				+ " title3 text, "
				+ " content text, "
				+ " optionnum text, "
				+ " answer text)");
	}

	/**
	 * @explain  往试卷信息表中写入试题信息
	 * @param db	SQLiteDatabase 数据库
	 * @param tableName		操作表名
	 * @param qid	第几题	sqlite 主键默认从1开始自增
	 * @param id	试题ID
	 * @param questiontypeid	试题类型
	 * @param dicname
	 * @param testpaperid
	 * @param title1
	 * @param title2
	 * @param title3
	 * @param content	试题内容
	 * @param optionnum	选项个数
	 * @param answer	选项
	 */
	public void addDataToTable(SQLiteDatabase db, String tableName, int qid, String id, String questiontypeid,
							   String dicname, String testpaperid, String title1, String title2, String title3, String content, String optionnum, String answer) {

		ContentValues values = new ContentValues();

		values.put("id", id);
		values.put("questiontypeid", questiontypeid);
		values.put("dicname", dicname);
		values.put("testpaperid", testpaperid);
		values.put("title1", title1);
		values.put("title2", title2);
		values.put("title3", title3);
		values.put("content", content);
		values.put("optionnum", optionnum);
		values.put("answer", answer);
		db.insert( tableName , null, values);
		values.clear();
	}

	/**
	 * @param  db	数据库对象
	 * @param  tableName 试卷信息表名
	 * @return 从数据库中取出缓存了的试卷信息。引用类型的数组(HashMap)。存储的是对象的引用，因此必须每次都new 一个新的questionMap对象，即一道新的试题。
	 */
	public HashMap<String, HashMap<String, String>> queryDataToTable(SQLiteDatabase db, String tableName) {
					  /*db.query(table, columns, selection, selectionArgs, groupBy, having, orderBy);*/
		/*试卷信息集合*/
		HashMap<String, HashMap<String, String>> examPaper = new HashMap<String, HashMap<String, String>>();
		Cursor cursor = db.query( tableName, null, null, null, null, null, null);
		/*使用Cursor标尺移动到表的每一行遍历*/
		if (cursor.moveToFirst()) {
			do {
				/*每道试题信息集合*/
				HashMap<String, String> question = new HashMap<String, String>();
				/*sqlite 主键默认从1开始自增*/
				String qid = cursor.getString(cursor.getColumnIndex("qid"));
				String id = cursor.getString(cursor.getColumnIndex("id"));
				String questiontypeid = cursor.getString(cursor.getColumnIndex("questiontypeid"));
				String dicname = cursor.getString(cursor.getColumnIndex("dicname"));
				String testpaperid = cursor.getString(cursor.getColumnIndex("testpaperid"));
				String title1 = cursor.getString(cursor.getColumnIndex("title1"));
				String title2 = cursor.getString(cursor.getColumnIndex("title2"));
				String title3 = cursor.getString(cursor.getColumnIndex("title3"));
				String content = cursor.getString(cursor.getColumnIndex("content"));
				String optionnum = cursor.getString(cursor.getColumnIndex("optionnum"));
				String answer = cursor.getString(cursor.getColumnIndex("answer"));
				question.put("qid",qid);
				question.put("id",id);
				question.put("questiontypeid",questiontypeid);
				question.put("dicname",dicname);
				question.put("testpaperid",testpaperid);
				question.put("title1",title1);
				question.put("title2",title2);
				question.put("title3",title3);
				question.put("content",content);
				question.put("optionnum",optionnum);
				question.put("answer",answer);
				examPaper.put(qid, question);
			} while (cursor.moveToNext());
		}
		cursor.close();
		return examPaper;
	}
}
