package ro.ubbcluj.map.demogui.repository.paging;

import ro.ubbcluj.map.demogui.domain.Entity;
import ro.ubbcluj.map.demogui.domain.Utilizator;
import ro.ubbcluj.map.demogui.repository.Repository;

import java.util.Optional;

public interface PagingRepo<ID,
        E extends Entity<ID>>
        extends Repository<ID, E> {
    Page<E> findAll(Pageable pageable);
}
