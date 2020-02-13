package tablas.dao;

import soporte.base.BaseDao;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import soporte.base.ConexionSVNBDBean;
import soporte.conexion.Conexion;
import tablas.bean.ColumnaBean;
import tablas.bean.TablasBean;
import soporte.base.BaseDao;
import java.io.File;


public class TablasDao extends BaseDao{

	Connection conexion = null;
    private int numeroColumnas;
    private String cadenaRespuesta;
    static TablasDao tablasDaoImpl;
    BaseDao baseDaoImpl;
    ConexionSVNBDBean conexionSVNBDBean;
	
	
	public TablasDao(){
    }
	
	/*
	public CamposTablaBean consultaCampos(String nombreTabla){
		CamposTablaBean camposBean = new CamposTablaBean();
		
		String query = "select max(character_length(COLUMN_NAME)) as LargoParametro, "+
				"max(character_length(COLUMN_TYPE)) as LargoTipoDato "+  
				"from information_schema.COLUMNS where "+
				"TABLE_SCHEMA = '"+conexionSVNBDBean.getDatabase()+"' and TABLE_NAME = '"+nombreTabla+"';";
		
		camposBean = (CamposTablaBean) baseDaoImpl.consulta(query, camposBean);
		
		return camposBean;
		
	}
	
	public CreateTableBean getCreateTable(String nombreTabla){
		CreateTableBean createTableBean = new CreateTableBean();
		String query = "show create table `"+nombreTabla+"`;";
		
		createTableBean = (CreateTableBean) baseDaoImpl.consulta(query, createTableBean);
		
		return createTableBean;
	}
	
	
	public List listaTablasReplica() {
		
		String query ="select TABLE_NAME  from information_schema.TABLES "+
						"WHERE TABLE_SCHEMA='"+conexionSVNBDBean.getDatabase()+"' "+
						"and (UPDATE_TIME between '"+conexionSVNBDBean.getLast_checkout()+
						"' and '"+conexionSVNBDBean.getNow()+"' "+
						"or CREATE_TIME between '"+conexionSVNBDBean.getLast_checkout()+
						"' and '"+conexionSVNBDBean.getNow()+"');";
		
		List listaRespuesta= new ArrayList();
		ReplicaTablaBean replicaTablaBean= new ReplicaTablaBean();
		
		try {
			listaRespuesta = baseDaoImpl.lista(query, listaRespuesta, replicaTablaBean);
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		return listaRespuesta;
	}
	

	
	


*/
	public TablasBean consultaTabla(TablasBean tablasBean){
		
		String query = "select TABLE_NAME as Nombre,replace(replace(TABLE_COMMENT,'\\n',' '),'\\t',' ') as Comentario\n" + 
				"from information_schema.TABLES where TABLE_SCHEMA = 'microfin_bienestar_enero'\n" + 
				"and TABLE_NAME = '"+tablasBean.getNombre()+"';";
		
		System.out.println("Q:"+query);
		tablasBean = (TablasBean) baseDaoImpl.consulta(query, tablasBean);
		
		return tablasBean;
	}
	
	public List consultaCampos(TablasBean tablasBean){
		ColumnaBean columnaBean = new ColumnaBean();
		List campos = new ArrayList();
		
		String query = "call FAB_GENERA_DATA_DICTIONARY('"+tablasBean.getNombre()+"','microfin_bienestar_enero');";
		
		try {
			
			campos =  baseDaoImpl.lista(query, campos, columnaBean);
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return campos;
		
	}
	
	
	public List listaTablasGIT(TablasBean tablasBean) {
		List listaRespuesta = new ArrayList();

		TablasBean tablaBean = null;
		File carpeta = new File(tablasBean.getRutaGit());
		String[] listado = carpeta.list();
		if (listado != null || listado.length != 0) {
			for (int i=0; i< listado.length; i++) {
				tablaBean = new TablasBean();
						
				tablaBean.setNombre(listado[i].replace(".sql", "").replace(".SQL", ""));
				listaRespuesta.add(tablaBean);
				
		      }
		}
		
		return listaRespuesta;
	}
	
	public List listaTablasCompleta() {
		TablasBean tablasBean = new TablasBean();
		tablasBean.setRutaGit("/Volumes/vmartinez/GIT/Bienestar/microfin/bd/TABLAS/");
		List<TablasBean> lista = listaTablasGIT(tablasBean);
		List<TablasBean> listaCompleta = new ArrayList();
		List listaColumnas = new ArrayList();
		
		System.out.println("Lista Tablas: "+lista.size());
		
		for(TablasBean bean:lista) {
			System.out.println(" Consulta Tabla: "+bean.getNombre());
			tablasBean = consultaTabla(bean);
			listaColumnas = consultaCampos(tablasBean);
			tablasBean.setColumnas(listaColumnas);
			listaCompleta.add(tablasBean);
		}
		
		System.out.println("Inicia Impresión; \n\n");
		
		
		
		return listaCompleta;
	}
	
	public static TablasDao getInstance(){
		
		if (tablasDaoImpl == null){
			tablasDaoImpl = new TablasDao();
        }

        return tablasDaoImpl;
		
	}
	
	public void setConexion(ConexionSVNBDBean conexionSVNBDBean) {
		this.conexionSVNBDBean = conexionSVNBDBean;
		baseDaoImpl = BaseDao.getInstance();
		System.out.println("Tabla Dao -->");
		baseDaoImpl.setConexion( conexionSVNBDBean );
		
	}

	
	public static void main(String arg[]) {
		TablasDao tablasDao = new TablasDao();
		
		ConexionSVNBDBean conexionBean = new ConexionSVNBDBean();

		conexionBean.setHost("127.0.0.1");
        conexionBean.setPort("3306");
        conexionBean.setDatabase("microfin_bienestar_enero");
        conexionBean.setUser("root");
        conexionBean.setPassword("ws2ws");
        conexionBean.setNombreConexion("PruebaDT");
        
		new Conexion(conexionBean);
		
		tablasDao.setConexion(conexionBean);
		System.out.println("Conexion Iniciada: "+conexionBean.getNombreConexion());
		TablasBean tablasBean = new TablasBean();
		tablasBean.setRutaGit("/Volumes/vmartinez/GIT/Bienestar/microfin/bd/TABLAS/");
		List<TablasBean> lista = tablasDao.listaTablasGIT(tablasBean);
		List<TablasBean> listaCompleta = new ArrayList();
		List listaColumnas = new ArrayList();
		
		System.out.println("Lista Tablas: "+lista.size());
		
		for(TablasBean bean:lista) {
			System.out.println(" Consulta Tabla: "+bean.getNombre());
			tablasBean = tablasDao.consultaTabla(bean);
			listaColumnas = tablasDao.consultaCampos(tablasBean);
			tablasBean.setColumnas(listaColumnas);
			listaCompleta.add(tablasBean);
		}
		
		System.out.println("Inicia Impresión; \n\n");
		
		for(TablasBean bean:listaCompleta) {
			System.out.println("\n\n-- --------------------------------------------------- --");
			System.out.println("Nombre:        "+bean.getNombre());
			System.out.println("Descripcion:   "+bean.getComentario());
			System.out.println("-- --------------------------------------------------- --");
			
			for(ColumnaBean colbean:bean.getColumnas()) {
			
				System.out.println(colbean.getColumna()+"\t"+colbean.getTipoDato());
			
			}
			
		}
		
		
	}
	
	
	
}

