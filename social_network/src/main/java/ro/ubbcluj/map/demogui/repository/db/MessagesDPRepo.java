package ro.ubbcluj.map.demogui.repository.db;

import ro.ubbcluj.map.demogui.domain.Message;
import ro.ubbcluj.map.demogui.domain.Prietenie;
import ro.ubbcluj.map.demogui.domain.Tuple;
import ro.ubbcluj.map.demogui.domain.Utilizator;
import ro.ubbcluj.map.demogui.domain.validators.Validator;
import ro.ubbcluj.map.demogui.repository.Repository;
import ro.ubbcluj.map.demogui.utils.controller.MessageAlert;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.*;

public class MessagesDPRepo implements Repository<Long, Message> {
    private final Validator<Message> validator;
    private final Connection connection;

    public MessagesDPRepo(String url, String username, String password, Validator<Message> validator) {
        this.validator = validator;
        try{
            this.connection= DriverManager.getConnection(url,username,password);}
        catch (SQLException e){
            throw new IllegalArgumentException(e);
        }
    }

    @Override
    public Optional<Message> findOne(Long aLong) {
        if (aLong==null)
            throw new IllegalArgumentException("id must be not null");
        List<Message> list;
        String insertQuery = "select * from message where id=?";
        String insertQuery1 = "select * from conv where mid=?";
        try{
            PreparedStatement statement= connection.prepareStatement(insertQuery);
            statement.setInt(1, Math.toIntExact(aLong));
            ResultSet resultSet=statement.executeQuery();
            PreparedStatement statement1= connection.prepareStatement(insertQuery1,ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            statement1.setInt(1, Math.toIntExact(aLong));
            ResultSet resultSet1=statement1.executeQuery();
            list=extractMesaj(resultSet,resultSet1);
            if(list.isEmpty())
                throw new IllegalArgumentException("id inexistent");
        } catch (SQLException e){
            throw new IllegalArgumentException(e);
        }
        return Optional.of(list.get(0));
    }
    private List<Message> extractMesaj(ResultSet resultSet,ResultSet resultSet1) throws SQLException {
        List<Message> list= new ArrayList<>();
        while (resultSet.next()){
            int id=resultSet.getInt("id");
            int from = 0;
            List<Long>to =new ArrayList<>();
            resultSet1.beforeFirst();
            while(resultSet1.next()){
                int id1=resultSet1.getInt("mid");
                System.out.println(id1);
                System.out.println(id);
                if(id1==id){
                    to.add((long) resultSet1.getInt("to"));
                    from=resultSet1.getInt("from");    
                }
            }
            String mesaj=resultSet.getString("message");
            int reply=resultSet.getInt("reply");
            Timestamp timestamp =resultSet.getTimestamp("date");
            LocalDateTime date=timestamp.toLocalDateTime();
            Message message=new Message((long)from,to,mesaj,date);
            message.setReply((long) reply);message.setId((long)id);
            list.add(message);
            System.out.println(message);
        }
        return list;
    }

    @Override
    public Iterable<Message> findAll() {
        List<Message> list;
        try(
            PreparedStatement statement= connection.prepareStatement("select * from message");
            ResultSet resultSet=statement.executeQuery();
            PreparedStatement statement1=connection.prepareStatement("select * from conv",ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            ResultSet resultSet1=statement1.executeQuery();
        ){
            list=extractMesaj(resultSet,resultSet1);
        } catch (SQLException e){
            throw new IllegalArgumentException(e);
        }
        return list;
    }

    @Override
    public Optional<Message> save(Message entity) {
        String insertQuery = "INSERT INTO message (message,reply,date) VALUES (?, ?, ?)";
        String insertQuery1= "INSERT INTO conv (\"from\",\"to\",mid) VALUES(?, ?, ?)";
        String insertQuery2= "SELECT currval('message_id_seq');";
        validator.validate(entity);
        this.findAll().forEach(x->{
            if(x.getReply().equals(entity.getReply())&& Objects.equals(x.getFrom(), entity.getFrom())&&entity.getReply()!=-1)
                throw new IllegalArgumentException("Ai raspuns deja la acest mesaj");
        });
        try{
            PreparedStatement statement= connection.prepareStatement(insertQuery);
            statement.setInt(1,entity.getFrom().intValue());
            statement.setString(1,entity.getMessage());
            if(entity.getReply()!=null)
                statement.setInt(2,entity.getReply().intValue());
            else
                statement.setInt(2,-1);
            statement.setTimestamp(3,Timestamp.valueOf(entity.getData()));
            int rowCount=statement.executeUpdate();
            if(rowCount>0){
                PreparedStatement statement1= connection.prepareStatement(insertQuery2);
                ResultSet resultSet1=statement1.executeQuery();
                long id = -1;
                while(resultSet1.next()){
                    id=resultSet1.getInt("currval");
                }
                long finalid=id;
                entity.getTo().forEach(x->{
                    try {
                        PreparedStatement statement2=connection.prepareStatement(insertQuery1);
                        statement2.setInt(1,entity.getFrom().intValue());
                        statement2.setInt(2,x.intValue());
                        statement2.setInt(3,Math.toIntExact(finalid));
                        statement2.executeUpdate();
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                });
                return Optional.empty();
            }
        }
        catch (SQLException e){
            throw new IllegalArgumentException(e);
        }
        return Optional.of(entity);
    }

    @Override
    public Optional<Message> delete(Long aLong) {
        if(aLong==null)
            throw new IllegalArgumentException("Id invalid");
        Message message = null;
        if(this.findOne(aLong).isPresent())
            message= this.findOne(aLong).get();
        String deleteQuery="DELETE FROM message WHERE id=?";
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
        return Optional.ofNullable(message);
    }

    @Override
    public Optional<Message> update(Message entity) {
        if(entity == null)
            throw new IllegalArgumentException("entity must be not null!");
        validator.validate(entity);
        this.findOne(entity.getId());
        String updateQuery="UPDATE message SET message=?,reply=? WHERE id=?";
        try{
            PreparedStatement statement= connection.prepareStatement(updateQuery);
            statement.setString(1,entity.getMessage());
            statement.setInt(2,entity.getReply().intValue());
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
