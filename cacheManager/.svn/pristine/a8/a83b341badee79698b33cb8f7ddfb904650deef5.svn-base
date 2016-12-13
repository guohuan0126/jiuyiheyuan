package base.pagehelper;

import java.util.List;

/**
 * 对Page<E>结果进行包装
 * <p/>
 * 新增分页的多项属性，主要参考:http://bbs.csdn.net/topics/360010907
 * 
 * @author liuzh/abel533/isea533
 * @version 3.3.0
 * @since 3.2.2 项目地址 : http://git.oschina.net/free/Mybatis_PageHelper
 */
public class PageInfo<T> {
	// 当前页
	private int pageNo;
	// 每页的数量
	private int pageSize;
	// 当前页的数量
	private int size;
	// 由于startRow和endRow不常用，这里说个具体的用法
	// 可以在页面中"显示startRow到endRow 共size条数据"

	// 当前页面第一个元素在数据库中的行号
	private int startRow;
	// 当前页面最后一个元素在数据库中的行号
	private int endRow;
	// 总记录数
	private long totalRecord;
	// 总页数
	private int totalPage;
	// 结果集
	private List<T> results;

	// 第一页
	private int firstPage;
	// 前一页
	private int prePage;
	// 下一页
	private int nextPage;
	// 最后一页
	private int lastPage;

	// 是否为第一页
	private boolean isFirstPage = false;
	// 是否为最后一页
	private boolean isLastPage = false;
	// 是否有前一页
	private boolean hasPreviousPage = false;
	// 是否有下一页
	private boolean hasNextPage = false;
	// 导航页码数
	private int navigatePages;
	// 所有导航页号
	private int[] navigatepageNums;

	public PageInfo() {
	}

	/**
	 * 包装Page对象
	 * 
	 * @param list
	 */
	public PageInfo(List<T> list) {
		this(list, 8);
	}

	/**
	 * 包装Page对象
	 * 
	 * @param list
	 *            page结果
	 * @param navigatePages
	 *            页码数量
	 */
	public PageInfo(List<T> list, int navigatePages) {
		if (list instanceof Page) {
			Page page = (Page) list;
			this.pageNo = page.getPageNum();
			this.pageSize = page.getPageSize();

			this.totalRecord = page.getTotal();
			this.totalPage = page.getPages();
			this.results = page;
			this.size = page.size();
			// 由于结果是>startRow的，所以实际的需要+1
			if (this.size == 0) {
				this.startRow = 0;
				this.endRow = 0;
			} else {
				this.startRow = page.getStartRow() + 1;
				// 计算实际的endRow（最后一页的时候特殊）
				this.endRow = this.startRow - 1 + this.size;
			}
			this.navigatePages = navigatePages;
			// 计算导航页
			calcNavigatepageNums();
			// 计算前后页，第一页，最后一页
			calcPage();
			// 判断页面边界
			judgePageBoudary();
		}
	}

	/**
	 * 计算导航页
	 */
	private void calcNavigatepageNums() {
		// 当总页数小于或等于导航页码数时
		if (totalPage <= navigatePages) {
			navigatepageNums = new int[totalPage];
			for (int i = 0; i < totalPage; i++) {
				navigatepageNums[i] = i + 1;
			}
		} else { // 当总页数大于导航页码数时
			navigatepageNums = new int[navigatePages];
			int startNum = pageNo - navigatePages / 2;
			int endNum = pageNo + navigatePages / 2;

			if (startNum < 1) {
				startNum = 1;
				// (最前navigatePages页
				for (int i = 0; i < navigatePages; i++) {
					navigatepageNums[i] = startNum++;
				}
			} else if (endNum > totalPage) {
				endNum = totalPage;
				// 最后navigatePages页
				for (int i = navigatePages - 1; i >= 0; i--) {
					navigatepageNums[i] = endNum--;
				}
			} else {
				// 所有中间页
				for (int i = 0; i < navigatePages; i++) {
					navigatepageNums[i] = startNum++;
				}
			}
		}
	}

	/**
	 * 计算前后页，第一页，最后一页
	 */
	private void calcPage() {
		if (navigatepageNums != null && navigatepageNums.length > 0) {
			firstPage = navigatepageNums[0];
			lastPage = navigatepageNums[navigatepageNums.length - 1];
			if (pageNo > 1) {
				prePage = pageNo - 1;
			}
			if (pageNo < totalPage) {
				nextPage = pageNo + 1;
			}
		}
	}

	/**
	 * 判定页面边界
	 */
	private void judgePageBoudary() {
		isFirstPage = pageNo == 1;
		isLastPage = pageNo == totalPage;
		hasPreviousPage = pageNo > 1;
		hasNextPage = pageNo < totalPage;
	}

	public int getPageNo() {
		return pageNo;
	}

	public void setPageNo(int pageNo) {
		this.pageNo = pageNo;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}

	public int getStartRow() {
		return startRow;
	}

	public void setStartRow(int startRow) {
		this.startRow = startRow;
	}

	public int getEndRow() {
		return endRow;
	}

	public void setEndRow(int endRow) {
		this.endRow = endRow;
	}

	public long getTotalRecord() {
		return totalRecord;
	}

	public void setTotalRecord(long totalRecord) {
		this.totalRecord = totalRecord;
	}

	public int getTotalPage() {
		return totalPage;
	}

	public void setTotalPage(int totalPage) {
		this.totalPage = totalPage;
	}

	public List<T> getResults() {
		return results;
	}

	public void setResults(List<T> results) {
		this.results = results;
	}

	public int getFirstPage() {
		return firstPage;
	}

	public void setFirstPage(int firstPage) {
		this.firstPage = firstPage;
	}

	public int getPrePage() {
		return prePage;
	}

	public void setPrePage(int prePage) {
		this.prePage = prePage;
	}

	public int getNextPage() {
		return nextPage;
	}

	public void setNextPage(int nextPage) {
		this.nextPage = nextPage;
	}

	public int getLastPage() {
		return lastPage;
	}

	public void setLastPage(int lastPage) {
		this.lastPage = lastPage;
	}

	public boolean isFirstPage() {
		return isFirstPage;
	}

	public void setFirstPage(boolean isFirstPage) {
		this.isFirstPage = isFirstPage;
	}

	public boolean isLastPage() {
		return isLastPage;
	}

	public void setLastPage(boolean isLastPage) {
		this.isLastPage = isLastPage;
	}

	public boolean isHasPreviousPage() {
		return hasPreviousPage;
	}

	public void setHasPreviousPage(boolean hasPreviousPage) {
		this.hasPreviousPage = hasPreviousPage;
	}

	public boolean isHasNextPage() {
		return hasNextPage;
	}

	public void setHasNextPage(boolean hasNextPage) {
		this.hasNextPage = hasNextPage;
	}

	public int getNavigatePages() {
		return navigatePages;
	}

	public void setNavigatePages(int navigatePages) {
		this.navigatePages = navigatePages;
	}

	public int[] getNavigatepageNums() {
		return navigatepageNums;
	}

	public void setNavigatepageNums(int[] navigatepageNums) {
		this.navigatepageNums = navigatepageNums;
	}

	@Override
	public String toString() {
		final StringBuffer sb = new StringBuffer("PageInfo{");
		sb.append("pageNum=").append(pageNo);
		sb.append(", pageSize=").append(pageSize);
		sb.append(", size=").append(size);
		sb.append(", startRow=").append(startRow);
		sb.append(", endRow=").append(endRow);
		sb.append(", total=").append(totalRecord);
		sb.append(", totalPage=").append(totalPage);
		sb.append(", list=").append(results);
		sb.append(", firstPage=").append(firstPage);
		sb.append(", prePage=").append(prePage);
		sb.append(", nextPage=").append(nextPage);
		sb.append(", lastPage=").append(lastPage);
		sb.append(", isFirstPage=").append(isFirstPage);
		sb.append(", isLastPage=").append(isLastPage);
		sb.append(", hasPreviousPage=").append(hasPreviousPage);
		sb.append(", hasNextPage=").append(hasNextPage);
		sb.append(", navigatePages=").append(navigatePages);
		sb.append(", navigatepageNums=");
		if (navigatepageNums == null)
			sb.append("null");
		else {
			sb.append('[');
			for (int i = 0; i < navigatepageNums.length; ++i)
				sb.append(i == 0 ? "" : ", ").append(navigatepageNums[i]);
			sb.append(']');
		}
		sb.append('}');
		return sb.toString();
	}
}
