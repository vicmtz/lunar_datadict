package tablas.bean;

import java.util.List;

import soporte.base.BaseBean;

public class TablasBean extends BaseBean {

	private String rutaGit;
	private String nombre;
	private String comentario;
	private List<ColumnaBean> columnas;
	
	public String getRutaGit() {
		return rutaGit;
	}
	public void setRutaGit(String rutaGit) {
		this.rutaGit = rutaGit;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getComentario() {
		return comentario;
	}
	public void setComentario(String comentario) {
		this.comentario = comentario;
	}
	public List<ColumnaBean> getColumnas() {
		return columnas;
	}
	public void setColumnas(List columnas) {
		this.columnas = columnas;
	}

	
	
	
}
