package musicOnline;

import java.io.IOException;

import javax.annotation.Resource;

import org.apache.commons.httpclient.HttpException;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.stereotype.Component;

import musicOnline.mapping.MusicMapper;
import musicOnline.mapping.SerchLogMapper;

@Component
public abstract class Api {
	@Resource
	public MusicMapper musicMapper;
	@Resource
	public SerchLogMapper serchLogMapper;
	protected String baseurl ;
	public abstract JSONArray findMusic(String name) throws HttpException, IOException, JSONException;
	public abstract String findMusicById(Integer musicid) throws HttpException, IOException, JSONException, NumberFormatException, IllegalArgumentException;
}
