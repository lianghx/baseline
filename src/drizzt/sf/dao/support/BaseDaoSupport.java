package drizzt.sf.dao.support;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Criteria;
import org.hibernate.EntityMode;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projection;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.impl.CriteriaImpl;
import org.hibernate.transform.ResultTransformer;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

/**
 * 基本Dao实现类
 * 
 */
public abstract class BaseDaoSupport<T> extends HibernateDaoSupport implements
		BaseDao<T> {

	protected static int pageSize = 10;
	@SuppressWarnings("unchecked")
	private Class<T> entityClass = (Class<T>) ((ParameterizedType) getClass()
			.getGenericSuperclass()).getActualTypeArguments()[0];

	protected final Log logger = LogFactory.getLog(getClass());

	@SuppressWarnings("unchecked")
	public T read(String uuid) {
		return (T) getSession().createCriteria(entityClass)
				.add(Restrictions.idEq(uuid)).uniqueResult();
	}

	@SuppressWarnings("rawtypes")
	public List find(final String hql, final Object... args) {
		return createQuery(hql, args).list();
	}

	@SuppressWarnings("rawtypes")
	public Object findOne(final String hql, final Object... args) {
		final List list = findLimit(1, hql, args);
		if (list.isEmpty()) {
			return null;
		}
		return list.get(0);
	}

	@SuppressWarnings("rawtypes")
	public List findLimit(int limit, final String hql, final Object... args) {
		return createQuery(hql, args).setMaxResults(limit).list();
	}

	@SuppressWarnings("hiding")
	public <T> T get(final Class<T> clazz, final Serializable id) {
		return (T) getHibernateTemplate().get(clazz, id);
	}

	@SuppressWarnings("hiding")
	public <T> T load(final Class<T> clazz, final Serializable id) {
		return (T) getHibernateTemplate().load(clazz, id);
	}

	// @Override
	public void load(Object vo, Serializable id) {
		getHibernateTemplate().load(vo, id);
	}

	// @Override
	public void load(Object vo) {
		load(vo, getId(vo));
	}

	@SuppressWarnings({ "hiding", "unchecked" })
	public <T> List<T> find(final Class<T> clazz) {
		return createCriteria(clazz).list();
	}

	@SuppressWarnings("rawtypes")
	public void delete(final Object entities) {
		if (entities instanceof Collection) {
			getHibernateTemplate().deleteAll((Collection) entities);
		} else {
			getHibernateTemplate().delete(entities);
		}
	}

	/**
	 * 根据Id删除指定实体类型对象，如果对象不存在，后台会有警告输出，但不抛出异常
	 */
	@SuppressWarnings("rawtypes")
	public void delete(final Class clazz, final Serializable ids) {
		if (ids instanceof Serializable[]) {
			for (Serializable id : (Serializable[]) ids) {
				delteVo(clazz, id);
			}
		} else {
			delteVo(clazz, ids);
		}
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	private void delteVo(final Class clazz, Serializable id) {
		Object o = get(clazz, id);
		if (o != null) {
			getHibernateTemplate().delete(o);
		} else {
			logger.warn(clazz.getName() + "(id=" + id + ")已被删除，请求可能出错");
		}
	}

	@SuppressWarnings("rawtypes")
	public void update(final Object entities) {
		if (entities instanceof Collection) {
			for (Object entity : (Collection) entities) {
				getHibernateTemplate().update(entity);
			}
		} else {
			getHibernateTemplate().update(entities);
		}
	}

	@SuppressWarnings("rawtypes")
	public void insert(final Object entities) {
		if (entities instanceof Collection) {
			for (Object entity : (Collection) entities) {
				save(entity);
			}
		} else {
			save(entities);
		}
	}

	private Serializable save(Object entity) {
		checkId(entity);
		return getHibernateTemplate().save(entity);
	}

	@SuppressWarnings("rawtypes")
	public void insertOrUpdate(final Object entities) {
		if (entities instanceof Collection) {
			for (Object entity : (Collection) entities) {
				saveOrUpdate(entity);
			}
		} else {
			saveOrUpdate(entities);
		}
	}

	private void saveOrUpdate(Object entity) {
		if (checkId(entity)) {
			getHibernateTemplate().save(entity);
		} else {
			getHibernateTemplate().saveOrUpdate(entity);
		}
	}

	private boolean checkId(Object entity) {
		return KeyGenerator.getKeyGenerator(getSessionFactory())
				.checkId(entity);
	}

	public int excute(final String hql, final Object... args) {
		return createQuery(hql, args).executeUpdate();
	}

	@SuppressWarnings({ "hiding", "unchecked" })
	public <T> PageList<T> page(final int pageNo, final int pageSize,
			final Class<T> clazz) {
		return page(pageNo, pageSize, createCriteria(clazz));
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public PageList page(final int pageNo, final int pageSize, Criteria criteria) {
		CriteriaImpl criteriaImpl = (CriteriaImpl) criteria;
		Projection p = criteriaImpl.getProjection();
		List orderList = null;
		Field field = null;
		if (criteriaImpl.iterateOrderings().hasNext()) {
			// 由于hibernate或者sqlserver的原因，带order by子句的统计总数查询会出错，因此去除order条件
			// hibernate的封装及其严格，只能采取反射访问CriteriaImpl的私域
			try {
				field = CriteriaImpl.class.getDeclaredField("orderEntries");
				field.setAccessible(true);
				orderList = (List) field.get(criteriaImpl);
				field.set(criteriaImpl, Collections.EMPTY_LIST);
			} catch (Exception e) {
				throw new MsgException("底层排序出错", e);
			}
		}
		ResultTransformer rt = criteriaImpl.getResultTransformer();
		criteriaImpl.setProjection(Projections.rowCount());
		final PageList pc = new PageList(pageNo, pageSize);
		pc.setTotalCount(HqlUtil.toNumber(criteriaImpl.list()).intValue());
		if (orderList != null && !orderList.isEmpty()) {
			try {
				field.set(criteriaImpl, orderList);
			} catch (Exception e) {
				throw new MsgException("底层排序出错", e);
			}
		}
		criteriaImpl.setProjection(p);
		criteriaImpl.setResultTransformer(rt);
		if (pc.getTotalCount() > pageNo * pageSize) {
			pc.setResult(criteriaImpl.setFirstResult(pc.firstIndex())
					.setMaxResults(pc.pageSize()).list());
		}
		return pc;

	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public PageList page(final int pageNo, final int pageSize, String hql,
			final Object... args) {
		final PageList pc = new PageList(pageNo, pageSize);
		pc.setTotalCount(HqlUtil.toNumber(
				createQuery(createCountHql(hql), args).list()).intValue());
		if (pc.getTotalCount() > pageNo * pageSize) {
			pc.setResult(createQuery(hql, args).setFirstResult(pc.firstIndex())
					.setMaxResults(pc.pageSize()).list());
		}
		return pc;
	}

	private static String createCountHql(String hql) {
		hql = hql.trim();
		final String hql2 = hql.toLowerCase();

		// 由于hibernate或者sqlserver的原因，带order by子句的统计总数查询会出错，因此去除order条件
		final int endIndex = hql2.lastIndexOf("order by");
		if (endIndex > 0) {
			hql = hql.substring(0, endIndex);
		}

		final int fromIndex = hql2.indexOf("from ");
		if (fromIndex == 0) {
			return "select count(*) " + hql;
		} else {
			int a = hql2.indexOf(" from ");
			int b = hql2.indexOf(")from ");
			return "select count(*) "
					+ hql.substring(((a >= 0 && (b < 0 || a < b)) ? a : b) + 1);
		}
	}

	private Query createQuery(final String hql, final Object... args) {
		final Query query = getSession().createQuery(hql);
		for (int i = 0; i < args.length; i++) {
			query.setParameter(i, args[i]);
		}
		return query.setCacheable(args.length < 2);
	}

	// ///////////////////////////////////////////////////////////////////////
	// 新补充的接口实现
	// //////////////////////////////////////////////////////////////////////
	@SuppressWarnings({ "hiding", "unchecked" })
	public <T> List<T> sort(final Class<T> entityClass, final String orderBy,
			final boolean isAsc) {
		return createCriteria(entityClass, orderBy, isAsc).list();
	}

	@SuppressWarnings({ "hiding", "unchecked" })
	public <T> PageList<T> pageSort(final int pageNo, final int pageSize,
			final Class<T> clazz, final String orderBy, final boolean isAsc) {
		Criteria criteria = createCriteria(clazz);
		criteria.addOrder(isAsc ? Order.asc(orderBy) : Order.desc(orderBy));
		return page(pageNo, pageSize, criteria);
	}

	@SuppressWarnings("rawtypes")
	private Criteria createCriteria(Class entityClass, String orderBy,
			boolean isAsc) {
		final Order order = isAsc ? Order.asc(orderBy) : Order.desc(orderBy);
		return createCriteria(entityClass).addOrder(order).setCacheable(true);
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public Criteria createCriteria(final Class entityClass) {
		return (Criteria) getHibernateTemplate().execute(
				new HibernateCallback() {
					public Object doInHibernate(Session session)
							throws HibernateException {
						return session.createCriteria(entityClass);
					}
				});
	}

	@SuppressWarnings("hiding")
	private <T> Criteria createCriteria(final Class<T> entityClass,
			final Criterion criterion) {
		return createCriteria(entityClass).add(criterion);
	}

	@SuppressWarnings({ "hiding", "unchecked" })
	public <T> List<T> find(final Class<T> entityClass,
			final String propertyName, final Object value) {
		return createCriteria(entityClass, Restrictions.eq(propertyName, value))
				.list();
	}

	@SuppressWarnings({ "unchecked", "hiding" })
	public <T> PageList<T> page(final int pageNo, final int pageSize,
			final Class<T> clazz, String propertyName, Object value) {
		Criteria criteria = createCriteria(clazz);
		criteria.add(Restrictions.eq(propertyName, value));
		return page(pageNo, pageSize, criteria);
	}

	@SuppressWarnings({ "unchecked", "hiding" })
	public <T> List<T> find(final Class<T> entityClass,
			final String propertyName, final Object value, String orderBy,
			boolean isAsc) {
		return createCriteria(entityClass, orderBy, isAsc,
				Restrictions.eq(propertyName, value)).list();
	}

	@SuppressWarnings("hiding")
	private <T> Criteria createCriteria(final Class<T> entityClass,
			final String orderBy, final boolean isAsc,
			final Criterion criterions) {
		return createCriteria(entityClass, criterions).addOrder(
				isAsc ? Order.asc(orderBy) : Order.desc(orderBy));
	}

	@SuppressWarnings({ "unchecked", "hiding" })
	public <T> PageList<T> page(final int pageNo, final int pageSize,
			final Class<T> clazz, final String propertyName,
			final Object value, final String orderBy, final boolean isAsc) {
		Criteria criteria = createCriteria(clazz);
		criteria.add(Restrictions.eq(propertyName, value));
		criteria.addOrder(isAsc ? Order.asc(orderBy) : Order.desc(orderBy));
		return page(pageNo, pageSize, criteria);
	}

	@SuppressWarnings({ "unchecked", "hiding" })
	public <T> T findOne(final Class<T> entityClass, final String propertyName,
			final Object value) {
		final List<T> list = createCriteria(entityClass,
				Restrictions.eq(propertyName, value)).setMaxResults(1).list();
		if (list.size() == 0) {// 未找到
			return null;
		}
		return list.get(0);// 找到返回第一条记录
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void clear() {
		getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session session) {
				session.flush();
				session.clear();
				return null;
			}
		});
	}

	@SuppressWarnings("rawtypes")
	public long newId(Class clazz) {
		return KeyGenerator.getKeyGenerator(getSessionFactory()).newId(clazz);
	}

	public Serializable getId(final Object entity) {
		return getSessionFactory().getClassMetadata(entity.getClass())
				.getIdentifier(entity, EntityMode.POJO);
	}

	@SuppressWarnings("rawtypes")
	public String getIdName(final Class clazz) {
		return getSessionFactory().getClassMetadata(clazz)
				.getIdentifierPropertyName();
	}

	public void setId(Object entity, Serializable id) {
		getSessionFactory().getClassMetadata(entity.getClass()).setIdentifier(
				entity, id, EntityMode.POJO);
	}
}
