import java.sql.Connection;
import java.sql.Statement;

public class Runner {

    public static void main(String[] args) {

     Connection connection = JDBC_Utils.connectToDataBase("localhost", "techproed", "postgres", "*********");

    //3.Adim : Statement olustur
     Statement statement = JDBC_Utils.createStatement();


     //4.Adim: Query calistir..
    // JDBC_Utils.execute("CREATE TABLE studentss1 (name VARCHAR(20), id INT, adress VARCHAR(80))");

     JDBC_Utils.toplama(4,5);
     JDBC_Utils.koniHacimHesapla(1,6);

     System.out.println("");


     JDBC_Utils.createTable("abcd", "name VARCHAR(10)", "ID INT", "adress VARCHAR(80)");

     JDBC_Utils.createTable("Scholl", "classes VARCHAR(20)", " teacher_name VARCHAR(20)", "id INT" );

     //5.Adim: connections ve statement kapat.
     JDBC_Utils.closeConnectionAndStatement();








    }
}
