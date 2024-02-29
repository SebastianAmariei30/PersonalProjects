package ro.ubbcluj.map.demogui.repository.paging;

import java.util.stream.Stream;

public class PageI<T> implements Page<T> {
    private Pageable pageable;
    private Stream<T> content;

    public PageI(Pageable pageable, Stream<T> content) {
        this.pageable = pageable;
        this.content = content;
    }

    @Override
    public Pageable getPageable() {
        return this.pageable;
    }

    @Override
    public Pageable nextPageable() {
        return new PageableI(this.pageable.getPageNumber() + 1, this.pageable.getPageSize());
    }

    @Override
    public Stream<T> getContent() {
        return this.content;
    }
}