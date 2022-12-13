import org.junit.Assert;
import org.junit.Test;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class CountriesTest {

    /*
        Given
          User connects to the database
        When
          User sends the query to get the region ids from "countries" table
        Then
          Assert that the number of region ids greater than 1 is 17.
        And
          User closes the connection
       */

    @Test
    public void countryTest() throws SQLException {
        //User connects to the database
        JDBC_Utils.connectToDataBase("localhost", "techproed", "postgres", "*******");
        Statement statement = JDBC_Utils.createStatement();

        //User sends the query to get the region ids from "countries" table

        String sql1 = "Select region_id From countries";

        ResultSet resultSet1 = statement.executeQuery(sql1);
        List<Integer> ids = new ArrayList<>();

        while (resultSet1.next()){
            ids.add(resultSet1.getInt(1));
        }
        System.out.println(ids);


        // Assert that the number of region ids greater than 1 is 17.
        List<Integer> ids1denBuyukId = new ArrayList<>();
        for (int w : ids){
            if(w>1){
                ids1denBuyukId.add(w);
            }
        }

        System.out.println(ids1denBuyukId);
        System.out.println(ids1denBuyukId.size());

        //TEST
        Assert.assertEquals(17, ids1denBuyukId.size() );

        //   User closes the connection
        JDBC_Utils.closeConnectionAndStatement();



    }
}
