package drizzt.sf.dao.support;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.EntityMode;
import org.hibernate.SessionFactory;
import org.hibernate.metadata.ClassMetadata;

public class KeyGenerator {

	private static final Byte INTEGER = 0;// 表示主键为整型，包含原型
	private static final Byte LONG = 1;// 表示主键为长整型，包含原型
	private static final Byte OTHER = -1;// 表示主键为其他类型
	private final SessionFactory factory;
	@SuppressWarnings("rawtypes")
	private final Map<Class, Long> idMap = new HashMap<Class, Long>();
	@SuppressWarnings("rawtypes")
	private final Map<Class, Byte> idFlagMap = new HashMap<Class, Byte>();// 缓存Id类型

	@SuppressWarnings({ "unchecked", "rawtypes" })
	private static Map<SessionFactory, KeyGenerator> genrtMap = new HashMap();

	public static KeyGenerator getKeyGenerator(SessionFactory factory) {
		KeyGenerator keyGenrator = genrtMap.get(factory);
		if (keyGenrator == null) {
			keyGenrator = new KeyGenerator(factory);
			genrtMap.put(factory, keyGenrator);
		}
		return keyGenrator;
	}

	private KeyGenerator(SessionFactory factory) {
		this.factory = factory;
	}

	/**
	 * 检查该实体对象是否需要设置主键，<b>全部</b>满足以下条件，该方法才会为对象生成主键： <br>
	 * &nbsp;&nbsp;1.主键的类型为整型或者长整型（包含相应的原型） <br>
	 * &nbsp;&nbsp;2.该对象的Id值为Null或者小于1<br>
	 * 
	 * @param entity
	 * @return 是否为实体设置过新的Id
	 */
	public boolean checkId(Object entity) {
		Byte idFlag = getIdFlag(entity);
		if (idFlag != OTHER) {
			long oldId = getId(entity, idFlag);
			Long id = newId(entity.getClass(), oldId);
			if (oldId < 0) {
				if (idFlag == INTEGER) {
					setId(entity, id.intValue());
				} else {
					setId(entity, id);
				}
				return true;
			}
		}
		return false;
	}

	private void setId(Object entity, Serializable id) {
		getClassMeta(entity).setIdentifier(entity, id, EntityMode.POJO);
	}

	private ClassMetadata getClassMeta(Object entity) {
		return factory.getClassMetadata(entity.getClass());
	}

	private long getId(Object entity, Byte idType) {
		Serializable id = getClassMeta(entity).getIdentifier(entity, EntityMode.POJO);
		if (id == null) {
			return -1;
		}
		if (idType == INTEGER) {
			return ((Integer) id).longValue();
		} else {
			return (Long) id;
		}
	}

	/**
	 * 生成主键
	 * 
	 * @param session
	 * @param clazz
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public Long newId(final Class clazz) {
		return newId(clazz, -1);
	}

	/**
	 * 以clazz为主键缓存当前表中最大的主键，供生成新的主键使用。 1.如果oldId小于1需要新生成主键否则不需要，
	 * 2.更新缓存中当前表中的最大的主键值(oldId大于1而且小于缓存中最大主键就不用更新缓存)
	 * 
	 * @param session
	 * @param clazz:
	 *            实体类型
	 * @param oldId:
	 *            原有主键
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	private synchronized Long newId(final Class clazz, long oldId) {
		Long keyId = idMap.get(clazz);
		if (keyId == null) {
			keyId = getMaxId(clazz);
		}
		if (oldId < 0) {
			// 没有主键，原主键加1，缓存后返回；
			keyId = keyId + 1;
			idMap.put(clazz, keyId);
			return keyId;
		}

		// 有主键
		if (oldId > keyId) {
			// 当前主键比缓存中的主键大，更新缓存
			idMap.put(clazz, oldId);
		}
		return oldId;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	private Long getMaxId(Class clazz) {
		StringBuilder hql = new StringBuilder();
		hql.append("select max(t.").append(
				factory.getClassMetadata(clazz).getIdentifierPropertyName());
		hql.append(") from ").append(clazz.getName()).append(' ').append('t');
		List<Number> list = factory.getCurrentSession().createQuery(hql.toString()).list();
		if (list.isEmpty() || list.get(0) == null) {
			return Long.valueOf(0);
		}
		return list.get(0).longValue();
	}

	/**
	 * 判断主键的类型，已作缓存
	 * 
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	private Byte getIdFlag(Object entity) {
		Class clazz = entity.getClass();
		Byte setIdFlag = idFlagMap.get(clazz);
		if (setIdFlag != null) {
			return setIdFlag;
		}
		Class idType = factory.getClassMetadata(entity.getClass()).getIdentifierType()
				.getReturnedClass();
		if (idType == Integer.class || idType == Integer.TYPE) {
			return setFlag(clazz, INTEGER);
		} else if (idType == Long.class || idType == Long.TYPE) {
			return setFlag(clazz, LONG);
		}
		return setFlag(clazz, OTHER);
	}

	@SuppressWarnings("rawtypes")
	private Byte setFlag(Class clazz, Byte flag) {
		idFlagMap.put(clazz, flag);
		return flag;
	}
}
