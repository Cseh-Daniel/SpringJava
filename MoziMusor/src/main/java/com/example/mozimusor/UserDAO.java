package com.example.mozimusor;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.*;
import java.util.ArrayList;

public class UserDAO {


    private Connection Con;

    public UserDAO(String url){//"jdbc:mysql://localhost/mozimusor?user=root"
        try {
            Con= DriverManager.getConnection(url);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }


    public boolean isNameSet(String name){

        String sql="select name from user where name='"+name+"'";

        try {
            Statement Sta = Con.createStatement();
            ResultSet Res = Sta.executeQuery(sql);
            Res.next();
            if(Res.getRow()>0){ return true;}else{return false;}
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean isEmailSet(String email){

        String sql="select email from user where email='"+email+"'";

        try {
            Statement Sta = Con.createStatement();
            ResultSet Res = Sta.executeQuery(sql);
            Res.next();
            if(Res.getRow()>0){ return true;}else{return false;}
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean checkPass(String pw,String email){
                  //select password from user where email='Teszt@teszt.com'
        String sql="select password from user where email='"+email+"'";
        //String sql="select password from user where email='Teszt@teszt.com'";

        try {
            MessageDigest pwHash = MessageDigest.getInstance("SHA-256");
            pwHash.update(pw.getBytes());
            pw=new String(pwHash.digest());

        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }



        try {
            Statement Sta = Con.createStatement();
            ResultSet Res = Sta.executeQuery(sql);
            Res.next();
            String qpass;
            if(Res.getRow()==0){return false;}
            qpass=Res.getString("password");
            //System.out.println(qpass+"\n"+pw);



            if(qpass.equals(pw)){
                System.out.println("Good Password");
                return true;
            } else {
                return false;}

        } catch (SQLException e) {
            System.out.println( e.getMessage());
            throw new RuntimeException(e);
        }


    }

    public String registerUser(User user){
        String pw=user.getPass();
        try {
            MessageDigest pwHash= MessageDigest.getInstance("SHA-256");
            pwHash.update(pw.getBytes());
            pw=new String(pwHash.digest());

        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }

        //INSERT INTO `user` (`id`, `name`, `email`, `password`, `roleid`) VALUES (NULL, 'Teszt', 'Teszt@teszt.com', 'pass', '2')
        String sql="INSERT INTO `user` (`id`, `name`, `email`, `password`, `roleid`) VALUES (NULL, '"+user.getName()+"', '"+user.getEmail()+"', '"+pw+"', '2')";


        if(isEmailSet(user.getEmail())){return "Az e-mail már foglalt!";}
        if(isNameSet(user.getName())){return "A név már foglalt!";}
        try {
            Statement Sta = Con.createStatement();
            int Res = Sta.executeUpdate(sql);

        return "Sikeres regisztráció!";

        } catch (SQLException e) {
            //System.out.println(e.getMessage());
            throw new RuntimeException(e);
        }


    }


    public String getName(String email){
        String sql="select * from user where email='"+email+"'";
        try {
            Statement Sta = Con.createStatement();
            ResultSet Res = Sta.executeQuery(sql);
            Res.next();
            //User qUser=new User(Res.getInt("id"),Res.getString("name"),Res.getString("email"),Res.getString("password"),Res.getString("role"));

            return Res.getString("name");
        } catch (SQLException e) {
            System.out.println( e.getMessage());
            throw new RuntimeException(e);
        }

    }

    public String getRole(User user){
        //select role_name from roles inner join user on user.roleid=roles.id where name="Teszt";
        if(user==null){return "Guest";}
        String sql="select role_name from roles inner join user on user.roleid=roles.id where name='"+user.getName()+"'";

        try {
            Statement Sta= Con.createStatement();
            ResultSet Res= Sta.executeQuery(sql);
            Res.next();
            return Res.getString("role_name");

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


    }

}
