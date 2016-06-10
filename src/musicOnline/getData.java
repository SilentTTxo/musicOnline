package musicOnline;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import musicOnline.data.Music;
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
	public JSONArray findMusic(String name) throws HttpException, IOException, JSONException{
		String Lname = "%"+name+"%";
		JSONArray ans = new JSONArray();
		HttpClient client = new HttpClient();
		PostMethod postMethod = new PostMethod(baseurl);
		postMethod.addParameter("method","baidu.ting.search.catalogSug");
		postMethod.addParameter("query",name);
		client.getParams().setParameter(
	            HttpMethodParams.HTTP_CONTENT_CHARSET, "utf-8");
	    // 执行并返回状态
	    int status = client.executeMethod(postMethod);
	    String data = postMethod.getResponseBodyAsString();
	    JSONObject gData = new JSONObject(data);
	    JSONArray songlist = gData.getJSONArray("song");
	    for(int i = 0;i<songlist.length();i++){
	    	JSONObject xx = songlist.getJSONObject(i);
	    	musicMapper.addMusic(Integer.parseInt(xx.get("songid").toString()), xx.get("songname").toString(), "active", xx.get("artistname").toString(), "-1", -1, "-1","-1");
	    }
		List<Music> xx = musicMapper.findByName(Lname);
		for(Music temp : xx){
			JSONObject js = new JSONObject();
			js.put("id", temp.getId());
			js.put("album", temp.getAlbum());
			js.put("artist", temp.getArtist());
			js.put("title", temp.getTitle());
			js.put("url", temp.getUrl());
			js.put("duration", temp.getDuration());
			js.put("lrc", temp.getLrc());
			js.put("img", temp.getImg());
			ans.put(js);
		}
	    return ans;
	}
	public JSONObject findMusicByIdFromBaidu(Integer musicid) throws HttpException, IOException, JSONException, NumberFormatException, IllegalArgumentException{
		if(musicid==null) return null;
		JSONObject ans = new JSONObject();
		HttpClient client = new HttpClient();
		PostMethod postMethod = new PostMethod(baseurl);
		postMethod.addParameter("method","baidu.ting.song.downWeb");
		postMethod.addParameter("songid",musicid+"");
		client.getParams().setParameter(
	            HttpMethodParams.HTTP_CONTENT_CHARSET, "utf-8");
	    // 执行并返回状态
	    int status = client.executeMethod(postMethod);
	    String data = postMethod.getResponseBodyAsString();
	    JSONObject gData = new JSONObject(data);
	    
	    
	    JSONObject songinfo = gData.getJSONObject("songinfo");
	    
	    
	    JSONArray bitrate = gData.getJSONArray("bitrate");
	    JSONObject xx = null;//设置音质
	    int bt = 2;
	    while(bitrate.getJSONObject(bt)==null){
	    	bt--;
	    }
	    if(bitrate.getJSONObject(bt).get("file_link").equals("")){
	    	bt--;
	    }
	    xx = bitrate.getJSONObject(bt);
	    ans.put("url", xx.get("file_link"));
	    
	    musicMapper.delMusic(musicid);
	    musicMapper.addMusic(musicid, songinfo.getString("title"), "active", songinfo.getString("author"), songinfo.getString("album_title"), Integer.parseInt(xx.getString("file_duration")), songinfo.getString("pic_big"), songinfo.getString("lrclink"));
	    
		return xx;
	}
	
	public InputStream getMusicByte(String url) throws IOException{
		HttpClient client = new HttpClient();
		GetMethod getMethod = new GetMethod(url);
		client.getParams().setParameter(
	            HttpMethodParams.HTTP_CONTENT_CHARSET, "utf-8");
	    // 执行并返回状态
	    int status = client.executeMethod(getMethod);
	    InputStream is = getMethod.getResponseBodyAsStream();
	    return is;
	}
}
