package ro.ubbcluj.map.demogui.repository.db;

import ro.ubbcluj.map.demogui.domain.Prietenie;
import ro.ubbcluj.map.demogui.domain.Tuple;
import ro.ubbcluj.map.demogui.domain.Utilizator;
import ro.ubbcluj.map.demogui.domain.validators.Validator;
import ro.ubbcluj.map.demogui.repository.paging.*;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class FriendshipsDBRepoPaging extends FriendshipsDPRepo implements PagingRepo<Tuple<Long,Long>, Prietenie> {
    public FriendshipsDBRepoPaging(String url, String username, String password, Validator<Prietenie> validator){
    super(url,username,password,validator);
}

    @Override
    public Page<Prietenie> findAll(Pageable pageable) {
        List<Prietenie> list;
        try{
            PreparedStatement statement= connection.prepareStatement("select * from friendships where status='accepted' limit ? offset ?");
            statement.setInt(1, pageable.getPageSize());
            statement.setInt(2, (pageable.getPageNumber() - 1) * pageable.getPageSize());
            ResultSet resultSet=statement.executeQuery();
            list=extractPrietenie(resultSet);
        } catch (SQLException e){
            throw new IllegalArgumentException(e);
        }
        return new PageI<>(pageable,list.stream());
    }
}
