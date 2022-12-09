import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class Execute01 {


    public static void main(String[] args) throws ClassNotFoundException, SQLException {
        // 1.Adim : Driver'a kaydol
        Class.forName("org.postgresql.Driver");
        // 2.Adim : Database'e baglan
        Connection con = DriverManager.getConnection("jdbc:postgresql://localhost:5432/techproed", "postgres", "*****");
        // 3.Adim : Statement olustur.
        Statement st = con.createStatement();
        // 4.Adim : Query calistir.
        System.out.println("Connection Success");

        /* execute() methodu DDL(Create, drop alter table ve DQL(select) icin kullanilabilir.
        1- eger execute() methodu DDL icin kullanilirsa false return eder...
        2- eger execute() methodu DQL icin kullanilirsa ResultSet alindiginda trure aksi halde false verir...

        */

        //1.Örnek: "workers" adında bir table oluşturup "worker_id,worker_name, worker_salary" sütunlarını ekleyin.
        boolean sql1 =  st.execute("CREATE TABLE workers (worker_id VARCHAR(20), worker_name VARCHAR(20), worker_salary INT)");
        System.out.println("sql1b = " +  sql1); // false return eder cunku daya cagirmiyoruz...

        //2.Örnek: Table'a worker_address sütunu ekleyerek alter yapın.
        String sql2 = "ALTER TABLE workers ADD worker_address VARCHAR(80)";
        boolean sql2b = st.execute(sql2);
        System.out.println("sql2b = " + sql2b);

        //3.Örnek: workers table'ını silin.
        String sql3 = "DROP TABLE workers";
        st.execute(sql3);

        // 5.Adim : Baglanti ve statement'i kapat.
        con.close();
        st.close();



    }
}