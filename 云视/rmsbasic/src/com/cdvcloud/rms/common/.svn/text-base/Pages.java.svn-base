package com.cdvcloud.rms.common;



public class Pages implements java.io.Serializable{
	
	private static final long serialVersionUID = 1L;
	
	/**每页显示多少条记录**/
	private Integer pageNum=10;
	/** 总的记录数**/
	private Long totalRecord;
	/** 当前第几页**/
	private Integer currentPage=1;
	
	/**结果集 **/
	private Object results;
	public Pages(int pageNum, Long totalRecord, int currentPage,Object results) {
		this.pageNum = pageNum;
		this.totalRecord = totalRecord;
		this.currentPage = currentPage;
		this.results = results;
	}
	public Pages(Object results) {
		this.results = results;
	}
	public Pages() {
	}
	public Integer getPageNum() {
		return pageNum;
	}
	public void setPageNum(Integer pageNum) {
		this.pageNum = pageNum;
	}
	public Long getTotalRecord() {
		return totalRecord;
	}
	public void setTotalRecord(Long totalRecord) {
		this.totalRecord = totalRecord;
	}
	public Integer getCurrentPage() {
		return currentPage;
	}
	public void setCurrentPage(Integer currentPage) {
		this.currentPage = currentPage;
	}
	public Object getResults() {
		return results;
	}
	public void setResults(Object results) {
		this.results = results;
	}
	@Override
	public String toString() {
		return "Pages [pageNum=" + pageNum + ", totalRecord=" + totalRecord + ", currentPage=" + currentPage + ", results=" + results + "]";
	}
	
	
}
