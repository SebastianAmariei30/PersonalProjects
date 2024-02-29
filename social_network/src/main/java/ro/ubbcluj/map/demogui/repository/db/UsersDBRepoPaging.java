package ro.ubbcluj.map.demogui.repository.db;

import ro.ubbcluj.map.demogui.domain.Utilizator;
import ro.ubbcluj.map.demogui.domain.validators.Validator;
import ro.ubbcluj.map.demogui.repository.paging.*;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class UsersDBRepoPaging extends UsersDBRepo implements UsersPagingRepo<Long, Utilizator> {
    public UsersDBRepoPaging(String url, String username, String password, Validator<Utilizator> validator){
        super(url,username,password,validator);
    }
    @Override
    public Page<Utilizator> findAll(Pageable pageable) {
        List<Utilizator> list;
        try{
                PreparedStatement statement= connection.prepareStatement("select * from users limit ? offset ?");
                statement.setInt(1, pageable.getPageSize());
                statement.setInt(2, (pageable.getPageNumber() - 1) * pageable.getPageSize());
                ResultSet resultSet=statement.executeQuery();
                list=extractUtilizator(resultSet);
        } catch (SQLException e){
            throw new IllegalArgumentException(e);
        }
        return new PageI<>(pageable,list.stream());
    }

    public Page<Utilizator> findAllNFriends(Pageable pageable,Long id){
        List<Utilizator> list;
        try{
            PreparedStatement statement= connection.prepareStatement(
                    """
                            SELECT u.id, u.first_name, u.last_name,u.username,u.password\s
                            FROM users u
                            LEFT JOIN friendships f1 ON u.id = f1.id1 AND f1.id2 = ?
                            LEFT JOIN friendships f2 ON u.id = f2.id2 AND f2.id1 = ?
                            WHERE f1.id1 IS NULL AND f2.id2 IS NULL AND u.id != ?
                            limit ? offset ?;""");
            statement.setInt(1, Math.toIntExact(id));
            statement.setInt(2, Math.toIntExact(id));
            statement.setInt(3, Math.toIntExact(id));
            statement.setInt(4, pageable.getPageSize());
            statement.setInt(5, (pageable.getPageNumber() - 1) * pageable.getPageSize());
            ResultSet resultSet=statement.executeQuery();
            list=extractUtilizator(resultSet);
        } catch (SQLException e){
            throw new IllegalArgumentException(e);
        }
        return new PageI<>(pageable,list.stream());
    }
    public Page<Utilizator> findAllPFriends(Pageable pageable,Long id){
        List<Utilizator> list;
        try{
            PreparedStatement statement= connection.prepareStatement(
                    """
                            SELECT u.id, u.first_name, u.last_name,u.username,u.password\s
                            FROM users u
                            INNER JOIN friendships f1 ON u.id = f1.id2 AND f1.id1 = ?
                            WHERE f1.status='pending'
                            limit ? offset ?;""");
            statement.setInt(1, Math.toIntExact(id));
            statement.setInt(2, pageable.getPageSize());
            statement.setInt(3, (pageable.getPageNumber() - 1) * pageable.getPageSize());
            ResultSet resultSet=statement.executeQuery();
            list=extractUtilizator(resultSet);
        } catch (SQLException e){
            throw new IllegalArgumentException(e);
        }
        return new PageI<>(pageable,list.stream());
    }
}
