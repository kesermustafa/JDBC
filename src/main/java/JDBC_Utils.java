import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class JDBC_Utils {

    private static Connection connection;
    private static Statement statement;
    private static ResultSet resultSet;


    // 1.Adim : Driver'a kaydol
    // 2.Adim : Database'e baglan
    public static Connection connectToDataBase(String hostName, String dbName, String userName, String pasword)  {

        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        try {
             connection = DriverManager.getConnection("jdbc:postgresql://"+hostName+":5432/"+dbName+"", ""+userName+ "", ""+pasword+"");
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


    // 4.Adim Query calistir

    public static boolean execute(String sql){
        boolean isExecute;

        try {
            isExecute = statement.execute((sql));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return isExecute;

    }


    // 5.Adim : Baglanti ve statement'i kapat.

    public static void closeConnectionAndStatement(){

        try {
            connection.close();
            statement.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        try {
            if(connection.isClosed() && statement.isClosed()){
                System.out.println("Connections and Statement closed!");
            }else {
                System.out.println("Connections and Statement NOT closed!");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    public static void main(String[] args) {
        connectToDataBase("localhost", "techproed", "postgres", "Mk.2243250");
        createStatement();
      //  createTable("abcdeq", "name VARCHAR(10)", "ID INT", "adress VARCHAR(80)");
        closeConnectionAndStatement();

    }



    // Table olusturan method
      public static void createTable(String tableName, String... columnName_dateType ){

        StringBuilder columnName_dateValue = new StringBuilder("");

        for(String w : columnName_dateType){
            columnName_dateValue.append(w).append(",");
        }

        columnName_dateValue.deleteCharAt(columnName_dateValue.length()-1);

        try {
            statement.execute("CREATE TABLE "+tableName+"("+columnName_dateValue+")");
            System.out.println("Table " + tableName + " created!");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    //ÖDEV:

    //ExecuteQuery methodu
    public static ResultSet executeQuery(String query) {

        try {
            resultSet = statement.executeQuery(query);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return resultSet;
    }

    //ExecuteUpdate methodu
    public static int executeUpdate(String query) {
        int guncellenenSatirSayisi;

        try {
            guncellenenSatirSayisi = statement.executeUpdate(query);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return guncellenenSatirSayisi;
    }

    //Table'a data girme methodu
    public static void insertDataIntoTable(String tableName, String... columnName_Value) {

        StringBuilder columnNames = new StringBuilder("");
        StringBuilder values = new StringBuilder("");

        for (String w : columnName_Value) {
            columnNames.append(w.split(" ")[0]).append(",");//Bir String değeri ikiye bölüp birinciyi sütun adı, ikinciyi sütun değeri olarak alıyorum.
            values.append(w.split(" ")[1]).append(",");
        }

        columnNames.deleteCharAt(columnNames.lastIndexOf(","));//En son virgülü siliyor.
        values.deleteCharAt(values.lastIndexOf(","));

        //"INSERT INTO      members     ( id, name, address ) VALUES(123, 'john', 'new york')"
        String query = "INSERT INTO " + tableName + "(" + columnNames + ") VALUES(" + values + ")";

        execute(query);//execute methodu üstte oluşturuldu, query'yi çalıştırıyor.
        System.out.println("Data " + tableName + " tablosuna girildi.");

    }

    //Sütun Değerlerini List içerisine alan method
    public static List<Object> getColumnList(String columnName, String tableName) {

        List<Object> columnData = new ArrayList<>();//ResultSet'ten alınan datanın koyulacağı List.

        //SELECT        id          FROM      students
        String query = "SELECT " + columnName + " FROM " + tableName;

        executeQuery(query);// => Bu method üstte oluşturuldu. Query'yi çalıştırıp alınan datayı 'resultSet' container'ı içine atama yapıyor.

        try {
            while (resultSet.next()) {
                columnData.add(resultSet.getObject(columnName));//add methodu ile alınan sütun değerlerini List'e ekliyor.
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return columnData;
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
