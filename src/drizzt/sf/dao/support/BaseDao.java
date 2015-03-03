package drizzt.sf.dao.support;

import java.io.Serializable;
import java.util.List;

import org.hibernate.Criteria;

/**
 * 基础Dao接口
 * 
 */
public interface BaseDao<T> {

	/**
	 * 删除对象映射数据
	 * <p>
	 * 
	 * @param object
	 *            orm类实例
	 */
//	public void delete(T object);

	/**
	 * 得到对象映射数据
	 * <p>
	 * 
	 * @param clas
	 *            orm类
	 * @param uuid
	 *            主键
	 * @return orm类实例
	 */
	public T read(String uuid);

	/**
	 * 保存新对象映射数据
	 * <p>
	 * 
	 * @param object
	 *            orm类实例
	 */
//	public void save(T object);

	/**
	 * 保存或更新对象映射数据
	 * <p>
	 * 
	 * @param object
	 *            orm类实例
	 */
//	public void saveOrUpdate(T object);

	/**
	 * 更新对象映射数据
	 * <p>
	 * 
	 * @param object
	 *            orm类实例
	 */
//	public void update(T object);
	
	

	/**
	 * 根据实体类和主键 获取实体对象，不存在返回null
	 */
	@SuppressWarnings("hiding")
	public <T> T get(final Class<T> clazz, final Serializable id);

	/**
	 * 根据实体类和主键 获取实体对象，不存在返回null
	 */
	@SuppressWarnings("hiding")
	public <T> T load(final Class<T> clazz, final Serializable id);

	/**
	 * 根据Id将数据库中的记录映射成对象vo
	 */
	public void load(final Object vo, final Serializable id);

	/**
	 * 根据Id将数据库中的记录映射成对象vo
	 */
	public void load(final Object vo);


	
	/**
	 * 更新实体对象
	 * 
	 * @param entities:
	 *            可以是单个实体对象，也可以是多个实体对象的集合（java.util.Collection）
	 */
	public void update(final Object entities);

	/**
	 * 插入实体对象
	 * 
	 * @param entities:
	 *            可以是单个实体对象，也可以是多个实体对象的集合（java.util.Collection）
	 */
	public void insert(final Object entities);

	/**
	 * 插入或更新实体对象
	 * 
	 * @param entities:
	 *            可以是单个实体对象，也可以是多个实体对象的集合（java.util.Collection）
	 */
	public void insertOrUpdate(final Object entities);

	/**
	 * 删除对象
	 * 
	 * @param entities:
	 *            可以是单个实体对象，也可以是多个实体对象的集合（java.util.Collection）
	 */
	public void delete(final Object entities);


	/**
	 * 根据Id删除指定实体类型对象，如果对象不存在，后台会有警告输出，但不抛出异常
	 */
	@SuppressWarnings("rawtypes")
	public void delete(final Class clazz, final Serializable ids);

	/**
	 * 根据HQL语句执行写操作
	 */
	public int excute(final String hql, final Object... args);

	/**
	 * 根据指定实体类型查询所有对象列表
	 */
	@SuppressWarnings("hiding")
	public <T> List<T> find(final Class<T> clazz);

	/**
	 * 根据HQL语句查询符合条件所有对象列表
	 */
	@SuppressWarnings("rawtypes")
	public List find(final String hql, final Object... args);

	/**
	 * 根据HQL语句查询符合条件有限对象列表
	 * 
	 * @param hql
	 * @param args
	 * @return
	 */
	public Object findOne(final String hql, final Object... args);

	/**
	 * 
	 * @param hql
	 * @param args
	 * @param limit
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public List findLimit(int limit, final String hql, final Object... args);

	/**
	 * 分页查询指定实体类型所有对象列表
	 * 
	 * @param <T>
	 * @param pageNo:
	 *            从第0页开始
	 * @param pageSize
	 * @param clazz
	 * @return
	 */
	@SuppressWarnings("hiding")
	public <T> PageList<T> page(final int pageNo, final int pageSize, final Class<T> clazz);

	/**
	 * 分页查询
	 * 
	 * @param pageNo:
	 *            从第0页开始
	 * @param pageSize
	 * @param hql
	 * @param args
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public PageList page(final int pageNo, final int pageSize, final String hql,
			final Object... args);

	/**
	 * 获取全部对象,带排序字段与升降序参数
	 */
	@SuppressWarnings("hiding")
	public <T> List<T> sort(final Class<T> entityClass, final String orderBy,
			final boolean isAsc);

	/**
	 * 分页获取对象,带排序字段与升降序参数
	 */
	@SuppressWarnings("hiding")
	public <T> PageList<T> pageSort(final int pageNo, final int pageSize,
			final Class<T> entityClass, final String orderBy, boolean isAsc);

	/**
	 * 根据属性名和属性值查询对象
	 */
	@SuppressWarnings("hiding")
	public <T> List<T> find(final Class<T> entityClass, final String propertyName,
			final Object value);

	/**
	 * 根据属性名和属性值分页查询对象
	 */
	@SuppressWarnings("hiding")
	public <T> PageList<T> page(final int pageNo, final int pageSize,
			final Class<T> entityClass, final String propertyName, final Object value);

	/**
	 * 根据属性名和属性值查询对象,带排序参数
	 */
	@SuppressWarnings("hiding")
	public <T> List<T> find(final Class<T> entityClass, final String propertyName,
			final Object value, final String orderBy, final boolean isAsc);

	/**
	 * 根据属性名和属性值分页查询对象,带排序参数
	 */
	@SuppressWarnings("hiding")
	public <T> PageList<T> page(final int pageNo, final int pageSize,
			final Class<T> entityClass, final String propertyName, final Object value,
			final String orderBy, final boolean isAsc);

	/**
	 * 根据属性名和属性值仅获取一个对象
	 */
	@SuppressWarnings("hiding")
	public <T> T findOne(final Class<T> entityClass, final String propertyName,
			final Object value);

	/**
	 * 取得对象的主键值,辅助接口
	 */
	public Serializable getId(final Object entity);

	/**
	 * 取得对象的主键名,辅助接口,如果是复合主键或无主键将返回null
	 */
	@SuppressWarnings("rawtypes")
	public String getIdName(final Class clazz);
	
	@SuppressWarnings("rawtypes")
	public Criteria createCriteria(Class entityClass);

	@SuppressWarnings("rawtypes")
	public PageList page(final int pageNo, final int pageSize, Criteria criteria);

	/**
	 * 为对象设置主键
	 * 
	 * @param entity
	 * @param id
	 */
	public void setId(Object entity, Serializable id);

	/**
	 * 清空hibernate缓存
	 */
	public void clear();
}
