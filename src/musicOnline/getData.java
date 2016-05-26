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

import musicOnline.mapping.MusicMapper;

public class getData {
	public MusicMapper musicMapper;
	private static String baseurl = "http://tingapi.ting.baidu.com/v1/restserver/ting";
	private int endNum = 3000 ;
	
	public JSONObject getWow(String method,int type,int size,int offset) throws HttpException, IOException, JSONException{
		if(offset == endNum) return null;
		HttpClient client = new HttpClient();
		PostMethod postMethod = new PostMethod(baseurl);
		postMethod.addParameter("method", method);
		postMethod.addParameter("type", type+"");
		postMethod.addParameter("size", size+"");
		postMethod.addParameter("offset", offset+"");
		client.getParams().setParameter(
	            HttpMethodParams.HTTP_CONTENT_CHARSET, "utf-8");
	    // 执行并返回状态
	    int status = client.executeMethod(postMethod);
	    String data = postMethod.getResponseBodyAsString();
	    //data = new String(data.getBytes("utf-8"));
	    JSONObject gData = new JSONObject(data);
	    JSONArray songlist = gData.getJSONArray("song_list");
	    for(int i = 0;i<songlist.length();i++){
	    	JSONObject xx = songlist.getJSONObject(i);
	    	musicMapper.addMusic(Integer.parseInt(xx.get("song_id").toString()), xx.get("title").toString(), "active", xx.get("author").toString(), xx.get("album_title").toString(), -1, xx.get("pic_big").toString(),xx.get("lrclink").toString());
	    }
	    //getWow(method, type, size, offset+size);
	    return gData;
	}
}
