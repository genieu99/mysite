package com.poscodx.mysite.vo;

public class PageVo {
	private static int pageBlock = 5;
	private static int perPage = 4;
	private int currentPage;
	private int startPage;
	private int endPage;
	private int maxPage;
	private int index;
	private boolean previousTab;
	private boolean nextTab;
	
	public static int getPageBlock() {
		return pageBlock;
	}
	public static void setPageBlock(int pageBlock) {
		PageVo.pageBlock = pageBlock;
	}
	public static int getPerPage() {
		return perPage;
	}
	public static void setPerPage(int perPage) {
		PageVo.perPage = perPage;
	}
	public int getCurrentPage() {
		return currentPage;
	}
	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}
	public int getStartPage() {
		return startPage;
	}
	public void setStartPage(int startPage) {
		this.startPage = startPage;
	}
	public int getEndPage() {
		return endPage;
	}
	public void setEndPage(int endPage) {
		this.endPage = endPage;
	}
	public int getMaxPage() {
		return maxPage;
	}
	public void setMaxPage(int maxPage) {
		this.maxPage = maxPage;
	}
	public int getIndex() {
		return index;
	}
	public void setIndex(int index) {
		this.index = index;
	}
	public boolean isPreviousTab() {
		return previousTab;
	}
	public void setPreviousTab(boolean previousTab) {
		this.previousTab = previousTab;
	}
	public boolean isNextTab() {
		return nextTab;
	}
	public void setNextTab(boolean nextTab) {
		this.nextTab = nextTab;
	}
	@Override
	public String toString() {
		return "PagingVo [currentPage=" + currentPage + ", startPage=" + startPage
				+ ", endPage=" + endPage + ", maxPage=" + maxPage + "]";
	}
}
