import java.sql.*;

public class ExecuteQuery02 {

    public static void main(String[] args) throws ClassNotFoundException, SQLException {

        // 1.Adim : Driver'a kaydol
        Class.forName("org.postgresql.Driver");

        // 2.Adim : Database'e baglan
        Connection con = DriverManager.getConnection("jdbc:postgresql://localhost:5432/techproed", "postgres", "************");

        // 3.Adim : Statement olustur.
        Statement st = con.createStatement();

        //1. Örnek: companies tablosundan en yüksek ikinci number_of_employees değeri olan company ve
        // number_of_employees değerlerini çağırın.

        String sql1 = "select company, number_of_employees from companies order by number_of_employees desc offset 1 limit 1";

        ResultSet resultSet1 = st.executeQuery(sql1);

        while (resultSet1.next()){
            System.out.println(resultSet1.getString(1)+" - "+ resultSet1.getInt(2));
        }


        //2.YOL
        String sql2 = "SELECT company, number_of_employees\n" +
                "FROM companies\n" +
                "WHERE number_of_employees = (SELECT MAX(number_of_employees)\n" +
                "                            FROM companies\n" +
                "                            WHERE number_of_employees < (SELECT MAX(number_of_employees)\n" +
                "                                                         FROM companies))";


        ResultSet resultSet2 = st.executeQuery(sql2);
        while (resultSet2.next()){
            System.out.println(resultSet2.getString(1)+" - "+ resultSet2.getInt(2));
        }

        con.close();
        st.close();
        resultSet2.close();
        resultSet1.close();
















    }

}
