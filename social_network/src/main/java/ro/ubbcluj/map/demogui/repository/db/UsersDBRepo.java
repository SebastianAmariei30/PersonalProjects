package ro.ubbcluj.map.demogui.repository.db;

import ro.ubbcluj.map.demogui.domain.Utilizator;
import ro.ubbcluj.map.demogui.domain.validators.Validator;
import ro.ubbcluj.map.demogui.repository.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UsersDBRepo implements Repository<Long, Utilizator> {
    private final Validator<Utilizator> validator;
    Connection connection;

    public UsersDBRepo(String url, String username, String password,Validator<Utilizator> validator) {
        this.validator=validator;
        try{
            this.connection= DriverManager.getConnection(url,username,password);}
        catch (SQLException e){
            throw new IllegalArgumentException(e);
        }
    }

    @Override
    public Optional<Utilizator> findOne(Long aLong) {
        if (aLong==null)
            throw new IllegalArgumentException("id must be not null");
        List<Utilizator> list;
        String insertQuery = "select * from users where id=?";
        try{
            PreparedStatement statement= connection.prepareStatement(insertQuery);
            statement.setInt(1, Math.toIntExact(aLong));
            ResultSet resultSet=statement.executeQuery();
            list=extractUtilizator(resultSet);
            if(list.isEmpty())
                throw new IllegalArgumentException("id inexistent");
        } catch (SQLException e){
            throw new IllegalArgumentException(e);
        }
        return Optional.of(list.get(0));
    }

    List<Utilizator> extractUtilizator(ResultSet resultSet) throws SQLException {
        List<Utilizator> list= new ArrayList<>();
        while (resultSet.next()){
            int id=resultSet.getInt("id");
            String prenume=resultSet.getString("first_name");
            String nume=resultSet.getString("last_name");
            String username=resultSet.getString("username");
            String password=resultSet.getString("password");
            Utilizator utilizator = new Utilizator(prenume,nume,username,password);
            utilizator.setId((long) id);
            list.add(utilizator);
        }
        return list;
    }

    @Override
    public Iterable<Utilizator> findAll() {
        List<Utilizator> list;
        try(
        PreparedStatement statement= connection.prepareStatement("select * from users");
        ResultSet resultSet=statement.executeQuery();
        ){
            list=extractUtilizator(resultSet);
        } catch (SQLException e){
            throw new IllegalArgumentException(e);
        }
        return list;
    }

    @Override
    public Optional<Utilizator> save(Utilizator entity) {
        String insertQuery = "INSERT INTO users (first_name, last_name,username,password) VALUES (?, ?, ?, ?)";
        validator.validate(entity);
        try{
            PreparedStatement statement= connection.prepareStatement(insertQuery);
            statement.setString(1,entity.getFirstName());
            statement.setString(2,entity.getLastName());
            statement.setString(3,entity.getUsername());
            statement.setString(4,entity.getPassword());
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
    public Optional<Utilizator> delete(Long aLong) {
        if(aLong==null)
            throw new IllegalArgumentException("Id invalid");
        Utilizator utilizator = null;
        if(this.findOne(aLong).isPresent())
            utilizator= this.findOne(aLong).get();
        String deleteQuery="DELETE FROM users WHERE id=?";
        try{
            PreparedStatement statement= connection.prepareStatement(deleteQuery);
            statement.setInt(1,Math.toIntExact(aLong));
            int rowCount=statement.executeUpdate();
            if(rowCount>0){
                return Optional.empty();
            }
        }
        catch (SQLException e){
            throw new IllegalArgumentException(e);
        }
        return Optional.ofNullable(utilizator);
    }

    @Override
    public Optional<Utilizator> update(Utilizator entity) {
        if(entity == null)
            throw new IllegalArgumentException("entity must be not null!");
        validator.validate(entity);
        this.findOne(entity.getId());
        String updateQuery="UPDATE users SET first_name=?,last_name=? WHERE id=?";
        try{
            PreparedStatement statement= connection.prepareStatement(updateQuery);
            statement.setString(1,entity.getFirstName());
            statement.setString(2,entity.getLastName());
            statement.setInt(3,Math.toIntExact(entity.getId()));
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
