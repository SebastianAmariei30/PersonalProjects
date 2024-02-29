package ro.ubbcluj.map.demogui.repository.paging;

import ro.ubbcluj.map.demogui.domain.Entity;
import ro.ubbcluj.map.demogui.domain.Utilizator;

public interface UsersPagingRepo<ID, E extends Entity<ID>> extends PagingRepo<ID, E> {
    Page<E> findAllNFriends(Pageable pageable, Long id);
    Page<E> findAllPFriends(Pageable pageable, Long id);
}
