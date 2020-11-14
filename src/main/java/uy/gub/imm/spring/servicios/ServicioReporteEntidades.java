package uy.gub.imm.spring.servicios;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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
import net.sf.jasperreports.engine.export.ooxml.JRXlsxExporter;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleHtmlExporterOutput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;
import net.sf.jasperreports.export.SimplePdfExporterConfiguration;
import net.sf.jasperreports.export.SimplePdfReportConfiguration;
import net.sf.jasperreports.export.SimpleWriterExporterOutput;
import net.sf.jasperreports.export.SimpleXlsxReportConfiguration;
import uy.gub.imm.spring.jpa.StmEntidad;
import uy.gub.imm.spring.repositorios.StmEntidadRepositorio;

@Service
public class ServicioReporteEntidades {

	private static final Logger logger = LoggerFactory.getLogger(ServicioReporteEntidades.class);

	@Autowired
	private StmEntidadRepositorio repoEntidades;

	public String exportarEntidades(String formato) {
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
	 */
	public void otroExporter(String formato, OutputStream out) {
		logger.info("otroExporter BEGIN " + formato);
		List<StmEntidad> entidades = repoEntidades.findAll();
		try {
			File inputStreamFile = ResourceUtils.getFile("classpath:entidades_report.jrxml");
			logger.info("otroExporter INFO fichero cargado path absoluto: " + inputStreamFile.getAbsolutePath());
			JasperReport jasperReport = JasperCompileManager.compileReport(inputStreamFile.getAbsolutePath());
			logger.info("otroExporter INFO fichero cargado y compilado");
			JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(entidades);
			logger.info("otroExporter INFO collección de datos asociados al fichero de reporte");
			Map<String, Object> parameters = new HashMap<>();
			parameters.put("creado", "Fernando");
			JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, dataSource);
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
			logger.info("otroExporter INFO Generado PDF");
		} catch (FileNotFoundException | JRException e) {
			logger.info("otroExporter Error " + e.getMessage());
		}
	}

}
