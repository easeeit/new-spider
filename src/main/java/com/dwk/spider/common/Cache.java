package com.dwk.spider.common;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;

import com.schooner.MemCached.SchoonerSockIOPool;
import com.whalin.MemCached.MemCachedClient;

public class Cache implements InitializingBean {
	private static final Logger log = LoggerFactory.getLogger(Cache.class);
	
	private String nodeList;
	private String weightList;
	private int initConn;
	private int maxConn;
	private int minConn;
	private String name;

	@Override
	public void afterPropertiesSet() throws Exception {
		init();
	}
	
	private void init() {
		String[] serverlist = nodeList.split(",");

		// Integer[] weights = { 3 };
		SchoonerSockIOPool pool = SchoonerSockIOPool.getInstance(weightList);
		pool.setServers(serverlist);
		// 开始时每个cache服务器的可用连接数
		pool.setInitConn(initConn);
		pool.setMinConn(minConn);
		pool.setMaxConn(maxConn);
		/*
		 * 设置连接池维护线程的睡眠时间 设置为0，维护线程不启动
		 * 维护线程主要通过log输出socket的运行状况，监测连接数目及空闲等待时间等参数以控制连接创建和关闭。
		 */
		pool.setMaintSleep(0);
		// 设置是否使用Nagle算法，因为我们的通讯数据量通常都比较大（相对TCP控制数据）而且要求响应及时，因此该值需要设置为false（默认是true）
		pool.setNagle(false);
		pool.initialize();
		
		if(pool.isInitialized()) {
			log.info("************************************memcache 初始化完成 ["+name+"]***************************");
		} else {
			log.error("************************************memcache 初始化失败 ["+name+"]***************************");
		}
	}

	/**
	 * 得到缓存连接
	 */
	public MemCachedClient getMemCachedClient() {
		return new MemCachedClient(weightList);
	}
	
	/**
	 * 缓存数据过期时间
	 * 
	 * @return
	 */
	public static Date getExpiry(int seconds) {
		Calendar expiry = Calendar.getInstance();
		expiry.add(Calendar.SECOND, seconds);
		return expiry.getTime();
	}
	
	public static long getExpiryLongFromNow(int seconds) {
		Calendar expiry = Calendar.getInstance();
		expiry.add(Calendar.SECOND, seconds);
		return expiry.getTimeInMillis();
	}
	
	public static Date getEndTime(){  
    Calendar todayEnd = Calendar.getInstance();  
    todayEnd.set(Calendar.HOUR, 23);  
    todayEnd.set(Calendar.MINUTE, 59);  
    todayEnd.set(Calendar.SECOND, 59);  
    todayEnd.set(Calendar.MILLISECOND, 999);  
    return todayEnd.getTime();  
}  
	
	/**
	 * 获得当前时间的long值
	 */
	public long getNowLong() {
		return System.currentTimeMillis();
	}

	public boolean set(String k, Serializable v) {
		return getMemCachedClient().set(k, v);
	}

	public boolean set(String k, Serializable v, long time) {
		return getMemCachedClient().set(k, v, new Date(time));
	}
	
	public boolean setToday(String k, Serializable v) {
	  return getMemCachedClient().set(k, v, getEndTime());
	}
	
	public Serializable get(String k) {
		return (Serializable)getMemCachedClient().get(k);
	}

	public boolean delete(String key) {
		return getMemCachedClient().delete(key);
	}

	public void setNodeList(String nodeList) {
		this.nodeList = nodeList;
	}

	public void setWeightList(String weightList) {
		this.weightList = weightList;
	}

	public void setInitConn(int initConn) {
		this.initConn = initConn;
	}

	public void setMaxConn(int maxConn) {
		this.maxConn = maxConn;
	}

	public void setMinConn(int minConn) {
		this.minConn = minConn;
	}

	public void setName(String name) {
		this.name = name;
	}

}
