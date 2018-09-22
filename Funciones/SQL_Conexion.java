package manzano.utj.sistemafluxing.Funciones;

/**
 * Created by jorge on 12/01/2018.
 */

import android.annotation.SuppressLint;
import android.os.StrictMode;
import android.util.Log;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

//Fluxing.ddns.net:1433  -- Remota
//192.168.15.131:1433   -- local

public class SQL_Conexion {

    private String IP, DB, User, Password;

    @SuppressLint("NewApi")
    public Connection ConnectSQL() {

        IP = "Fluxing.ddns.net:1433";
        DB = "Fluxing_Viaticos";
        User = "sa";
        Password = "Flux1ng2017";
        Connection connection = null;

        try {

            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
            String ConnectionURL = "jdbc:jtds:sqlserver://" + IP + ";databaseName=" + DB + ";user=" + User + ";password=" + Password + ";loginTimeout=2;socketTimeout=2";
            Class.forName("net.sourceforge.jtds.jdbc.Driver");
            connection = DriverManager.getConnection(ConnectionURL);

        } catch (SQLException se) {
            Log.e("error here 1 : ", se.getMessage());
        } catch (ClassNotFoundException e) {
            Log.e("error here 2 : ", e.getMessage());
        } catch (Exception e) {
            Log.e("error here 3 : ", e.getMessage());
        }
        return connection;
    }

    public Boolean Validate_Connection() {
        Boolean Connected;

        if (ConnectSQL() != null) {
            Connected = true;
        } else {
            Connected = false;
        }
        return Connected;
    }

}





