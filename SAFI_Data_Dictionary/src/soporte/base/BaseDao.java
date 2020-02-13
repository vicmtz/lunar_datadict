package soporte.base;


import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import soporte.base.ConexionSVNBDBean;
import soporte.conexion.Conexion;

public class BaseDao {

	Connection conexion = null;
    private int numeroColumnas;
    private String cadenaRespuesta;
    static BaseDao baseDaoImpl = null;
    ConexionSVNBDBean conexionSVNBDBean;
	
	public BaseDao(){
    }
	
	public Object consulta(String query,Object resultado) {
				
         PreparedStatement st;

         try {
        	 System.out.println("Dat; "+conexionSVNBDBean.getNombreConexion());
        	conexion = Conexion.getConnection(conexionSVNBDBean);
        	
			st = conexion.prepareStatement(query);
			ResultSet rs = st.executeQuery();
			
			numeroColumnas = rs.getMetaData().getColumnCount();
			cadenaRespuesta = "";
			Method[] metodos=resultado.getClass().getMethods();
			
			String campoBD = "";
			while(rs.next()){
				
				for(int col = 1; col <= numeroColumnas ; col++){
                    cadenaRespuesta+=rs.getMetaData().getColumnName(col)+":"+rs.getString(col)+"\n";
                    campoBD = rs.getMetaData().getColumnName(col);
                    campoBD = campoBD.substring(0, 1).toUpperCase() + campoBD.substring(1);
                    campoBD = campoBD.replace(" ", "");
                    
                    for(Method m: metodos) {
                                                 
                        if (m.getName().equals("set"+campoBD)) {
                             
                            try {
                            	
                                m.invoke(resultado,rs.getString(col));
                                
                            } catch (IllegalAccessException  e) {
                                // TODO Auto-generated catch block
                                e.printStackTrace();
                            } catch (IllegalArgumentException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							} catch (InvocationTargetException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
                        }
                         
                    }
                    
                }
				
			}
			conexion.close();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			try {
				conexion.close();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}  
        
        return resultado;
         
	}
	
	
	public List lista(String query,List lista,Object resultado ) throws InstantiationException, IllegalAccessException{
		
        PreparedStatement st;

        try {
        	conexion = Conexion.getConnection(conexionSVNBDBean);
			st = conexion.prepareStatement(query);
			ResultSet rs = st.executeQuery();
			
			numeroColumnas = rs.getMetaData().getColumnCount();
			cadenaRespuesta = "";
			Method[] metodos=resultado.getClass().getMethods();
			
			String campoBD = "";
			while(rs.next()){
				
				resultado = resultado.getClass().newInstance();
				
				for(int col = 1; col <= numeroColumnas ; col++){
                   cadenaRespuesta+=rs.getMetaData().getColumnName(col)+":"+rs.getString(col)+"\n";
                   campoBD = rs.getMetaData().getColumnName(col);
                   campoBD = campoBD.substring(0, 1).toUpperCase() + campoBD.substring(1);
                   campoBD = campoBD.replace(" ", "");
                   
                   for(Method m: metodos) {
                       if (m.getName().equals("set"+campoBD)) {
                            
                           try {
                           	
                               m.invoke(resultado,rs.getString(col));
                               
                           } catch (IllegalAccessException  e) {
                               // TODO Auto-generated catch block
                               e.printStackTrace();
                           } catch (IllegalArgumentException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							} catch (InvocationTargetException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
                       }
                        
                   }
                   
               }
				
			   lista.add(resultado);
				
			}
			
			conexion.close();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			try {
				conexion.close();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}  
       
       return lista;
        
	}
	
	public static BaseDao getInstance(){
		if(baseDaoImpl == null){
			baseDaoImpl = new BaseDao();
		}
		
		return baseDaoImpl;
	}

	public Connection getConexion() {
		return conexion;
	}

	public void setConexion(ConexionSVNBDBean conexionSVNBDBean) {
		System.out.println("Base Dao -->");
		this.conexionSVNBDBean = conexionSVNBDBean;
	}
	
	
	
}