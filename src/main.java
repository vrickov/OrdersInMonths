import java.io.*;
import java.lang.reflect.Array;
import java.sql.Connection;
import java.time.Instant;
import java.util.*;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class main {
    public static void main(String[] args) throws ParseException {

        String jdbcurl = "jdbc:postgresql://localhost:5432/postgres";
        String username = "postgres";
        String password = "ricardop";

        try {
            Connection connection = DriverManager.getConnection(jdbcurl, username, password);
            System.out.println("Connected");

            Scanner sc = new Scanner(System.in);  // Cria  Scanner 
            System.out.println("Intervalo de datas:");

            String intervalo = sc.nextLine();  // le user input
            System.out.println("Intervalo de datas: " + intervalo);  // Output do user input
            String[] info = intervalo.split("\" \"");

            info[0] = info[0].replace("\"", "");
            info[1] = info[1].replace("\"", "");

            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

            Date inicio = formatter.parse(info[0]);
            Date fim = formatter.parse(info[1]);

            Calendar initialCalendar = Calendar.getInstance();
            initialCalendar.setTime(inicio);

            Calendar endCalendar = Calendar.getInstance();
            endCalendar.setTime(fim);

            int initialYear = initialCalendar.get(Calendar.YEAR);
            int finalYear = endCalendar.get(Calendar.YEAR);

            Date date1 = new GregorianCalendar(initialYear, Calendar.JANUARY, 1).getTime();
            String sqlDate1 = new java.sql.Date(date1.getTime()).toString()
                    .replace("-", "/");

            Date date2 = new GregorianCalendar(initialYear, Calendar.APRIL, 1).getTime();
            String sqlDate2 = new java.sql.Date(date2.getTime()).toString()
                    .replace("-", "/");

            Date date3 = new GregorianCalendar(initialYear, Calendar.JULY, 1).getTime();
            String sqlDate3 = new java.sql.Date(date3.getTime()).toString()
                    .replace("-", "/");

            Date date4 = new GregorianCalendar(initialYear + 1, Calendar.JANUARY, 1).getTime();
            String sqlDate4 = new java.sql.Date(date4.getTime()).toString()
                    .replace("-", "/");


            java.sql.Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery("SELECT COUNT(*) AS recordCount FROM public.orders WHERE orderdate between '" + sqlDate1 +
                    "' and  '" + sqlDate2 + "'");
            rs.next();
            int count1 = rs.getInt("recordCount");

            rs = statement.executeQuery("SELECT COUNT(*) AS recordCount FROM public.orders WHERE orderdate between '" + sqlDate2 +
                    "' and  '" + sqlDate3 + "'");
            rs.next();
            int count2 = rs.getInt("recordCount");

            rs = statement.executeQuery("SELECT COUNT(*) AS recordCount FROM public.orders WHERE orderdate between '" + sqlDate3 +
                    "' and  '" + sqlDate4 + "'");
            rs.next();
            int count3 = rs.getInt("recordCount");


            rs = statement.executeQuery("SELECT COUNT(*) AS recordCount FROM public.orders WHERE orderdate >= '" + sqlDate4 + "'");
            rs.next();
            int count4 = rs.getInt("recordCount");

            System.out.println("1-3 months: " + count1 + " orders");
            System.out.println("4-6 months: " + count2 + " orders");
            System.out.println("7-12 months: " + count3 + " orders");
            System.out.println(">12 months: " + count4 + " orders");
            
            connection.close();

        } catch (SQLException e){
            System.out.print("Error");
            e.printStackTrace();
        }
    }
}