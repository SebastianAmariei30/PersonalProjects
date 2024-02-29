package ro.ubbcluj.map.demogui.repository.paging;

public class PageableI implements Pageable{
    private int pageNumber;
    private int pageSize;

    public PageableI(int pageNumber, int pageSize) {
        this.pageNumber = pageNumber;
        this.pageSize = pageSize;
    }

    @Override
    public int getPageNumber() {
        return this.pageNumber;
    }

    @Override
    public int getPageSize() {
        return this.pageSize;
    }
}
