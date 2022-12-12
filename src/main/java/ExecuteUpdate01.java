import java.sql.*;

public class ExecuteUpdate01 {

    public static void main(String[] args) throws ClassNotFoundException, SQLException {

        // 1.Adim : Driver'a kaydol  // 2.Adim : Database'e baglan  // 3.Adim : Statement olustur.
        Class.forName("org.postgresql.Driver");
        Connection con = DriverManager.getConnection("jdbc:postgresql://localhost:5432/techproed", "postgres", "**********0");
        Statement st = con.createStatement();

        //1. Örnek: number_of_employees değeri ortalama çalışan sayısından
        // az olan number_of_employees değerlerini 16000 olarak UPDATE edin.

      String sgl1 = "UPDATE companies SET number_of_employees = 16000 " +
              "         WHERE number_of_employees < (select avg (number_of_employees) from companies)";

        int updateEdilenSatirSayisi = st.executeUpdate(sgl1);
        System.out.println("updateEdilenSatirSayisi = " + updateEdilenSatirSayisi);


        ResultSet resultSet1 = st.executeQuery("select * from companies" );

        while (resultSet1.next()){

            System.out.println(resultSet1.getInt(1) + " "+ resultSet1.getString(2) + " " + resultSet1.getInt(3));
        }

        con.close();
        st.close();
        resultSet1.close();



    }

}
