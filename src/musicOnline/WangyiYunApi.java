package musicOnline;

import java.io.IOException;

import javax.annotation.Resource;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.stereotype.Component;

import musicOnline.mapping.MusicMapper;
import musicOnline.mapping.SerchLogMapper;

@Component
public class WangyiYunApi extends Api{

	public WangyiYunApi() {
		// TODO Auto-generated constructor stub
		baseurl = "http://music.163.com/api/search/pc";
	}
	@Override
	public JSONArray findMusic(String name) throws HttpException, IOException, JSONException {
		// TODO Auto-generated method stub
		baseurl = "http://music.163.com/api/search/pc";
		JSONArray ans = new JSONArray();
		HttpClient client = new HttpClient();
		PostMethod postMethod = new PostMethod(baseurl);
		postMethod.addParameter("s",name);
		postMethod.addParameter("type","1");
		postMethod.addParameter("limit","20");
		client.getParams().setParameter(
	            HttpMethodParams.HTTP_CONTENT_CHARSET, "utf-8");
		postMethod.addRequestHeader("Referer", "http://music.163.com/");
	    // 执行并返回状态
	    int status = client.executeMethod(postMethod);
	    String data = postMethod.getResponseBodyAsString();
	    JSONObject gData = new JSONObject(data);
	    gData = gData.getJSONObject("result");
	    JSONArray songlist = gData.getJSONArray("songs");
	    if(serchLogMapper.findByContent(name)==null){
	    	for(int i = 0;i<songlist.length();i++){
		    	JSONObject xx = songlist.getJSONObject(i);
		    	String artists = xx.getJSONArray("artists").getJSONObject(0).get("name").toString();
		    	musicMapper.addMusic(Integer.parseInt(xx.get("id").toString())+300000000, xx.get("name").toString(), "active", artists, "-1", -1, "-1","-1");
		    }
	    }
		return null;
	}

	@Override
	public JSONObject findMusicById(Integer musicid)  throws HttpException, IOException, JSONException, NumberFormatException, IllegalArgumentException{
		// TODO Auto-generated method stub
		baseurl = "http://music.163.com/api/song/detail/";
		if(musicid==null) return null;
		JSONObject ans = new JSONObject();
		HttpClient client = new HttpClient();
		PostMethod postMethod = new PostMethod(baseurl);
		postMethod.addParameter("id",musicid+"");
		postMethod.addParameter("ids","["+musicid+"]");
		client.getParams().setParameter(
	            HttpMethodParams.HTTP_CONTENT_CHARSET, "utf-8");
	    // 执行并返回状态
	    int status = client.executeMethod(postMethod);
	    String data = postMethod.getResponseBodyAsString();
	    JSONObject gData = new JSONObject(data);
	    
	    JSONObject songinfo = gData.getJSONArray("songs").getJSONObject(0);
	    
	    ans.put("url", songinfo.get("mp3Url").toString());
	    ans.put("duration", Integer.parseInt(songinfo.getString("duration"))/1000);
	    ans.put("pic", songinfo.getJSONObject("album").getString("picUrl"));
	       
		return ans;
	}

	
}
