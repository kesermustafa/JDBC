import java.sql.*;

public class CallableStatement01 {
    public static void main(String[] args) throws ClassNotFoundException, SQLException {

        /*
        Java'da methodlar return type olsada olmasada method olarak adlandirilir.
        SQL'de ise data return ediyorsa "function" denir. Return yapmiyorsa "procedure" olarak adlandirilir.
         */

        Class.forName("org.postgresql.Driver");
        Connection con = DriverManager.getConnection("jdbc:postgresql://localhost:5432/techproed", "postgres", "**********");
        Statement st = con.createStatement();

        // CallableStatement ile function cagirmayi parametrelendirecegiz...

        //1. Adim : Fonkciyon kodunu yaz
        String sql1 = "CREATE OR REPLACE FUNCTION  toplamaF(x NUMERIC, y NUMERIC)\n" +
                "RETURNS NUMERIC\n" +
                "LANGUAGE plpgsql\n" +
                "AS \n" +
                "$$\n" +
                "BEGIN \n" +
                "\n" +
                "RETURN X+Y;\n" +
                "\n" +
                "END\n" +
                "$$";
        //2. ADIM : Fonksiyonu calistir.
        st.execute(sql1);

        //3. ADIM : Fonksiyonu cagir..
        CallableStatement cst1 = con.prepareCall("{? = call toplamaF(?, ?)}");

        // 4.ADIM : Return icin registerOutParameter() methodunu, parametreler icin ise set() methodlarini kullanacagiz.

        cst1.registerOutParameter(1, Types.NUMERIC);
        cst1.setInt(2, 6);
        cst1.setInt(3, 4);

        //5.ADIM : execute() methodu ile CallableStatement'i calistir.
        cst1.execute();

        //6. ADIM : Sonucu cagirmak icin return data type tipine gore
        System.out.println(cst1.getBigDecimal(1));


        //2. Örnek: Koninin hacmini hesaplayan bir function yazın.


    //1. Adim : Fonkciyon kodunu yaz
        String sql2 = "CREATE OR REPLACE FUNCTION  konininHacmiFonkcion(r NUMERIC, h NUMERIC)\n" +
                "RETURNS NUMERIC\n" +
                "LANGUAGE plpgsql\n" +
                "AS \n" +
                "$$\n" +
                "BEGIN \n" +
                "\n" +
                "RETURN 3.14*r*r*h/3;\n" +
                "\n" +
                "END\n" +
                "$$";
        //2. ADIM : Fonksiyonu calistir.
        st.execute(sql2);

        //3. ADIM : Fonksiyonu cagir..
        CallableStatement cst2 = con.prepareCall("{? = call konininHacmiFonkcion(?, ?)}");

        // 4.ADIM : Return icin registerOutParameter() methodunu, parametreler icin ise set() methodlarini kullanacagiz.

        cst2.registerOutParameter(1, Types.NUMERIC);
        cst2.setInt(2, 1);
        cst2.setInt(3, 6);

        //5.ADIM : execute() methodu ile CallableStatement'i calistir.
        cst2.execute();

        //6. ADIM : Sonucu cagirmak icin return data type tipine gore
        System.out.println(String.format("%.2f", cst2.getBigDecimal(1)));

        System.out.printf("%.2f", cst2.getBigDecimal(1));










    }
}
