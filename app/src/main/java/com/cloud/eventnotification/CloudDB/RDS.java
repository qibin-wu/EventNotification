package com.cloud.eventnotification.CloudDB;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.text.Editable;

import com.cloud.eventnotification.Model.UserEvents;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class RDS {
    static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    static final String DB_URL = "jdbc:mysql://eventn.caskf3tbze59.us-east-1.rds.amazonaws.com:3306/eventn?useSSL=false";
    static final String USER = "Morris";
    static final String PASS = "bbq12345";

    public static void addEvent(UserEvents tempEvent)
    {
        if(checkEventExist(tempEvent))
        {
            return;
        }
        Connection conn = null;
        Statement stmt = null;
        try{

            Class.forName(JDBC_DRIVER);
            conn = DriverManager.getConnection(DB_URL,USER,PASS);
            stmt = conn.createStatement();
            String sql;
            SimpleDateFormat sdf = new SimpleDateFormat("d/MM/yyyy h:mm:ss aa");
            sql = "INSERT INTO eventn.Event" +
                    "(ID, title, stime, etime, Location, Android_ID)" +
                    "VALUES('"+tempEvent.getID()+"', '"+tempEvent.getTitle()+"', '"+sdf.format(tempEvent.getsTime())+"', '"+sdf.format(tempEvent.geteTime())+"', '"+tempEvent.getLocation()+"', '"+tempEvent.getAndroid_ID()+"')";
          stmt.executeUpdate(sql);
            stmt.close();
            conn.close();
        }catch(SQLException se){
            se.printStackTrace();
        }catch(Exception e){
            e.printStackTrace();
        }finally{

            try{
                if(stmt!=null) stmt.close();
            }catch(SQLException se2){
            }
            try{
                if(conn!=null) conn.close();
            }catch(SQLException se){
                se.printStackTrace();
            }
        }




    }

    private static boolean checkEventExist(UserEvents tempEvent) {
        boolean check =false;
        int i=0;
        Connection conn = null;
        Statement stmt = null;
        try{

            Class.forName(JDBC_DRIVER);
            conn = DriverManager.getConnection(DB_URL,USER,PASS);
            stmt = conn.createStatement();
            String sql;
            sql = "SELECT * FROM Event where title='"+tempEvent.getTitle() +"' and Android_ID='"+ tempEvent.getAndroid_ID()+"'";
            ResultSet rs = stmt.executeQuery(sql);
            while(rs.next()){
               i++;
            }
            rs.close();
            stmt.close();
            conn.close();
        }catch(SQLException se){

            se.printStackTrace();
        }catch(Exception e){
            e.printStackTrace();
        }finally{

            try{
                if(stmt!=null) stmt.close();
            }catch(SQLException se2){
            }
            try{
                if(conn!=null) conn.close();
            }catch(SQLException se){
                se.printStackTrace();
            }
        }
        if(i !=0)
        {
            check=true;
        }


        return check;
    }

    public static ArrayList<UserEvents> selectEvents(String a_id) {
        ArrayList<UserEvents> userEvents= new ArrayList<>();
        SimpleDateFormat sdf = new SimpleDateFormat("d/MM/yyyy h:mm:ss aa");
        Connection conn = null;
        Statement stmt = null;
        try{

            Class.forName(JDBC_DRIVER);
            conn = DriverManager.getConnection(DB_URL,USER,PASS);
            stmt = conn.createStatement();
            String sql;
            sql = "SELECT * FROM eventn.Event where Android_ID='"+ a_id+"'";
            ResultSet rs = stmt.executeQuery(sql);
            while(rs.next()){
                userEvents.add(new UserEvents(rs.getString("ID"),rs.getString("title"),sdf.parse(rs.getString("stime")),sdf.parse(rs.getString("etime")),rs.getString("location"),rs.getString("Android_ID")));
            }
            rs.close();
            stmt.close();
            conn.close();
        }catch(SQLException se){

            se.printStackTrace();
        }catch(Exception e){
            e.printStackTrace();
        }finally{

            try{
                if(stmt!=null) stmt.close();
            }catch(SQLException se2){
            }
            try{
                if(conn!=null) conn.close();
            }catch(SQLException se){
                se.printStackTrace();
            }
        }


        return userEvents;

    }

    public static int getTh(String a_id) {


        return EndPoint.getTh(a_id);

    }
    public static int getRa(String a_id) {


        return EndPoint.getRa(a_id);

    }

    public static UserEvents  getEventBytitle(String title,String AID)
    {
        SimpleDateFormat sdf = new SimpleDateFormat("d/MM/yyyy h:mm:ss aa");
        UserEvents userEvents=new UserEvents("unknow",title,new Date(System.currentTimeMillis()),new Date(System.currentTimeMillis()),"unknow",AID);
        Connection conn = null;
        Statement stmt = null;
        try{

            Class.forName(JDBC_DRIVER);
            conn = DriverManager.getConnection(DB_URL,USER,PASS);
            stmt = conn.createStatement();
            String sql;
            sql = "SELECT * FROM eventn.Event where Android_ID='"+ AID+"' and title='"+title+"'";
            ResultSet rs = stmt.executeQuery(sql);
            while(rs.next()){
              userEvents=new UserEvents(rs.getString("ID"),rs.getString("title"),sdf.parse(rs.getString("stime")),sdf.parse(rs.getString("etime")),rs.getString("location"),rs.getString("Android_ID"));
            }
            rs.close();
            stmt.close();
            conn.close();
        }catch(SQLException se){

            se.printStackTrace();
        }catch(Exception e){
            e.printStackTrace();
        }finally{

            try{
                if(stmt!=null) stmt.close();
            }catch(SQLException se2){
            }
            try{
                if(conn!=null) conn.close();
            }catch(SQLException se){
                se.printStackTrace();
            }
        }

        return userEvents;
    }

    public static void deleteEvent(UserEvents userEvents) {
        Connection conn = null;
        Statement stmt = null;
        try{

            Class.forName(JDBC_DRIVER);
            conn = DriverManager.getConnection(DB_URL,USER,PASS);
            stmt = conn.createStatement();
            String sql;
            sql = "DELETE FROM eventn.Event" +
                    "WHERE title='"+userEvents.getTitle()+"' and Android_ID='"+userEvents.getAndroid_ID() +"';";
            stmt.executeUpdate(sql);
            stmt.close();
            conn.close();
        }catch(SQLException se){
            se.printStackTrace();
        }catch(Exception e){
            e.printStackTrace();
        }finally{

            try{
                if(stmt!=null) stmt.close();
            }catch(SQLException se2){
            }
            try{
                if(conn!=null) conn.close();
            }catch(SQLException se){
                se.printStackTrace();
            }
        }

    }

    public static void addEvents(ArrayList<UserEvents> userEvents) {

        for(int i=0;i<userEvents.size();i++)
        {
            RDS.addEvent(userEvents.get(i));
        }
    }

    public static void setTime(String AID, String TH, String RA) {

        Connection conn = null;
        Statement stmt = null;
        try{

            Class.forName(JDBC_DRIVER);
            conn = DriverManager.getConnection(DB_URL,USER,PASS);
            stmt = conn.createStatement();
            String sql;
            sql = "INSERT INTO eventn.UserSetting" +
                    "(Android_ID, Threshold, Remind)" +
                    "VALUES('"+AID+"', "+Integer.parseInt(TH)+", "+Integer.parseInt(RA)+");";
            stmt.executeUpdate(sql);
            stmt.close();
            conn.close();
        }catch(SQLException se){
            se.printStackTrace();
        }catch(Exception e){
            e.printStackTrace();
        }finally{

            try{
                if(stmt!=null) stmt.close();
            }catch(SQLException se2){
            }
            try{
                if(conn!=null) conn.close();
            }catch(SQLException se){
                se.printStackTrace();
            }
        }



    }

    public static void deleteEvent(String title, String aid) {

        RDS.deleteEvent(getEventBytitle(title,aid));

    }
}
