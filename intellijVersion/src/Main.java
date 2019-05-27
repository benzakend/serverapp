import org.json.simple.JSONArray;
import spark.Spark;

import java.util.*;
import static spark.Spark.*;
import static spark.route.HttpMethod.get;

public class Main {//please run the Main class after  you install the "pom.xml" file

    public static void main(String[] args) {
        LogManager logManager = new LogManager();

            Spark.get("/logs/showLogs", (req, res) -> {// use get method to show all the logs in the DB
                ArrayList logs = null;
                 logs = logManager.selectAllLogs();

            return logs;
        });


        Spark.get("/logs", (req, res) -> {// use get method to show all the logs in the DB
            String mass = "Home Page";


            return mass;
        });




        Spark.get("/logs/addLog/:log", (req, res) -> {
            String log = req.params("log");
            logManager.insertLog(log);
            return "log inserted successfully";
        });
        post("/logs/addLog", (req, res) -> {// use post  method to insert a new log.
            String log = req.body();
            logManager.insertLog(log);
            return "log inserted successfully";
        });



    }
}
