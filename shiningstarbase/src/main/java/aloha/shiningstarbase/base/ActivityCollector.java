package aloha.shiningstarbase.base;

import android.app.Activity;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * @author  Aloha
 * @version 2015-10-22 下午3:54:34
 * @explain 活动管理器 ////.....
 */
public class ActivityCollector {

	public static Map<String,Activity> activities = new HashMap<String,Activity>();

	/**
	 * @explain 将正在创建的活动添加到活动管理器中
	 * @param className 当前类名为key
	 * @param activity 添加的activity
     */
	public static void addActivity(String className,Activity activity) {
		activities.put(className,activity);
	}

	/**
	 * @explain 指定activity 销毁
	 * @param className
	 */
	public static void removeActivity(String className) {
		activities.remove(className);
	}

	/**
	 * @explain 将List中存储的所有活动全部销毁
	 * @param
	 */
	public static void finishAll() {
		Iterator iter = activities.entrySet().iterator();
		while (iter.hasNext()) {
			Map.Entry entry = (Map.Entry) iter.next();
			Object key = entry.getKey();
			Object val = entry.getValue();
			activities.remove(key);
		}
	}
}
