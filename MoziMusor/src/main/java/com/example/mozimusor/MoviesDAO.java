package com.example.mozimusor;

import com.google.gson.Gson;

import java.sql.*;

public class MoviesDAO {

    Connection Con;

    public MoviesDAO(String URL) {
        try {
            Con= DriverManager.getConnection(URL);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

 public String getMoviebyID(int id){

     try {
         Statement sta= Con.createStatement();
         ResultSet res=sta.executeQuery("select count(*) as db from film");
         res.next();
         int max= res.getInt("db");
         Gson gs=new Gson();

         String ret="";
         //{"error":<string>}
         String error="Érvénytelen film id (1-"+max+" érvényes)!";
         if(id<1 || id>max){return error;}

         res=sta.executeQuery("select * from film where fkod="+id);

         res.next();

         int fkod=res.getInt("fkod");
         String filmcim=res.getString("filmcim");
         int szines=res.getInt("szines");
         String szinkron=res.getString("szinkron");
         String szarmazas=res.getString("szarmazas");
         String mufaj=res.getString("mufaj");
         int hossz=res.getInt("hossz");

         Movies mov=new Movies(fkod,filmcim,szines,szinkron,szarmazas,mufaj,hossz);

        ret=gs.toJson(mov);

         return ret;
     } catch (SQLException e) {
         throw new RuntimeException(e);
     }



 }


}
