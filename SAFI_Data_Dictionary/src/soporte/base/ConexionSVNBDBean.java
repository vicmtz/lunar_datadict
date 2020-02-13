package soporte.base;


public class ConexionSVNBDBean extends BaseBean {
	
	private String nombreConexion;
	private String host;
	private String port;
	private String database;
	private String user;
	private String password;
	private String svn_path;
	private String last_checkout;
	private String now;
	
	public String getHost() {
		return host;
	}
	public void setHost(String host) {
		this.host = host;
	}
	public String getPort() {
		return port;
	}
	public void setPort(String port) {
		this.port = port;
	}
	public String getDatabase() {
		return database;
	}
	public void setDatabase(String database) {
		this.database = database;
	}
	public String getUser() {
		return user;
	}
	public void setUser(String user) {
		this.user = user;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getSvn_path() {
		return svn_path;
	}
	public void setSvn_path(String svn_path) {
		this.svn_path = svn_path;
	}
	
	public String getNombreConexion() {
		return nombreConexion;
	}
	public void setNombreConexion(String nombreConexion) {
		this.nombreConexion = nombreConexion;
	}
	public String getLast_checkout() {
		return last_checkout;
	}
	public void setLast_checkout(String last_checkout) {
		this.last_checkout = last_checkout;
	}
	public String getNow() {
		return now;
	}
	public void setNow(String now) {
		this.now = now;
	}
	
	
	
	
}
