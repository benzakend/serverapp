import org.json.simple.JSONArray;

import java.sql.SQLException;
import java.util.ArrayList;

public class LogManager {

    private MySQLProvider dbProvider;



    public LogManager(){// connection Details to a cloud MySQL DB
        String serverIp = "sql12.freemysqlhosting.net";
        dbProvider = new MySQLProvider("yXL9LLIUYD".trim(),"YytUh56BHi".trim(), "remotemysql.com", "yXL9LLIUYD");
    }

    public void insertLog(String log){// method that stored  "insert" a log query and send  it to the dbProvider class.
        String insertQuery = "insert into LogsTable (log) values (\'"+ log +"\')";
        try {
            dbProvider.executeNonQuery(insertQuery);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public ArrayList selectAllLogs(){// method that stored  "select " a log query and send  it to the dbProvider class.
        String selectAllQuery = "select id, log from LogsTable";
        ArrayList listOfLogs= null;
        try {
            listOfLogs = dbProvider.executeQuery(selectAllQuery);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return listOfLogs;
    }

}
