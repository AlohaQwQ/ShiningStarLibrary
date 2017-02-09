package aloha.shiningstarbase.util.lol;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/*import com.google.gson.Gson;*/


public class FormData {
	public String modelname;
	public String classname;
	public List<FieldData> fields;

    public FormData(){

    }

    public FieldData getFieldDataInstance(String datatype,String field,String note){
       return 	new FieldData(datatype,field,note);
    }

	public FormData(String data){
		String starttag="message";
		int p1=starttag.length();
		int p2=data.indexOf("{");
		int p3=data.indexOf("}");
		String[] mc=data.subSequence(p1, p2).toString().trim().split("\\.");
		this.modelname=mc[0];
		this.classname=mc[1];

		fields=new ArrayList<FieldData>();

		String body=data.substring(p2+1, p3);
        //LOG.out("类名"+classname);
        //LOG.out("主体"+body);
		String[] sa=body.split(";");
		for(int i=0;i<sa.length;i++){
			String[] field=sa[i].split(" ");
			if(field.length<2){
				return ;
			}
			FieldData fd=new FieldData(field[0], field[1]);
			fields.add(fd);
		}
	}

	public String getClassname() {
		return classname;
	}

	public void setClassname(String classname) {
		this.classname = classname;
	}

	public List<FieldData> getFields() {
		return fields;
	}

	public void setFields(List<FieldData> fields) {
		this.fields = fields;
	}


	public String getModelname() {
		return modelname;
	}

	public void setModelname(String modelname) {
		this.modelname = modelname;
	}

	public String tojavaString(String packagename){
        StringBuffer sb=new StringBuffer();
		sb.append("package ").append(packagename).append(";\r");
		sb.append("public class ").append(firstUpper(classname)).append(" {").append("\r");
		for(int i=0;i<fields.size();i++){
			FieldData fd=fields.get(i);
			sb.append("\t").append("private ").append(fd.getDatatype()).append(" ").append(fd.getField()).append(";").append("\r");

		}
		sb.append("}");
		return sb.toString();
	}

	public static String firstUpper(String text){
		String a1 = text.substring(0,1);
		String a2=text.substring(1);
		 return a1.toUpperCase()+a2;
	}


	public String tohtmlform(String model){
/*		<html>
		<body>

		<form action="/example/html/form_action.asp" method="get">
		  <p>First name: <input type="text" name="fname" /></p>
		  <p>Last name: <input type="text" name="lname" /></p>
		  <input type="submit" value="Submit" />
		</form>

		<p>请单击确认按钮，输入会发送到服务器上名为 "form_action.asp" 的页面。</p>

		</body>
		</html>*/

		StringBuffer sb=new StringBuffer();
		sb.append("<html>").append("\r");
		sb.append("<head>\r");
		sb.append("<meta http-equiv='pragma' content='no-cache'> \r");
		sb.append("<meta http-equiv='Content-Type' content='text/html; charset=UTF-8'>");
		sb.append("</head>\r");
		sb.append("<body>").append("\r");
		sb.append("<form action='").append(model).append("' method='get' >");
		sb.append(" <p>model: <input type='text' readOnly='true'  name='model' value='"+firstUpper(modelname)+"' /></p>");
		sb.append(" <p>action: <input type='text' readOnly='true' name='action' value='"+classname+"' /></p>");
		for(int i=0;i<fields.size();i++){
			FieldData fd=fields.get(i);
			sb.append("\t<p>");
			sb.append(fd.getField()+":");
			sb.append("<input type='text' name='").append(fd.getField()).append("' />\r");
			sb.append(fd.getDatatype());
			sb.append("</p>");
		}
		sb.append("<input type='submit' value='Submit' />");
		sb.append("</form>");
		sb.append("\r<p> post数据:\r");

		//sb.append(new Gson().toJson(toexamplemap()));
		sb.append("\r</p>");
		sb.append("</body>").append("\r");
		sb.append("</html>");

		return sb.toString();
	}


	public Map<String,Object> toexamplemap(){
		Map<String,Object> map=new HashMap<String,Object>();
		map.put("model", firstUpper(modelname));
		map.put("action", classname);
		for(int i=0;i<fields.size();i++){
			FieldData fe=fields.get(i);
			map.put(fe.getField(), fe.getDatatype());
		}
		return map;
	}



	public class FieldData {
		String datatype;
		String field;
		String note;

		public FieldData(String datatype,String field){
			this.datatype=datatype;
			this.field=field;
		}

		public FieldData(String datatype,String field,String note){
			this.datatype=datatype;
			this.field=field;
			this.note=note;
		}
		public String getDatatype() {
			if(datatype.equalsIgnoreCase("string")){
				return "String";
			}
			return datatype;
		}
		public void setDatatype(String datatype) {
			this.datatype = datatype;
		}
		public String getField() {
			return field;
		}
		public void setField(String field) {
			this.field = field;
		}
		public String getNote() {
			return note;
		}
		public void setNote(String note) {
			this.note = note;
		}



	}

}
