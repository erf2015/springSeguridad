package uy.gub.imm.spring.servicios;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.HtmlExporter;
import net.sf.jasperreports.engine.export.JRCsvExporter;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.engine.export.ooxml.JRDocxExporter;
import net.sf.jasperreports.engine.export.ooxml.JRXlsxExporter;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleHtmlExporterOutput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;
import net.sf.jasperreports.export.SimplePdfExporterConfiguration;
import net.sf.jasperreports.export.SimplePdfReportConfiguration;
import net.sf.jasperreports.export.SimpleWriterExporterOutput;
import net.sf.jasperreports.export.SimpleXlsExporterConfiguration;
import net.sf.jasperreports.export.SimpleXlsxReportConfiguration;
import net.sf.jasperreports.export.WriterExporterOutput;
import uy.gub.imm.spring.jpa.StmEntidad;
import uy.gub.imm.spring.repositorios.StmEntidadRepositorio;

@Service
public class ServicioReporteEntidades {

	private static final Logger logger = LoggerFactory.getLogger(ServicioReporteEntidades.class);

	@Autowired
	private StmEntidadRepositorio repoEntidades;

	public String exportarEntidadesLocal(String formato) {
		logger.info("exportarEntidades BEGIN " + formato);
		List<StmEntidad> entidades = repoEntidades.findAll();
		String destFileName = "/home/fernando/Escritorio";
		String mensaje = "";
		try {
			File inputStreamFile = ResourceUtils.getFile("classpath:entidades_report.jrxml");
			logger.info("exportarEntidades INFO fichero cargado path absoluto: " + inputStreamFile.getAbsolutePath());
			JasperReport jasperReport = JasperCompileManager.compileReport(inputStreamFile.getAbsolutePath());
			logger.info("exportarEntidades INFO fichero cargado y compilado");
			JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(entidades);
			logger.info("exportarEntidades INFO collección de datos asociados al fichero de reporte");
			Map<String, Object> parameters = new HashMap<>();
			parameters.put("creado", "Fernando");
			JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, dataSource);
			switch (formato) {
			case "pdf":
				logger.info("exportarEntidades INFO Generando PDF");
				JRPdfExporter pdf = new JRPdfExporter();
				pdf.setExporterInput(new SimpleExporterInput(jasperPrint));
				pdf.setExporterOutput(new SimpleOutputStreamExporterOutput("entidadesReporte.pdf"));
				SimplePdfReportConfiguration reportConfig_PDF = new SimplePdfReportConfiguration();
				reportConfig_PDF.setSizePageToContent(true);
				reportConfig_PDF.setForceLineBreakPolicy(false);
				SimplePdfExporterConfiguration exportConfig = new SimplePdfExporterConfiguration();
				exportConfig.setMetadataAuthor("Fernando");
				exportConfig.setEncrypted(true);
				exportConfig.setAllowedPermissionsHint("PRINTING");
				pdf.setConfiguration(exportConfig);
				pdf.exportReport();
				JasperExportManager.exportReportToPdfFile(jasperPrint, destFileName + "/entidadesReporte.pdf");
				mensaje = "Exportado el fichero en " + destFileName + "/entidadesReporte.pdf";
				logger.info("exportarEntidades INFO Generado PDF");
				break;
			case "xls":
				logger.info("exportarEntidades INFO Generando XLS ");
				JRXlsxExporter xls = new JRXlsxExporter();
				SimpleXlsxReportConfiguration reportConfig_XLS = new SimpleXlsxReportConfiguration();
				reportConfig_XLS.setSheetNames(new String[] { "Entidades STM" });
				xls.setConfiguration(reportConfig_XLS);
				xls.exportReport();
				mensaje = "Exportado el fichero en " + destFileName + "/entidadesReporte.xls";
				logger.info("exportarEntidades INFO Generado XLS");
				break;
			case "html":
				logger.info("exportarEntidades INFO Generando HTML");
				HtmlExporter html = new HtmlExporter();
				html.setExporterInput(new SimpleExporterInput(jasperPrint));
				html.setExporterOutput(new SimpleHtmlExporterOutput("entidadesReporte.html"));
				html.exportReport();
				JasperExportManager.exportReportToHtmlFile(jasperPrint, destFileName + "/entidadesReporte.html");
				mensaje = "Exportado el fichero en " + destFileName + "/entidadesReporte.html";
				logger.info("exportarEntidades INFO Generado HTML");
				break;
			default:// csv
				logger.info("exportarEntidades INFO Generando CSV");
				JRCsvExporter csv = new JRCsvExporter();
				csv.setExporterOutput(new SimpleWriterExporterOutput("entidadesReporte.csv"));
				csv.exportReport();
				mensaje = "Exportado el fichero en " + destFileName + "/entidadesReporte.csv";
				logger.info("exportarEntidades INFO Generado CSV");
				break;
			}
		} catch (FileNotFoundException | JRException e) {
			logger.info("exportarEntidades ERROR al cargar el fichero " + e.getMessage());
			mensaje = "Error " + e.getMessage();
		}
		return mensaje;
	}

	/**
	 * Este funciona bien exporta con descarga, hay que ver para que descargue a
	 * otros formatos y además comenzar a acomodar los estilos dentro del documento.
	 * 
	 * @param formato
	 * @param out
	 * @throws IOException
	 */
	public void descargarReporte(String formato, OutputStream out) throws IOException {
		logger.info("otroExporter BEGIN " + formato);
		List<StmEntidad> entidades = repoEntidades.findAll();
		try {
			// File inputStreamFile =
			// ResourceUtils.getFile("classpath:entidades_report.jrxml");
			File inputStreamFile = ResourceUtils.getFile("classpath:tabla_logo.jrxml");
			logger.info("otroExporter INFO fichero cargado path absoluto: " + inputStreamFile.getAbsolutePath());
			JasperReport jasperReport = JasperCompileManager.compileReport(inputStreamFile.getAbsolutePath());
			logger.info("otroExporter INFO fichero cargado y compilado");
			JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(entidades);
			logger.info("otroExporter INFO collección de datos asociados al fichero de reporte");
			Map<String, Object> parameters = new HashMap<>();
			parameters.put("creado", "Fernando");
			URL url = ResourceUtils.getURL("classpath:consultaGL-STM.png");
			parameters.put("logoUrl",url);
			parameters.put("titulo", "Nuevos valores titulo");
			parameters.put("tituloDos", "Del segundo titulo");
			//parameters.put("logo", ClassLoader.getSystemResourceAsStream("classpath:error-404-monochrome.svg"));
			JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, dataSource);
			if (formato.equalsIgnoreCase("pdf")) {
				logger.info("otroExporter INFO Generando PDF");
				JRPdfExporter pdf = new JRPdfExporter();
				pdf.setExporterInput(new SimpleExporterInput(jasperPrint));
				pdf.setExporterOutput(new SimpleOutputStreamExporterOutput("entidadesReporte.pdf"));
				SimplePdfReportConfiguration reportConfig_PDF = new SimplePdfReportConfiguration();
				reportConfig_PDF.setSizePageToContent(true);
				reportConfig_PDF.setForceLineBreakPolicy(false);
				SimplePdfExporterConfiguration exportConfig = new SimplePdfExporterConfiguration();
				exportConfig.setMetadataAuthor("Fernando");
				exportConfig.setEncrypted(true);
				exportConfig.setAllowedPermissionsHint("PRINTING");
				pdf.setConfiguration(exportConfig);
				pdf.exportReport();
				JasperExportManager.exportReportToPdfStream(jasperPrint, out);
				logger.info("otroExporter SUCCESS Generado PDF");
			} else if (formato.equalsIgnoreCase("xls")) {
				logger.info("otroExporter INFO Generando XLS");
				JRXlsxExporter xls = new JRXlsxExporter();
				xls.setExporterInput(new SimpleExporterInput(jasperPrint));
				xls.setExporterOutput(new SimpleOutputStreamExporterOutput(out));
				SimpleXlsxReportConfiguration reportConfig_XLS = new SimpleXlsxReportConfiguration();
				reportConfig_XLS.setSheetNames(new String[] { "Entidades STM" });
				reportConfig_XLS.setOnePagePerSheet(false);
				reportConfig_XLS.setRemoveEmptySpaceBetweenRows(true);
				reportConfig_XLS.setDetectCellType(true);
				reportConfig_XLS.setWhitePageBackground(false);
				xls.setConfiguration(reportConfig_XLS);
				xls.exportReport();
				logger.info("otroExporter SUCCESS Generado XLS");
			} else if (formato.equalsIgnoreCase("csv")) {
				logger.info("otroExporter INFO Generando CSV");
				JRCsvExporter csv = new JRCsvExporter();
				csv.setExporterInput(new SimpleExporterInput(jasperPrint));
				csv.setExporterOutput(new SimpleWriterExporterOutput(out));
				csv.exportReport();
				logger.info("otroExporter SUCCESS Generado CSV");
			} else if (formato.equalsIgnoreCase("doc")) {
				logger.info("otroExporter INFO Generando DOC");
				JRDocxExporter doc = new JRDocxExporter();
				doc.setExporterInput(new SimpleExporterInput(jasperPrint));
				doc.setExporterOutput(new SimpleOutputStreamExporterOutput(out));
				doc.exportReport();
				logger.info("otroExporter SUCCESS Generado DOC");
			} else {// html
				logger.info("otroExporter WARNING no implementado html");
			}
		} catch (FileNotFoundException | JRException e) {
			logger.info("otroExporter Error " + e.getMessage());
		}
	}

}