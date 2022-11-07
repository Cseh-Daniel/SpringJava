package com.example.mozimusor;


import javax.xml.crypto.Data;
import java.sql.*;
import java.util.ArrayList;

public class DatabaseDAO {

    private Connection Con;

    //String  sql="SELECT filmcim,szines,szinkron,szarmazas,mufaj,hossz,mozinev,irszam,cim,telefon FROM mozi,hely,film where mozi.moziazon=hely.moziazon and film.fkod=hely.fkod;";
    //String sql="SELECT filmcim,szarmazas,mufaj,mozinev,cim FROM mozi,hely,film where mozi.moziazon=hely.moziazon and film.fkod=hely.fkod; ";

    public DatabaseDAO(String url){//"jdbc:mysql://localhost/mozimusor?user=root"
        try {
            Con= DriverManager.getConnection(url);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
    public ArrayList<Database> getAll(){
        String sql="SELECT filmcim,szarmazas,mufaj,mozinev,cim FROM mozi,hely,film where mozi.moziazon=hely.moziazon and film.fkod=hely.fkod";
        ArrayList<Database> data = new ArrayList<Database>();
        try {

            Statement Sta = Con.createStatement();
            ResultSet Res = Sta.executeQuery(sql);
            Res.next();

            String filcim=Res.getString("filmcim");
            String szarmazas=Res.getString("szarmazas");
            String mufaj=Res.getString("mufaj");
            String mozinev=Res.getString("mozinev");
            String cim=Res.getString("cim");

            data.add(new Database(filcim,szarmazas,mufaj,mozinev,cim));

            return data;
        } catch (SQLException e) {
            System.out.println( e.getMessage());
            throw new RuntimeException(e);
        }

    }




}
