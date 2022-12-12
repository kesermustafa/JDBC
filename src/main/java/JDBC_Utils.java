import java.sql.*;


public class JDBC_Utils {



    private static Connection connection;
    private static Statement statement;





    // 1.Adim : Driver'a kaydol
    // 2.Adim : Database'e baglan
    public static Connection connectToDataBase()  {

        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        try {
             connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/techproed", "postgres", "******");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


        if(connection!=null){
            System.out.println("DataBase baglandi");
        }else{
            System.out.println("Connection fail");
        }

        return connection;
    }


    // 3.Adim : Statement olustur.
    public static Statement createStatement(){
        try {
            statement = connection.createStatement();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return statement;
    }

    public static void toplama(int x, int y){

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
        try {
            statement.execute(sql1);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        //3. ADIM : Fonksiyonu cagir..
        CallableStatement cst1 = null;
        try {
            cst1 = connection.prepareCall("{? = call toplamaF(?, ?)}");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        // 4.ADIM : Return icin registerOutParameter() methodunu, parametreler icin ise set() methodlarini kullanacagiz.

        try {
            cst1.registerOutParameter(1, Types.NUMERIC);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        try {
            cst1.setInt(2, x);
            cst1.setInt(3, y);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        //5.ADIM : execute() methodu ile CallableStatement'i calistir.
        try {
            cst1.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        //6. ADIM : Sonucu cagirmak icin return data type tipine gore
        try {
            System.out.println(cst1.getBigDecimal(1));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


    }


    public static void koniHacimHesapla(int x, int y){

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
        try {
            statement.execute(sql2);
            CallableStatement cst2 = null;
            cst2 = connection.prepareCall("{? = call konininHacmiFonkcion(?, ?)}");
            cst2.registerOutParameter(1, Types.NUMERIC);
            cst2.setInt(2, x);
            cst2.setInt(3, y);
            cst2.execute();
            System.out.println(String.format("%.2f", cst2.getBigDecimal(1)));
            System.out.printf("%.2f", cst2.getBigDecimal(1));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }



}
