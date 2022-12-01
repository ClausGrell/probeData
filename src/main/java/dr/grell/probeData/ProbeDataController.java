package dr.grell.probeData;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

import java.sql.SQLException;
import java.util.concurrent.atomic.AtomicLong;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController


public class ProbeDataController

{
    private static final String template = "Hello, %s!";
    private final AtomicLong counter = new AtomicLong();

    @GetMapping("/aabyvej")
    public String greeting(@RequestParam String probe, String dateTime, String messure) {
        System.out.println("Getting...");
        Connection dbConnection = null;
        String dbURL = "jdbc:oracle:thin:@(DESCRIPTION= (ADDRESS= (PROTOCOL=TCP) (HOST=192.168.0.242) (PORT=1521)) (CONNECT_DATA= (SERVICE_NAME=XEPDB1)))";
        String strUserID = "workspace";
        String strPassword = "S0r0ver";
        System.out.println("Getting..." + probe + ", " + dateTime + ", " + messure );
        PreparedStatement preparedStatement;
        try {
            dbConnection=DriverManager.getConnection(dbURL,strUserID,strPassword);
            //preparedStatement = dbConnection.prepareStatement("alter session set container=XEPDB1");
            //preparedStatement.executeUpdate();
            System.out.println("inserting data...");
            String sql="insert into aabyvej (probe,datetime,messurement) values ('" + probe + "',to_Date('" + dateTime + "','YYYYMMDDHH24MI'),to_number('" + messure + "') )";
            System.out.println("SQL=" + sql);

            preparedStatement = dbConnection.prepareStatement(sql);
            preparedStatement.executeUpdate();
            preparedStatement.close();
            dbConnection.close();

        }  catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            System.out.println(" ");
        }

        return "OK"; // new Greeting(counter.incrementAndGet(), String.format(template, name));
    }



}
