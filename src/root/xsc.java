package root;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

public class xsc {
	//解析视频,生成列表网页
	
	
	public static StringBuilder x信息网页(String x网页_CSS_JS资源路径) {
	
		StringBuilder xsb=new StringBuilder();//偷懒一下
		File xFile=new File("C:\\Users\\lenovo\\Desktop\\我的一亿\\download\\");

		if (xsb.length()>0) {
			//("-----执行偷懒!");
			return xsb;//因为是静态,所以一次存在就可以一直使用
		}else {
			xsb.append(
					"<script src=\""+x网页_CSS_JS资源路径+"js/jquery-1.11.2.min.js\"></script> " + 
					"<script src=\""+x网页_CSS_JS资源路径+"js/jquery.treemenu.js\"></script>"
					+"<script>" 
					+"$(function(){"
					+"$(\".tree\").treemenu({delay:300}).openActive();" 
					+"});"
					+"</script>" 
					
					+"<ul class=\"tree\">"
					
					+x根层(xFile)
					
					+ "</ul>");
		}
		
		/**  列表  包围 ( 项目层 )   /列表  */
		return xsb;
	}
	private static StringBuilder x根层(File x上下文路径) {
		
		//返回系列的数据子项即可
		StringBuilder sbr=new StringBuilder();
		File[] xfs = x上下文路径.listFiles();//根路径
		for (File x系列 : xfs) {
			if (x系列.getName().equals("15511024")) {
				sbr.append(x系列层(x系列));
			}
		}
		
		return sbr;
	}
	
	protected static StringBuilder x系列层(File x根文件夹) {	
		
		//一个文件夹代表一个系列的视频团
		String x系列名=null;
		StringBuilder xbr=new StringBuilder();
		
		File[] x集数层 = x根文件夹.listFiles();
		for (File file : x集数层) {
			File xentry = new File(file, "entry.json");
			
			if (!xentry.exists()) {
				return null;
			}
			
			String xa = x读文件(xentry);
			
			JSONObject xJson = JSONObject.parseObject(xa);
			String xavid = xJson.getString("avid");
			String xtype_tag = xJson.getString("type_tag");
			String xtitle = xJson.getString("title");
			if (x系列名==null) {
				x系列名 = xtitle;//这个判断只会执行一次,获取系列名字只用一次就可以
			}
			
			JSONObject xJson2 = xJson.getJSONObject("page_data");		
			String xpage = xJson2.getString("page");
			String xpart = xJson2.getString("part");//当前集名
			
			String x3 = "\"?x300="+xavid+"&x301="+xpage+"&x302="+xtype_tag+
					           "&x303="+xpart+"\"";
			//格式 :"?15511024/27/lua.mp4.bili2api.16&servlet.第25讲.用户管理系统2.0到数据库验证-servlet操作数据库"
			
			xbr.append("<li><a href="+x3+">" + xpart + "</a></li>");	
		}
		
		//???? 上下文路径 + avid + page +type_tag + 解析index.json =视频绝对路径
		
		/*要添加
		 *          <ul>
		 *                <li><a>模板</a> <ul>...子数据很多很多..</ul></li>
		 *                <li><a>模板</a> <ul>...很多很多..</ul></li>
		 *          </ul> 
		 *          画中画 计中计 列表中还带有列表
		 *          先计算内层 再计算外层
		 *          这些数据的外层还是 <li>
		 * **/
		return new StringBuilder().append("<li><a>"+x系列名+"</a>  <ul>"+xbr+"</ul> </li>");
	}
	
	
	public static String[] x_blv_json(StringBuilder xbr) {
		
		//解析json 拼接路径
		String xjsobj = x读文件(new File(xbr.toString()));
		//解析两层
		JSONArray xJsonObjectar=JSONObject.parseObject(xjsobj).getJSONArray("segment_list");
		String[] xList=new String[xJsonObjectar.size()];
		for (int i = 0; i < xJsonObjectar.size(); i++) {
			xList[i] = new File(xbr.toString()).getParent()+"\\"+i+".blv";
		}
				
		return xList;
	}
	
	
	
	private static String x读文件(File xFile) {
		String xstr=null;
		try {
			FileInputStream xin = new FileInputStream(xFile);
			BufferedInputStream xb = new BufferedInputStream(xin);
			byte[] bys=new byte[xb.available()];
			xb.read(bys);
			xb.close();
			xin.close();
			xstr=new String(bys);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return xstr;
	}
	
	
	public static void main(String[] args) {
		//设计请求规则
		//x根层(new File("C:\\Users\\lenovo\\Desktop\\我的一亿\\download\\"));
		
		// x_blv_json(new StringBuilder().append("C:\\Users\\lenovo\\Desktop\\我的一亿\\download\\15511024\\29\\lua.mp4.bili2api.16\\index.json"));
	}

}
