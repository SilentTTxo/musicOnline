package musicOnline.mapping;

import java.util.List;

import musicOnline.data.Music;

public interface MusicMapper {
	List<Music> findAll();
	List<Music> findByUserId(Integer userid);
	int addLoveMusic(Integer userid,Integer musicid);
}
