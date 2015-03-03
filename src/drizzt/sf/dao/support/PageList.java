package drizzt.sf.dao.support;

import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public class PageList<T> implements Iterable<T> {
	private final int pageNo;
	private final int pageSize;
	private int totalCount;
	@SuppressWarnings("unchecked")
	private List<T> list = Collections.EMPTY_LIST;

	/**
	 * 唯一的构造函数
	 * 
	 * @param pageIndex:页数序号，从0开始
	 * @param pageSize:页数大小
	 */
	public PageList(int pageNo, int pageSize) {
		this.pageNo = pageNo;
		this.pageSize = pageSize;
	}

	/**
	 * 本页第一条记录在数据库中的序号
	 * 
	 * @return
	 */
	public int firstIndex() {
		return pageSize * pageNo;
	}

	public int pageNo() {
		return pageNo;
	}

	public int pageSize() {
		return pageSize;
	}

	public int getPageSize() {
		return pageSize;
	}

	public int size() {
		return list.size();
	}

	public void setResult(List<T> list) {
		this.list = list;
	}

	public Iterator<T> iterator() {
		return list.iterator();
	}

	public int getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
	}

	public int getPageCount() {
		return totalCount / pageSize + (totalCount % pageSize == 0 ? 0 : 1);
	}

	public boolean hasData() {
		return totalCount > (pageNo - 1) * pageSize;
	}

	public List<T> getList() {
		return list;
	}
	
	public boolean hasNext(){
		return pageNo() < getPageCount() - 1;
	}
}
