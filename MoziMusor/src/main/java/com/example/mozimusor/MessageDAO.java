package com.example.mozimusor;

import java.sql.*;
import java.util.ArrayList;

public class MessageDAO {

    private Connection Con;

    public MessageDAO(String url){//"jdbc:mysql://localhost/mozimusor?user=root"
        try {
            Con= DriverManager.getConnection(url);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public ArrayList<Message> getAll(){
        String sql="select * from messages";
        ArrayList<Message> msgList=new ArrayList<Message>();
        try {
            Statement Sta= Con.createStatement();
            ResultSet Res= Sta.executeQuery(sql);
            Res.next();
            if(Res.getRow()==0){
                msgList.add(new Message(0,"","Nincs Üzenet az adatbázisban"));
                return msgList;
            }else{
                int id;
                String name,msg;
                id = Res.getInt("id");
                name = Res.getString("name");
                msg = Res.getString("text");
                msgList.add(new Message(id, name, msg));
                while(Res.next()) {
                    id = Res.getInt("id");
                    name = Res.getString("name");
                    msg = Res.getString("text");
                    msgList.add(new Message(id, name, msg));
                }
                return msgList;
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }


    public String sendMessage(Message msg){
        //INSERT INTO `messages`(`id`, `name`, `text`) VALUES ('[value-1]','[value-2]','[value-3]')

        if(msg.getMessage().length()<10){return "Az üzenet túlrövid! (minimum 10 karakter)";}

        String name=msg.getName();
        if(name==null){name="Guest";}
        String sql="INSERT INTO `messages`(`id`, `name`, `text`) VALUES (null,'"+name+"','"+msg.getMessage()+"')";

        try {
            Statement Sta=Con.createStatement();
            int Res=Sta.executeUpdate(sql);
            return "Üzenet elküldve!";


        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


    }


}
