import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class ConexionDB {
    
    private static final String URL_DB = "jdbc:sqlite:gestor_turnos.sqlite";
    
    public static Connection conectar() throws SQLException {
        try {
            Class.forName("org.sqlite.JDBC");
        } catch (ClassNotFoundException e) {
            throw new SQLException("Driver JDBC de SQLite no encontrado.", e);
        }
        return DriverManager.getConnection(URL_DB);
    }
    
    public static void inicializarBaseDeDatos() throws SQLException {
        
        try (Connection conn = conectar();
             Statement stmt = conn.createStatement()) {
            
            
            
        }    
}
    
}
