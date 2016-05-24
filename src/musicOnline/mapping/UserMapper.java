package musicOnline.mapping;

import java.util.List;

import musicOnline.data.User;

public interface UserMapper {
	
	User findById(int id);
	List<User> findAll();
	User findByName(String username);
	int register(User user);
}
