import org.junit.Assert;
import org.junit.Test;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class MedunnaTest {

    /*
   Given
     User connects to the database
     (Host name: medunna.com, Database name: medunna_db, Usename: medunna_user, Password: medunna_pass_987))
   When
     User sends the query to get the names of created_by column from "room" table
   Then
     Assert that there are some rooms created by "john_doe".
   And
     User closes the connection
  */

    @Test
    public void medunnaTest() throws SQLException {
      //  User connects to the database
    JDBC_Utils.connectToDataBase("medunna.com", "medunna_db", "medunna_user", "medunna_pass_987"   );
    Statement statement = JDBC_Utils.createStatement();

    //User sends the query to get the names of created_by column from "room" table

        String sql1 = "Select created_by From room";

        ResultSet resultSet1 = statement.executeQuery(sql1);
        List<String> created_by_byList = new ArrayList<>();

        while (resultSet1.next()){
            created_by_byList.add(resultSet1.getString(1));
        }

        System.out.println("created_by_byList = " + created_by_byList);

       // Assert that there are some rooms created by "john_doe".


        Assert.assertTrue(created_by_byList.contains("john_doe"));

        // User closes the connection
        JDBC_Utils.closeConnectionAndStatement();



    }
}
