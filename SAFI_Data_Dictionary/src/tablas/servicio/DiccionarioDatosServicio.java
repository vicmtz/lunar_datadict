package tablas.servicio;

import soporte.base.BaseServicio;
import soporte.base.ConexionSVNBDBean;
import soporte.conexion.Conexion;
import tablas.bean.ColumnaBean;
import tablas.bean.TablasBean;
import tablas.dao.TablasDao;

import java.awt.Color;
import java.io.File;
import java.io.FileOutputStream;
import java.util.Calendar;
import java.util.List;

import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFColor;
import org.apache.poi.xssf.usermodel.XSSFDataFormat;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class DiccionarioDatosServicio extends BaseServicio{
	
	TablasDao tablaDao = null;

	public DiccionarioDatosServicio() {
		super();
	}
	
	public void generaDiccionarioDatos() {
		String mesEnLetras	= "";
		String anio		= "";
		
		tablaDao = TablasDao.getInstance();
		ConexionSVNBDBean conexionBean = new ConexionSVNBDBean();

		conexionBean.setHost("127.0.0.1");
        conexionBean.setPort("3306");
        conexionBean.setDatabase("microfin_bienestar_enero");
        conexionBean.setUser("root");
        conexionBean.setPassword("ws2ws");
        conexionBean.setNombreConexion("PruebaDT");
        
		new Conexion(conexionBean);
		tablaDao.setConexion(conexionBean);
		
		List<TablasBean> listaTablas = tablaDao.listaTablasCompleta();


		int regExport = 0;
	
		try {

			XSSFWorkbook libro = new XSSFWorkbook();
			
			//Crea un Fuente Negrita con tamaño 8 para informacion del reporte.
			XSSFFont fuenteNegrita8= libro.createFont();
			fuenteNegrita8.setFontHeightInPoints((short)9);
			fuenteNegrita8.setFontName("Negrita");
			fuenteNegrita8.setBoldweight(XSSFFont.BOLDWEIGHT_BOLD);
			fuenteNegrita8.setColor(IndexedColors.WHITE.getIndex());
			
			
			XSSFCellStyle estiloNeg8 = libro.createCellStyle();
			estiloNeg8.setFont(fuenteNegrita8);
			
			// Negrita 10 centrado
			XSSFFont centradoNegrita10 = libro.createFont();
			centradoNegrita10.setFontHeightInPoints((short)9);
			centradoNegrita10.setFontName("Negrita");
			centradoNegrita10.setBoldweight(XSSFFont.BOLDWEIGHT_BOLD);
			XSSFCellStyle estiloNegCentrado10 = libro.createCellStyle();
			estiloNegCentrado10.setFont(centradoNegrita10);
			estiloNegCentrado10.setAlignment((short)XSSFCellStyle.ALIGN_CENTER);
			
			
			XSSFCellStyle estiloNegIzq10 = libro.createCellStyle();
			estiloNegIzq10.setFont(centradoNegrita10);
			estiloNegIzq10.setAlignment((short)XSSFCellStyle.ALIGN_LEFT);
			estiloNegIzq10.setFillForegroundColor(new XSSFColor( new Color(206, 238, 252)));
			estiloNegIzq10.setFillPattern(CellStyle.SOLID_FOREGROUND);
			
			XSSFCellStyle estiloNegIzq10NB = libro.createCellStyle();
			estiloNegIzq10NB.setAlignment((short)XSSFCellStyle.ALIGN_LEFT);
			estiloNegIzq10NB.setFillForegroundColor(new XSSFColor( new Color(206, 238, 252)));
			estiloNegIzq10NB.setFillPattern(CellStyle.SOLID_FOREGROUND);
			
			
			
			XSSFCellStyle estiloNegCentrado10Celda = libro.createCellStyle();
			estiloNegCentrado10Celda.setFont(fuenteNegrita8);
			estiloNegCentrado10Celda.setAlignment((short)XSSFCellStyle.ALIGN_CENTER);
			estiloNegCentrado10Celda.setBorderBottom((short)1);
			estiloNegCentrado10Celda.setBorderTop((short)1);
			estiloNegCentrado10Celda.setBorderLeft((short)1);
			estiloNegCentrado10Celda.setBorderRight((short)1);
			estiloNegCentrado10Celda.setFillForegroundColor(new XSSFColor( new Color(2, 66, 122)));
			estiloNegCentrado10Celda.setFillPattern(CellStyle.SOLID_FOREGROUND);
			
			XSSFCellStyle estilo10Celda = libro.createCellStyle();
			estilo10Celda.setBorderBottom((short)1);
			estilo10Celda.setBorderTop((short)1);
			estilo10Celda.setBorderLeft((short)1);
			estilo10Celda.setBorderRight((short)1);
			
			
			// Negrita 8 centrado
			XSSFFont centradoNegrita8= libro.createFont();
			centradoNegrita8.setFontHeightInPoints((short)8);
			centradoNegrita8.setFontName("Negrita");
			centradoNegrita8.setBoldweight(XSSFFont.BOLDWEIGHT_BOLD);
			XSSFCellStyle estiloNegCentrado8 = libro.createCellStyle();
			estiloNegCentrado8.setFont(centradoNegrita8);
			estiloNegCentrado8.setAlignment((short)XSSFCellStyle.ALIGN_CENTER);  
			estiloNegCentrado8.setVerticalAlignment((short)XSSFCellStyle.VERTICAL_CENTER);
						
			//Estilo Formato decimal (0.00)
			XSSFCellStyle estiloFormatoDecimal = libro.createCellStyle();
			XSSFDataFormat format = libro.createDataFormat();
			estiloFormatoDecimal.setDataFormat(format.getFormat("$#,##0.00"));

			XSSFCellStyle estiloDatosCentrado = libro.createCellStyle();
			estiloDatosCentrado.setAlignment((short)XSSFCellStyle.ALIGN_CENTER);  
			
			// Creacion de hoja
			XSSFSheet hoja = libro.createSheet("Diccionario Datos");
			XSSFRow fila= hoja.createRow(0);

			
			
			// Descripcion del Reporte
			fila	= hoja.createRow(1);	
			// Hora en que se genera el reporte
			fila = hoja.createRow(2);	
			XSSFCell celda=fila.createCell((short)1);
			
			
			XSSFCell celdaR=fila.createCell((short)0);
			celdaR	= fila.createCell((short)0);			
			celdaR.setCellStyle(estiloNegCentrado10);
			celdaR.setCellValue("DICCIONARIO DE DATOS");
			hoja.addMergedRegion(new CellRangeAddress(
		            0, //first row (0-based)
		            0, //last row  (0-based)
		            1, //first column (0-based)
		            7  //last column  (0-based)
		    ));
			
			// Encabezado del Reporte
			fila = hoja.createRow(3);	
			fila = hoja.createRow(4);	
			int numfila = 5;
			
			for(TablasBean beanTab:listaTablas) {
				
				System.out.println("Escribe Tabla: "+beanTab.getNombre());
				
				fila = hoja.createRow(numfila);
				celdaR= fila.createCell((short)0);			
				celdaR.setCellStyle(estiloNegIzq10);
				celdaR.setCellValue("Nombre Tabla: ");
	
				celdaR= fila.createCell((short)1);		
				celdaR.setCellStyle(estiloNegIzq10);
				celdaR.setCellValue(beanTab.getNombre());
				hoja.addMergedRegion(new CellRangeAddress(
						numfila, //first row (0-based)
						numfila, //last row  (0-based)
			            1, //first column (0-based)
			            8  //last column  (0-based)
			    ));
				
				numfila++;
				fila = hoja.createRow(numfila);
				
				celdaR= fila.createCell((short)0);			
				celdaR.setCellStyle(estiloNegIzq10);
				celdaR.setCellValue("Descripción:");

				
				celdaR= fila.createCell((short)1);	
				celdaR.setCellStyle(estiloNegIzq10NB);
				celdaR.setCellValue(beanTab.getComentario());
				hoja.addMergedRegion(new CellRangeAddress(
						numfila, //first row (0-based)
						numfila, //last row  (0-based)
			            1, //first column (0-based)
			            8  //last column  (0-based)
			    ));
				
				
				
				/* 
				 * ------- ENCABEZADOS ------------
				 */
				
				numfila++;
				fila = hoja.createRow(numfila);
				
				celdaR= fila.createCell((short)0);	
				celdaR.setCellStyle(estiloNegCentrado10Celda);
				celdaR.setCellValue("Columna");
				
				celdaR= fila.createCell((short)1);	
				celdaR.setCellStyle(estiloNegCentrado10Celda);
				celdaR.setCellValue("Tipo");
				
				celdaR= fila.createCell((short)2);	
				celdaR.setCellStyle(estiloNegCentrado10Celda);
				celdaR.setCellValue("Longitud");

				celdaR= fila.createCell((short)3);	
				celdaR.setCellStyle(estiloNegCentrado10Celda);
				celdaR.setCellValue("Nulo");
				
				celdaR= fila.createCell((short)4);	
				celdaR.setCellStyle(estiloNegCentrado10Celda);
				celdaR.setCellValue("Llave\nPrimaria");
				
				celdaR= fila.createCell((short)5);	
				celdaR.setCellStyle(estiloNegCentrado10Celda);
				celdaR.setCellValue("Llave\nForánea");
				
				celdaR= fila.createCell((short)6);	
				celdaR.setCellStyle(estiloNegCentrado10Celda);
				celdaR.setCellValue("Unico");
				
				celdaR= fila.createCell((short)7);	
				celdaR.setCellStyle(estiloNegCentrado10Celda);
				celdaR.setCellValue("Valor por\ndefecto");
				
				celdaR= fila.createCell((short)8);	
				celdaR.setCellStyle(estiloNegCentrado10Celda);
				celdaR.setCellValue("Comentario");

				
				System.out.println("Escribe Campos: "+beanTab.getNombre());
				for(ColumnaBean colbean:beanTab.getColumnas()) {
					
					
					numfila++;
					fila = hoja.createRow(numfila);
					
					celdaR= fila.createCell((short)0);	
					celdaR.setCellStyle(estilo10Celda);
					celdaR.setCellValue(colbean.getColumna());
					
					celdaR= fila.createCell((short)1);	
					celdaR.setCellStyle(estilo10Celda);
					celdaR.setCellValue(colbean.getDato());
					
					celdaR= fila.createCell((short)2);	
					celdaR.setCellStyle(estilo10Celda);
					celdaR.setCellValue(colbean.getLongitud());


					celdaR= fila.createCell((short)3);	
					celdaR.setCellStyle(estilo10Celda);
					celdaR.setCellValue(colbean.getIsNull());
					
					celdaR= fila.createCell((short)4);	
					celdaR.setCellStyle(estilo10Celda);
					celdaR.setCellValue(colbean.getPrimaryKey());
					
					celdaR= fila.createCell((short)5);	
					celdaR.setCellStyle(estilo10Celda);
					celdaR.setCellValue(colbean.getForeignKey());
					
					celdaR= fila.createCell((short)6);	
					celdaR.setCellStyle(estilo10Celda);
					celdaR.setCellValue(colbean.getUniqueKey());
					
					celdaR= fila.createCell((short)7);	
					celdaR.setCellStyle(estilo10Celda);
					celdaR.setCellValue(colbean.getDefaultVal());
					
					celdaR= fila.createCell((short)8);	
					celdaR.setCellStyle(estilo10Celda);
					celdaR.setCellValue(colbean.getComentario());
					
					
				
				}
				numfila++;
				numfila++;
				/*if(numfila >= 40) {
					break;
				}*/
			} 
			
			hoja.setColumnWidth(0, 18 * 256);	
			hoja.setColumnWidth(1, 15 * 256);	
			hoja.setColumnWidth(2, 10 * 256);	
			hoja.setColumnWidth(3, 9 * 256);	
			hoja.setColumnWidth(4, 9 * 256);	
			hoja.setColumnWidth(5, 9 * 256);
			hoja.setColumnWidth(6, 9 * 256);
			hoja.setColumnWidth(7, 15 * 256);	
			hoja.setColumnWidth(8, 60 * 256);	


			
			File archivo = new File("SAFI_Diccionario_Datos.xlsx");
			FileOutputStream salida = new FileOutputStream(archivo);
			
			hoja.getWorkbook().write(salida);
			
			System.out.println("Archivo Generado");
			
			}catch(Exception e){
	    		e.printStackTrace();
	    	}//Fin del catch
	}
	
	
	public static void main(String arg[]) {
		DiccionarioDatosServicio diccionario = new DiccionarioDatosServicio();
		diccionario.generaDiccionarioDatos();
	}
	
	
}
