package ehu.isad.controllers.db;

import ehu.isad.model.ServerCMS;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ServerCMSDB {

    private static final ServerCMSDB instance = new ServerCMSDB();
    private static final DBController dbcontroller = DBController.getController();

    private ServerCMSDB() {   }

    public static ServerCMSDB getInstance() {
        return instance;
    }

    public void insertQueryIntoDB(String query){
        dbcontroller.execSQL(query);
    }

    public void updateDate(){

    }

    public boolean domainInDB(String domain) throws SQLException {
        String query = "select target_id from targets where target = '" + domain + "'";
        ResultSet rs = dbcontroller.execSQL(query);
        if(rs.next()){
            return true;
        }else{
            return false;
        }
    }

    public ObservableList<ServerCMS> getCMSDB() throws SQLException {
        ObservableList<ServerCMS> results = FXCollections.observableArrayList();
        String firstQuery = "select * from targets";
        ResultSet rst = dbcontroller.execSQL(firstQuery);
        if(rst.next()){
            String query = "select target,name,s.*,date from scans as s,targets as t,plugins as p, servercmsDate as d where s.plugin_id = p.plugin_id and t.target_id = s.target_id";
            ResultSet rs = dbcontroller.execSQL(query);
            String loopTarget= null;
            String currentTarget=null;
            String cms = null;
            try {
                if(rs.next()){
                    loopTarget = rs.getString("target");
                }
                while(rs.next()){
                    currentTarget = rs.getString("target");
                    if(currentTarget.equals(loopTarget)){
                        cms = validCMS(rs.getString("name"));
                        if(cms != null){
                            results.add(new ServerCMS(rs.getString("target"),cms,null,rs.getString("version"),""));
                        }
                    }else{
                        loopTarget = currentTarget;
                        if(cms == null){
                            results.add(new ServerCMS(rs.getString("target"),"unknown",null,"0",""));
                        }
                    }
                }
            }catch(SQLException e){ e.printStackTrace(); }
        }
        return results;
    }

    private String validCMS(String plugin){
        switch (plugin) {
            case "WordPress":
                return "WordPress";
            case "Joomla":
                return "Joomla";
            case "phpMyAdmin":
                return "phpMyAdmin";
            case "Drupal":
                return "Drupal";
            default:
                return null;
        }
    }

}
