package com.duanshaoyue.musicOnline.service;

import java.io.IOException;

import javax.annotation.Resource;

import org.apache.commons.httpclient.HttpException;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.stereotype.Component;

import com.duanshaoyue.musicOnline.Dao.MusicDAO;
import com.duanshaoyue.musicOnline.Dao.SerchLogDAO;

@Component
public abstract class Api {
	@Resource
	public MusicDAO musicMapper;
	@Resource
	public SerchLogDAO serchLogMapper;
	protected String baseurl ;
	public abstract JSONArray findMusic(String name) throws HttpException, IOException, JSONException;
	public abstract JSONObject findMusicById(Integer musicid) throws HttpException, IOException, JSONException, NumberFormatException, IllegalArgumentException;
}
