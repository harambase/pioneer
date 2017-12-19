package com.harambase.common;

import java.util.LinkedHashMap;

public class Page {
    public int totalPages = 0;
    public int currentPage = -1;
    public int previousPage = 1;
    public int nextPage = 1;
    public long totalRows = -1L;
    public int pageSize = 20;
    public String lastList;
    public static int sysPageSize = 20;

    public Page() {
    }

    public Page(LinkedHashMap linkedHashMap) {
        this.totalPages = (int) linkedHashMap.get("totalPages");
        this.currentPage = (int) linkedHashMap.get("currentPage");
        this.pageSize = (int) linkedHashMap.get("pageSize");
    }

    public Page(int currentPage, long totalRows, int pageSize) {
        this.currentPage = currentPage;
        this.totalRows = totalRows;
        this.pageSize = pageSize;
        currentPage = currentPage <= 1?1:currentPage;
        this.totalPages = totalRows % (long)pageSize == 0L?(int)(totalRows / (long)pageSize):(int)(totalRows / (long)pageSize + 1L);
        currentPage = currentPage >= this.totalPages?this.totalPages:currentPage;
        this.previousPage = currentPage > 1?currentPage - 1:1;
        this.nextPage = currentPage < this.totalPages?currentPage + 1:this.totalPages;
    }

    public Page(int currentPage, long totalRows) {
        this.currentPage = currentPage;
        this.totalRows = totalRows;
    }

    public int getPageSize() {
        return this.pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getTotalPages() {
        return this.totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

    public int getCurrentPage() {
        return this.currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public int getPreviousPage() {
        return this.previousPage;
    }

    public void setPreviousPage(int previousPage) {
        this.previousPage = previousPage;
    }

    public int getNextPage() {
        return this.nextPage;
    }

    public void setNextPage(int nextPage) {
        this.nextPage = nextPage;
    }

    public long getTotalRows() {
        return this.totalRows;
    }

    public void setTotalRows(long totalRows) {
        this.totalRows = totalRows;
    }

    public static int getModulePageSize(String module_name) {
        return sysPageSize;
    }

    public String getLastList() {
        return this.lastList;
    }

    public void setLastList(String lastList) {
        this.lastList = lastList;
    }

    public int getCurrentIndex(){
        return pageSize * (currentPage - 1);
    }
}
