import java.sql.Connection;
import java.sql.Statement;

public class Runner {

    public static void main(String[] args) {

     Connection connection = JDBC_Utils.connectToDataBase();
     Statement statement = JDBC_Utils.createStatement();


     JDBC_Utils.toplama(22,11);
     JDBC_Utils.koniHacimHesapla(1, 6);










    }
}
