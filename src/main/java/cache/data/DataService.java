package cache.data;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DataService {

	@Autowired
	DataDao dataDao;
	
	public int redisKey(int key, int row, int col) {
		return dataDao.redisKey(key, row, col);
	}
	
	public int redis(int row, int col) {
		return dataDao.redis(row, col);
	}
}
