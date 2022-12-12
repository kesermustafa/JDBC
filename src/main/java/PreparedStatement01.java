import java.sql.*;

public class PreparedStatement01 {
    public static void main(String[] args) throws ClassNotFoundException, SQLException {

        /*
        Prepared Statement interface, birden cok kez calistilabilen onceden derlenmis bir SQL kodunu temsil eder.
        Parametrelendirilmis SQL sorgulari ile calisir. Bu sorguyu 0 veya daha fazla parametre ile kullanabiliriz.
         */

        Class.forName("org.postgresql.Driver");
        Connection con = DriverManager.getConnection("jdbc:postgresql://localhost:5432/techproed", "postgres", "**********");
        Statement st = con.createStatement();

        //1. Örnek: Prepared statement kullanarak company adı IBM olan number_of_employees değerini 9999 olarak güncelleyin.

        //1.Adim : repared statement query sini olustur.
        String sql1 = "UPDATE companies SET number_of_employees = ? WHERE company = ? ";

        // 2. Adim :  Prepared Statement objesini olustur.
        PreparedStatement pst1 = con.prepareStatement(sql1);

        // 3. Adim : setInt(), setString(),.... diger methodlarini kullanarak soru isaretleri yerlerini deger gir.
        pst1.setInt(1, 9999);
        pst1.setString(2, "IBM");

        //4. Adim : Query'yi calistir.
        int guncelenenStairSayisi = pst1.executeUpdate();
        System.out.println("guncelenenStairSayisi = " + guncelenenStairSayisi);

        String sql2 = "SELECT * FROM companies";

        ResultSet resultSet1 = st.executeQuery(sql2);

        while (resultSet1.next()){
            System.out.println(resultSet1.getInt(1)+" "+resultSet1.getString(2)+ " "+ resultSet1.getInt(3));
        }

        //2. Örnek: Prepared statement kullanarak company adı GOOGLE olan number_of_employees değerini 5555 olarak güncelleyin.
        pst1.setInt(1, 5555);
        pst1.setString(2, "GOOGLE");

        int guncelenenStairSayisi1 = pst1.executeUpdate();
        System.out.println("guncelenenStairSayisi = " + guncelenenStairSayisi1);

        ResultSet resultSet2 = st.executeQuery(sql2);

        while (resultSet2.next()){
            System.out.println(resultSet2.getInt(1)+" "+resultSet2.getString(2)+ " "+ resultSet2.getInt(3));
        }

        con.close();
        st.close();
        resultSet1.close();
        resultSet2.close();
        pst1.close();















    }
}
