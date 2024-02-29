package ro.ubbcluj.map.demogui.repository.db;

import ro.ubbcluj.map.demogui.domain.Prietenie;
import ro.ubbcluj.map.demogui.domain.Tuple;
import ro.ubbcluj.map.demogui.domain.validators.Validator;
import ro.ubbcluj.map.demogui.repository.Repository;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class FriendshipsDPRepo implements Repository<Tuple<Long,Long>, Prietenie> {
    private final Validator<Prietenie> validator;
    Connection connection;

    public FriendshipsDPRepo(String url, String username, String password, Validator<Prietenie> validator) {
        this.validator = validator;
        try{
            this.connection= DriverManager.getConnection(url,username,password);}
        catch (SQLException e){
            throw new IllegalArgumentException(e);
        }
    }
    List<Prietenie> extractPrietenie(ResultSet resultSet) throws SQLException {
        List<Prietenie> list= new ArrayList<>();
        while (resultSet.next()){
            int id1=resultSet.getInt("id1");
            int id2=resultSet.getInt("id2");
            Timestamp timestamp =resultSet.getTimestamp("date");
            LocalDateTime date=timestamp.toLocalDateTime();
            String status=resultSet.getString("status");
            Prietenie prietenie=new Prietenie((long) id1,(long)id2,date,status);
            list.add(prietenie);
        }
        return list;
    }
    @Override
    public Optional<Prietenie> findOne(Tuple<Long, Long> longLongTuple) {
        if(longLongTuple.getLeft()==null || longLongTuple.getRight()==null)
            throw new IllegalArgumentException("id must be not null");
        List<Prietenie> list;
        String insertQuery = "select * from friendships where id1=? and id2=?";
        try{
            PreparedStatement statement= connection.prepareStatement(insertQuery);
            statement.setInt(1, Math.toIntExact(longLongTuple.getLeft()));
            statement.setInt(2,Math.toIntExact(longLongTuple.getRight()));
            ResultSet resultSet=statement.executeQuery();
            list=extractPrietenie(resultSet);
            if(list.isEmpty())
                return Optional.empty();
        } catch (SQLException e){
            throw new IllegalArgumentException(e);
        }
        return Optional.of(list.get(0));
    }

    @Override
    public Iterable<Prietenie> findAll() {
        List<Prietenie> list;
        try(
            PreparedStatement statement= connection.prepareStatement("select * from friendships");
            ResultSet resultSet=statement.executeQuery()
        ){
            list=extractPrietenie(resultSet);
        } catch (SQLException e){
            throw new IllegalArgumentException(e);
        }
        return list;
    }

    @Override
    public Optional<Prietenie> save(Prietenie entity) {
        String insertQuery = "INSERT INTO friendships (id1, id2,date,status) VALUES (?, ?, ?,?)";
        validator.validate(entity);

        if(this.findOne(new Tuple<>(entity.getId().getRight(), entity.getId().getLeft())).isPresent())
            throw new IllegalArgumentException("prietenie existenta");
        try{
            PreparedStatement statement= connection.prepareStatement(insertQuery);
            statement.setInt(1,entity.getId().getLeft().intValue());
            statement.setInt(2,entity.getId().getRight().intValue());
            statement.setTimestamp(3,Timestamp.valueOf(entity.getDate()));
            statement.setString(4,entity.getStatus());
            int rowCount=statement.executeUpdate();
            if(rowCount>0){
                return Optional.empty();
            }
        }
        catch (SQLException e){
            throw new IllegalArgumentException(e);
        }
        return Optional.of(entity);

    }

    @Override
    public Optional<Prietenie> delete(Tuple<Long, Long> longLongTuple) {
        if(longLongTuple.getLeft()==null||longLongTuple.getRight()==null)
            throw new IllegalArgumentException("Id invalid");
        Prietenie prietenie = null;
        if(this.findOne(longLongTuple).isPresent())
            prietenie= this.findOne(longLongTuple).get();
        String deleteQuery="DELETE FROM friendships WHERE id1=? and id2=?";
        try{
            PreparedStatement statement= connection.prepareStatement(deleteQuery);
            statement.setInt(1,Math.toIntExact(longLongTuple.getLeft()));
            statement.setInt(2,Math.toIntExact(longLongTuple.getRight()));
            int rowCount=statement.executeUpdate();
            if(rowCount>0){
                return Optional.empty();
            }
        }
        catch (SQLException e){
            throw new IllegalArgumentException(e);
        }
        return Optional.ofNullable(prietenie);
    }

    @Override
    public Optional<Prietenie> update(Prietenie entity) {
        if(entity == null)
            throw new IllegalArgumentException("entity must be not null!");
        validator.validate(entity);
        this.findOne(entity.getId());
        String updateQuery="UPDATE friendships SET id1=?,id2=?,date=?,status=? WHERE id1=? and id2=?";
        try{
            PreparedStatement statement= connection.prepareStatement(updateQuery);
            statement.setInt(1,entity.getId().getLeft().intValue());
            statement.setInt(2,entity.getId().getRight().intValue());
            statement.setTimestamp(3,Timestamp.valueOf(entity.getDate()));
            statement.setString(4, entity.getStatus());
            statement.setInt(5,entity.getId().getLeft().intValue());
            statement.setInt(6,entity.getId().getRight().intValue());
            int rowCount=statement.executeUpdate();
            if(rowCount>0){
                return Optional.empty();
            }
        }
        catch (SQLException e){
            throw new IllegalArgumentException(e);
        }
        return Optional.of(entity);
    }
}
