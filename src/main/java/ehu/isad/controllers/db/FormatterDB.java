package ehu.isad.controllers.db;

import javax.print.DocFlavor;
import java.sql.SQLException;

public class FormatterDB {
    private static FormatterDB instance = new FormatterDB();
    private static DBController dbcontroller = DBController.getController();
    private FormatterDB() {}

    private Boolean exists(String url,String format) throws SQLException {
        String q1 = "Select * from cache  where url = ";
        String q2 = " AND ";
        String q3 = " = TRUE;";
        StringBuilder query = new StringBuilder();
        query.append(q1).append(url).append(q2).append(format).append(q3);
        System.out.println( query.append(q1).append(url).append(q2).append(format).append(q3));
        return dbcontroller.execSQL(query.toString()).next();
    }

    private void addDB(String url, String format) {
        //This method adds the "check" to the database.
        String q = "Insert into cache(domain,"+format+") values("+url+", TRUE)";
        dbcontroller.execSQL(q);
    }



}
