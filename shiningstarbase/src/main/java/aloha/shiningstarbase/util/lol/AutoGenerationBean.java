package aloha.shiningstarbase.util.lol;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/*import cn.lebaodai.common.util.FormData.FieldData;
import cn.lebaodai.lbd.dao.impl.ActivityDao;
import cn.lebaodai.lbd.vo.Activity;*/

public class AutoGenerationBean {

	final static String sql = "SELECT COLUMN_NAME,COLUMN_TYPE,COLUMN_COMMENT FROM information_schema.COLUMNS where TABLE_SCHEMA='%s' and TABLE_NAME='%s' order by ORDINAL_POSITION;";
	static String[] list;

	//static List<FieldData> lf = new ArrayList<FieldData>();
	static List<String> lf = new ArrayList<String>();

	/**
	 * @param args
	 * @throws SQLException
	 */
	public static void main(String[] args) throws SQLException {
		String className="FirstTest";
		String iDaoBean = IDaoBean(className);
		String iDaoImplBean = IDaoImplBean(className);
		String iServiceBean = IServiceBean(className);
		String iServiceImplBean = IServiceImplBean(className);
		saveFile("I"+className+"Dao", iDaoBean,"dao");

		saveFile(className+"Dao", iDaoImplBean,"dao/impl");

		saveFile("I"+className+"Service", iServiceBean,"service");
		saveFile(className+"Service", iServiceImplBean,"service/impl");
	}



	public static  String  IDaoBean(String className){
		String text="";
		text+="package cn.lebaodai.lbd.dao;                                \r";
		text+="import cn.lebaodai.lbd.vo."+className+";                    \r";
		text+="/**                                                         \r";
		text+=" *                                                          \r";
		text+=" *                                                          \r";
		text+=" * @author  kanghong                                        \r";
		text+=" * @version 0.0.1                                           \r";
		text+=" * @since                                                   \r";
		text+=" */                                                         \r";
		text+="public interface I"+className+"Dao extends IEntityDao<"+className+"> {        \r";
		text+="	                                                           \r";
		text+="}                                                            \r";
		return text;
	}

	public static  String  IDaoImplBean(String className){
		String text="";
		text+="package cn.lebaodai.lbd.dao.impl;                                \r";
		text+="import org.springframework.beans.factory.annotation.Qualifier;   \r";
		text+="import org.springframework.stereotype.Repository;                \r";
		text+="import cn.lebaodai.lbd.dao.I"+className+"Dao;                      \r";
		text+="import cn.lebaodai.lbd.vo."+className+";                           \r";
		text+="/**                                                              \r";
		text+=" *                                                               \r";
		text+=" * @author kanghong                                              \r";
		text+=" * @version 0.0.1                                                \r";
		text+=" * @since 	                                                    \r";
		text+=" */                                                              \r";
		text+="@Repository                                                      \r";
		text+="@Qualifier(\""+className+"Dao"+"\")                                     \r";
		text+="public class "+className+"Dao extends EntityDao<"+className+"> implements I"+className+"Dao {";
		text+="	                                                           \r     \r";
		text+="}";
		return text;
	}



	public static  String  IServiceBean(String className){
		String text="";
		text+="package cn.lebaodai.lbd.service;                                \r";
		text+="import cn.lebaodai.lbd.vo."+className+";                    \r";
		text+="/**                                                         \r";
		text+=" *                                                          \r";
		text+=" *                                                          \r";
		text+=" * @author  kanghong                                        \r";
		text+=" * @version 0.0.1                                           \r";
		text+=" * @since                                                   \r";
		text+=" */                                                         \r";
		text+="public interface I"+className+"Service  {                   \r";
		text+="	                                                           \r";
		text+="}                                                            \r";
		return text;
	}

	public static  String  IServiceImplBean(String className){
		String text="";
		text+="package cn.lebaodai.lbd.service.impl;                            \r";
		text+="import org.springframework.beans.factory.annotation.Autowired;   \r";
		text+="import org.springframework.beans.factory.annotation.Qualifier;   \r";
		text+="import org.springframework.stereotype.Service;                   \r";
		text+="import cn.lebaodai.lbd.dao.I"+className+"Dao;                         \r";
		text+="import cn.lebaodai.lbd.dao.impl."+className+"Dao;                     \r";
		text+="import cn.lebaodai.lbd.service.I"+className+"Service;                 \r";
		text+="import cn.lebaodai.lbd.vo."+className+";                              \r";
		text+="/**                                                              \r";
		text+=" *                                                               \r";
		text+=" *                                                               \r";
		text+=" * @author kanghong                                              \r";
		text+=" * @version 0.0.1                                                \r";
		text+=" * @since                                                        \r";
		text+=" */                                                              \r";
		text+="@Service                                                         \r";
		text+="@Qualifier(\""+className+"Service\")                                    \r";
		text+="public class "+className+"Service extends EntityService<"+className+", "+className+"Dao>    \r";
		text+="		implements I"+className+"Service {    \r";
		text+="	@Autowired    \r";
		text+="	@Qualifier(\""+className+"\")    \r";
		text+="	private I"+className+"Dao "+className.toLowerCase()+"Dao;    \r";
		text+="	                                                           \r";
		text+="}    \r";
		return text;
	}


	public static void saveFile(String classname, String text,String pageName) {

		System.out.println("text:"+text);
		//LOG.out("创建java类：" + classname);
		String filepath = Thread.currentThread().getContextClassLoader()
				.getResource(".").getPath();

		System.out.println("filepath:"+filepath);
		// LOG.out(filepath);


		String[] fileP = filepath.split("target");
		System.out.println(fileP[0]);


		File file = new File(fileP[0] + "/src/main/java/cn/lebaodai/lbd/"+pageName+"/"
				+ FormData.firstUpper(classname) + ".java");
		/* File file = new
		 File("F\:\lebaodai\myelicpseWs\trunk\src\main\java\cn\lebaodai\lbd\vo\Msg_push.java");*/

		if (file.exists()) {
			file.delete();
		}

		if (!file.getParentFile().exists()) {
			file.getParentFile().mkdir();
		}

		try {
			file.createNewFile();
		} catch (IOException e) {
			e.printStackTrace();
		}

		BufferedWriter writer;
		try {
			writer = new BufferedWriter(
					new FileWriter(new File(file.getPath())));
			writer.write(text);
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
}
