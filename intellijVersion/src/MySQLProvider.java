import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import java.util.*;

import java.sql.*;

public class MySQLProvider {

    protected MysqlDataSource dataSource;// Interface that used  to create connection to thd DataBase.

    public MySQLProvider(String user, String password, String serverName, String databaseName){ //constructor
        dataSource = new MysqlDataSource();
        dataSource.setUser(user);
        dataSource.setPassword(password);
        dataSource.setServerName(serverName);
        dataSource.setDatabaseName(databaseName);
    }


    /**
     * method that get query and return ArrayList that contain all logs from the DB
     * @param query
     * @return ArrayList
     * @throws SQLException
     */
    public ArrayList executeQuery(String query) throws SQLException {
        Connection connection = dataSource.getConnection();//first get connection using the Interface method
        PreparedStatement statement = connection.prepareStatement(query);//preparing the query
        ResultSet resultSet = null;
        ArrayList convertedArray = null;
        try{
            resultSet = statement.executeQuery();//return the query output to a resultSet
            convertedArray = resultSetToArrayList(resultSet); //converting the resultSet to an ArrayList
        }catch (SQLException e){
            throw new SQLException("couldn't execute query. Details: "+e.getMessage());
        }finally {//closing the connection and the resultSet anyway.
            if(statement!=null){
                statement.close();
            }
            if(resultSet!=null){
                resultSet.close();
            }
        }
        return convertedArray;
    }

    /**
     * for insert, put, update queries.
     * @param query
     * @return
     * @throws SQLException
     */
    public void executeNonQuery(String query) throws SQLException {
        Connection connection = dataSource.getConnection();
        PreparedStatement statement = connection.prepareStatement(query);
        try{
            statement.executeUpdate();
        }catch (SQLException e){
            throw new SQLException("couldn't execute query. Details: "+e.getMessage());
        }finally {
            if(statement!=null){
                statement.close();
            }
        }
    }


    /**
     * method that get ResultSet and return ArrayList that contain all the Log to print them on the screen
     * @param  rs ResultSet
     * @return ArrayList
     * @throws SQLException
     */

    private ArrayList resultSetToArrayList(ResultSet rs) throws SQLException{
        ResultSetMetaData md = rs.getMetaData();
        int columns = md.getColumnCount();
        ArrayList list = new ArrayList();
        while (rs.next()){
            HashMap row = new HashMap(columns);
            for(int i=1; i<=columns; ++i){
                String data= null;
                if (rs.getObject(i) instanceof String){
                    data = (String)(rs.getObject(i));
                }else
                     data= Integer.toString((Integer) rs.getObject(i));
                row.put(md.getColumnName(i)+' ',' '+ data);


            }
            list.add(row);

        }

        return list;
    }
}