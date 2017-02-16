package cache.data;

import org.springframework.stereotype.Repository;

import cache.annotation.SwiftCache;

@Repository
public class DataDao {

	@SwiftCache(expire=1200)
	public int redisKey(int key, int row, int col) {
		float[][] datas = redisCreateArrays(key, row, col);
		return datas.length;
	}
	
	@SwiftCache(expire=60)
	public int redis(int row, int col) {
		float[][] datas = createArrays(row, col);
		return datas.length;
	}
	
	/**
	 *  创建二维数据，模拟从数据库取数据的过程
	 */
	public static float[][] redisCreateArrays(int key, int row, int col) {
		float[][] result = new float[row][col];
		for (int i = 0; i < row; i++) {
			for (int j = 0; j < col; j++) {
				key++;
				result[i][j] = key;
			}
		}
		return result;
	}
	
	/**
	 *  创建二维数据，模拟从数据库取数据的过程
	 */
	public static float[][] createArrays(int row, int col) {
		float[][] result = new float[row][col];
		int temp = 0;
		for (int i = 0; i < row; i++) {
			for (int j = 0; j < col; j++) {
				temp++;
				result[i][j] = temp;
			}
		}
		return result;
	}
}
