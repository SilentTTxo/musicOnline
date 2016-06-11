package musicOnline.mapping;

import musicOnline.data.SerchLog;

public interface SerchLogMapper {
	SerchLog findByContent(String content);
	int addContent(String content);
}
