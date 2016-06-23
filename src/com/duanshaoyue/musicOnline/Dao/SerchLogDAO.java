package com.duanshaoyue.musicOnline.Dao;

import com.duanshaoyue.musicOnline.data.SerchLog;

public interface SerchLogDAO {
	SerchLog findByContent(String content);
	int addContent(String content);
}
