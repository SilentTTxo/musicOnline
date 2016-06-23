package com.duanshaoyue.musicOnline.Dao;

import java.util.List;

import com.duanshaoyue.musicOnline.data.User;

public interface UserDAO {
	
	User findById(int id);
	List<User> findAll();
	User findByName(String username);
	int updatePassword(int userid,String password);
	int delById(int userid);
	int register(User user);
}
