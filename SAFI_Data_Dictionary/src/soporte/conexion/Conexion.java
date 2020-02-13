package soporte.conexion;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.HashMap;

import com.mchange.v2.c3p0.DataSources;
import com.mchange.v2.c3p0.PooledDataSource;
import javax.sql.DataSource;

import soporte.base.ConexionSVNBDBean;


/**
 *
 * @author victor
 */
public class Conexion {
    
    private static Connection conn = null;
    
    private String driver;
    private String url;
    private static String host;
    private static String port;
    private static String database;
    private static String usuario;
    private static String password;
    static PooledDataSource pooledDS = null;
 
    public Conexion(ConexionSVNBDBean conexionBean){
        
        this.host 		= conexionBean.getHost();
        this.port		= conexionBean.getPort();
        this.database 	= conexionBean.getDatabase();
        this.usuario 	= conexionBean.getUser();
        this.password 	= conexionBean.getPassword();
        
        driver = "com.mysql.jdbc.Driver";
        
        url = "jdbc:mysql://"+host+":"+port+"/"+database;
        
        try{
            Class.forName(driver);
            DataSource unpooled = DataSources.unpooledDataSource((String)("jdbc:mysql://" + host + ":" + port + "/" + database + "?autoReconnect=true"), (String)usuario, (String)password);
            HashMap<String, Integer> overrideProps = new HashMap<String, Integer>();
            overrideProps.put("maxPoolSize", 6);
            overrideProps.put("minPoolSize", 1);
            pooledDS = (PooledDataSource)DataSources.pooledDataSource((DataSource)unpooled, overrideProps);
            ConexionOrigenDatos.getConexion().put(conexionBean.getNombreConexion(), pooledDS);
                       
            System.out.println("Conectado a: "+database);
        }
        catch(SQLException e){
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
    }
    
    public static Connection getConnection(ConexionSVNBDBean conexionBean){
    	try {
	        if (!ConexionOrigenDatos.getConexion().containsKey(conexionBean.getNombreConexion())){
	        	System.out.println("No  Existe, se Agrega Conexion");
	            new Conexion(conexionBean);                        
	        }else {
	        	System.out.println("Conexion Existe");
	        }
	        
	        pooledDS = (PooledDataSource)ConexionOrigenDatos.getConexion().get(conexionBean.getNombreConexion());
	        
	        	
			conn = pooledDS.getConnection();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
        return conn;
    }
     
    
    
    
}
