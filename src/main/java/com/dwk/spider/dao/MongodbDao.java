package com.dwk.spider.dao;

import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import org.apache.commons.collections.MapUtils;

import com.lenovo.supernote.orm.db.MongoDao;
import com.mongodb.DB;

/**
 * No sql mongodb dao.
 * @author: xp
 * @data : 2014-8-28
 * @since : 1.5
 */
@SuppressWarnings({"unchecked", "rawtypes"})
public class MongodbDao {

  private MongoDao mongo;
  
  private static final Map<String, Object> empty_parameter = MapUtils.EMPTY_MAP;
  
  /**
   * Retrieve a single row mapped from the statement key.
   * @param <T> the returned object type.
   * @param statement Unique identifier matching the statement to use.
   * @return Mapped object.
   */
  public <T> T selectOne(String statement) {
    return selectOne(statement, empty_parameter);
  }
  
  /**
   * Retrieve a single row mapped from the statement key and parameter.
   * @param <T> the returned object type.
   * @param statement Unique identifier matching the statement to use.
   * @param parameter A parameter object to pass to the statement.
   * @return Mapped object.
   */
  public <T> T selectOne(String statement, Object parameter) {
    return (T)mongo.selectOne(statement, parameter);
  }
  
  /**
   * Retrieve a single row from query.
   * @param collection Target mongodb collection.
   * @param parameter Query parameter.
   * @return
   */
  public JSONObject selectOneByJSON(String collection, Map parameter) {
    return selectOneByJSON(collection, parameter, empty_parameter);
  }
  
  public JSONObject selectOneByJSON(String collection, Map parameter, Map field) {
    return selectOneByJSON(collection, parameter, field, empty_parameter);
  }
  
  public JSONObject selectOneByJSON(String collection, Map parameter, Map field, Map sort) {
    return mongo.queryByJson(collection, parameter, field, sort);
  }
  
  /**
   * Retrieve a list of mapped objects from the statement key.
   * @param <T> the returned list element type.
   * @param statement Unique identifier matching the statement to use.
   * @return List of mapped object.
   */
  public <T> List<T> selectList(String statement) {
    return selectList(statement, empty_parameter, 0, 0);
  }
  
  /**
   * Retrieve a list of mapped objects from the statement key and parameter.
   * @param <T> the returned list element type.
   * @param statement Unique identifier matching the statement to use.
   * @param parameter A parameter object to pass to the statement.
   * @return List of mapped object.
   */
  public <T> List<T> selectList(String statement, Object parameter) {
    return selectList(statement, parameter, 0, 0);
  }
  
  /**
   * Retrieve a list of mapped objects from the statement key and parameter.
   * @param <T> the returned list element type.
   * @param statement Unique identifier matching the statement to use.
   * @param limit Limit mapped objects.
   * @param skip Retrieve a list of mapped objects from skip index.
   * @return List of mapped object.
   */
  public <T> List<T> selectList(String statement, Object parameter, Integer limit, Integer skip) {
    return (List<T>)mongo.selectList(statement, parameter, limit, skip);
  }
  
  /**
   * Retivieve a list from query.
   * @param collection Target mongodb collection.
   * @param parameter Query parameter.
   * @return
   */
  public List<JSONObject> selectListByJSON(String collection, Map parameter) {
    return selectListByJSON(collection, parameter, empty_parameter);
  }
  
  public List<JSONObject> selectListByJSON(String collection, Map parameter, Map field) {
    return selectListByJSON(collection, parameter, field, empty_parameter);
  }
  
  public List<JSONObject> selectListByJSON(String collection, Map parameter, Map field, Map sort) {
    return selectListByJSON(collection, parameter, field, sort, 0, 0);
  }
  
  public List<JSONObject> selectListByJSON(String collection, Map parameter, Map field, Map sort, int skip, int limit) {
    return mongo.queryListByJson(collection, parameter, field, sort, skip, limit);
  }
  
  /**
   * Count a list of mapped objects from the statement key.
   * @param statement Unique identifier matching the statement to use.
   * @return Count of mapped object.
   */
  public long count(String statement) {
    return count(statement, empty_parameter);
  }
  
  /**
   * Count a list of mapped objects from the statement key and parameter.
   * @param statement Unique identifier matching the statement to use.
   * @param parameter A parameter object to pass to the statement.
   * @return Count of mapped object.
   */
  public long count(String statement, Object parameter) {
    return mongo.count(statement, parameter, 0, 0);
  }
  
  /**
   * Execute an insert statement.
   * @param statement Unique identifier matching the statement to execute.
   * @return String The rows id affected by the insert.
   */
  public String insert(String statement) {
    return insert(statement, empty_parameter);
  }

  /**
   * Execute an insert statement with the given parameter object. 
   * @param statement Unique identifier matching the statement to execute.
   * @param parameter A parameter object to pass to the statement.
   * @return String The rows id affected by the insert.
   */
  public String insert(String statement, Object parameter) {
    return mongo.insert(statement, parameter);
  }
  
  public JSONObject insertByJson(String collection, Map parameter) {
    return mongo.insertByJson(collection, parameter);
  }
  
  /**
   * Execute an insert statement with the given list of parameter object. 
   * @param statement Unique identifier matching the statement to execute.
   * @param list A list parameter object to pass to the statement.
   * @return List<String> The rows id affected by the insert.
   */
  public <T> List<String> insertBatch(String statement, List<T> list) {
    return mongo.insertBatch(statement, list);
  }
  
  /**
   * Execute an update statement. The number of rows affected will be returned.
   * @param statement Unique identifier matching the statement to execute.
   * @return int The number of rows affected by the update.
   */
  public int update(String statement) {
    return update(statement, empty_parameter);
  }
  
  /**
   * Execute an update statement. The number of rows affected will be returned.
   * @param statement Unique identifier matching the statement to execute.
   * @param parameter A parameter object to pass to the statement.
   * @return int The number of rows affected by the update.
   */
  public int update(String statement, Object parameter) {
    return update(statement, parameter, parameter);
  }
  
  /**
   * Execute an update statement. The number of rows affected will be returned.
   * @param statement Unique identifier matching the statement to execute.
   * @param parameter A parameter object to pass to the statement.
   * @param action A parameter object to update.
   * @return int The number of rows affected by the update.
   */
  public int update(String statement, Object parameter, Object action) {
    return mongo.update(statement, parameter, action);
  }

  public int updateByJson(String statement, Map parameter, Map action) {
    return mongo.updateByJson(statement, parameter, action);
  }
  
  /**
   * Execute a delete statement. The number of rows affected will be returned.
   * @param statement Unique identifier matching the statement to execute.
   * @return int The number of rows affected by the delete.
   */
  public int delete(String statement) {
    return delete(statement, empty_parameter);
  }

  /**
   * Execute a delete statement. The number of rows affected will be returned.
   * @param statement Unique identifier matching the statement to execute.
   * @param parameter A parameter object to pass to the statement.
   * @return int The number of rows affected by the delete.
   */
  public int delete(String statement, Object parameter) {
    return mongo.delete(statement, parameter);
  }
  
  public int deleteByJson(String statement, Map parameter) {
    return mongo.deleteByJson(statement, parameter);
  }

  /**
   * Get mongo db
   * @return
   */
  public DB getDB() {
    return mongo.getDB();
  }
  
  public void setMongo(MongoDao mongo) {
    this.mongo = mongo;
  }
  
}
