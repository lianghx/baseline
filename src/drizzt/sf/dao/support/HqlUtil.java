package drizzt.sf.dao.support;

import java.util.List;


public class HqlUtil {

	/**
	 * field in(?,...,)
	 * 
	 * @param hql
	 * @param field
	 * @param length
	 * @return
	 */
	public static StringBuilder inExp(StringBuilder hql, String field, int length) {
		if (length > 0) {
			hql.append(field).append(" in (");
			for (int i = 0; i < length; i++) {
				hql.append('?').append(',');
			}
			StringUtil.delLastChar(hql, ',').append(')');
		}
		return hql;
	}

	@SuppressWarnings("rawtypes")
	public static Number toNumber(List list) {
		return toNumber(list, 0);
	}

	@SuppressWarnings("rawtypes")
	public static Number toNumber(List list, Number dft) {
		if (list == null || list.get(0) == null) {
			return dft;
		}
		return (Number) list.get(0);
	}
}
