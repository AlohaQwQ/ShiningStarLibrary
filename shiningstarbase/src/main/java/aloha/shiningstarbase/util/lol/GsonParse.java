package aloha.shiningstarbase.util.lol;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * @author  Aloha <br>
 * @version 2015-9-25 下午3:35:55
 * @explain Gson解析类
 */
public class GsonParse {

	/**
	 * @author  Aloha <br>
	 * @version 2015-9-25 12:05
	 * @param
	 * @param   jsonData 解析的JSON内容
	 * @explain GSON解析成对象。并不会调用对象类的构造方法。
	 * @return  解析之后返回一组Topic对象

	public static List<?> parseJSONWithGSON(String jsonData) {
		Gson gson = new Gson();
		List<?> list = null;
		try {
			if (jsonData != null) {
				// 将数据再转为JSON对象，取出试题内容
				JSONObject obj = new JSONObject(jsonData);
				System.out.println("试卷解析成功");
				String qq = obj.get("questions").toString();
				if (qq.equals("none")) {
					System.out.println("questions为空，没有试题");
				}else {
					list = gson.fromJson(qq, new TypeToken<List<TopicSingleChoose>>() {}.getType());
				}
			} else {
				System.out.println("JSON解析失败");
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		/*
		//需加上此判断，否则会报空指针
		if (topiclist != null && topiclist.size() > 0) {
			//实际上取的是qid=21的对象，qid从1开始计算
			//responseText.setText(topiclist.get(20).getTitle());
			System.out.println("解析成功");
		} else {
			System.out.println("解析失败");
		}
		return list;
	}
	 */

	/**
	 * @author  Aloha <br>
	 * @version 2015-9-25 12:05
	 * @param   jsonData 解析的JSON内容
	 * @explain 解析JSON
	 */
	public static void parseJSONWithJSONObject(String jsonData) {
		try {
			JSONArray jsonArray = new JSONArray(jsonData);
			for (int i = 0; i < jsonArray.length(); i++) {
				JSONObject jsonObject = jsonArray.getJSONObject(i);
				String title = jsonObject.getString("title");
				//responseText.setText(title);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
