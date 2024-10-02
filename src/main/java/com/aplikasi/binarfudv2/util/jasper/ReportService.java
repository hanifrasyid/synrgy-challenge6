package com.aplikasi.binarfudv2.util.jasper;

import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.export.JRXlsExporterParameter;
import net.sf.jasperreports.engine.export.ooxml.JRDocxExporter;
import net.sf.jasperreports.engine.export.ooxml.JRXlsxExporter;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Map;

@Service
public class ReportService {

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Autowired
    DataSource dataSource;

    public byte[] generate_pdf(Map<String, Object> parameters, String reportName, String fileName) throws SQLException {
        try {
            JasperReport report = JasperCompileManager.compileReport(reportName);
            JasperPrint jasperPrint = JasperFillManager
                    .fillReport
                            (report,
                                    parameters,
                                    jdbcTemplate.getDataSource().getConnection());
//            JasperPrint jasperPrint = JasperFillManager
//                    .fillReport
//                            (reportName,
//                                    parameters,
//                                    dataSource.getConnection());
            JasperExportManager.exportReportToPdfFile(jasperPrint, "./cdn/"+ fileName+".pdf");
            byte[] result = JasperExportManager.exportReportToPdf(jasperPrint) ;
            return result;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }finally {
            //close koneksi
            jdbcTemplate.getDataSource().getConnection().close();
        }
    }

    public byte[] generateDocx(Map<String, Object> parameters, String reportName, String fileName) throws SQLException {
        try {
            JasperReport report = JasperCompileManager.compileReport(reportName);
            JasperPrint jasperPrint = JasperFillManager
                    .fillReport
                            (report,
                                    parameters,
                                    jdbcTemplate.getDataSource().getConnection());
//            JasperExportManager.exportReportToPdfFile(jasperPrint, "./cdn/out.pdf");
            JRDocxExporter exporter = new JRDocxExporter();
            exporter.setExporterInput(new SimpleExporterInput(jasperPrint));
            File exportReportFile = new File("./cdn/"+ fileName +".docx");
            exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(exportReportFile));
            exporter.exportReport();
            byte[] result = JasperExportManager.exportReportToPdf(jasperPrint) ;
            return result;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }finally {
            //close koneksi
            jdbcTemplate.getDataSource().getConnection().close();
        }
    }


    public byte[] generateHtml(Map<String, Object> parameters, String reportName, String fileName) {
        try {
            JasperReport report = JasperCompileManager.compileReport(reportName);

            JasperPrint jasperPrint = JasperFillManager.fillReport(report, parameters,jdbcTemplate.getDataSource().getConnection());
            //perbedaan hanya disini
            JasperExportManager.exportReportToHtmlFile(jasperPrint, "./cdn/"+ fileName +".html");
            byte[] result = JasperExportManager.exportReportToPdf(jasperPrint) ;
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public ResponseEntity generate_excel(Map<String, Object> parameters, String reportName, String fileName) throws SQLException {
        Connection connection = null;
        byte[] data = new byte[0];
        HttpHeaders httpHeaders = new HttpHeaders();
        try {
            connection = dataSource.getConnection();
            httpHeaders.set( HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + reportName + ".xlsx" );

            JasperReport report = JasperCompileManager.compileReport(reportName);

            // Fill the report
            JasperPrint jasperPrint = JasperFillManager.fillReport(report, parameters,jdbcTemplate.getDataSource().getConnection());

            JRXlsxExporter exporter = new JRXlsxExporter();

            ByteArrayOutputStream byteArrayOutputStream=new ByteArrayOutputStream();
            exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
            exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, byteArrayOutputStream);
            exporter.setParameter(JRXlsExporterParameter.IS_ONE_PAGE_PER_SHEET, Boolean.TRUE);
            exporter.setParameter(JRXlsExporterParameter.IS_WHITE_PAGE_BACKGROUND, Boolean.FALSE);
            exporter.setParameter(JRXlsExporterParameter.IS_REMOVE_EMPTY_SPACE_BETWEEN_ROWS, Boolean.TRUE);
            exporter.exportReport();
            data = byteArrayOutputStream.toByteArray();
            OutputStream out = new FileOutputStream("./cdn/"+ fileName +".xlsx");
            out.write(data);
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            if(connection != null) {
                connection.close();
                connection = null;
            }
        }
        return new ResponseEntity(data, httpHeaders, HttpStatus.OK );
    }

    public ResponseEntity generateCSV(Map<String, Object> parameters, String reportName, String fileName) throws SQLException {
        Connection connection = null;
        byte[] data = new byte[0];
        HttpHeaders httpHeaders = new HttpHeaders();
        try {
            connection = dataSource.getConnection();
            httpHeaders.set( HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + reportName + ".csv" );

            JasperReport report = JasperCompileManager.compileReport(reportName);

            // Fill the report
            JasperPrint jasperPrint = JasperFillManager.fillReport(report, parameters,jdbcTemplate.getDataSource().getConnection());

            JRXlsxExporter exporter = new JRXlsxExporter();

            ByteArrayOutputStream byteArrayOutputStream=new ByteArrayOutputStream();
            exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
            exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, byteArrayOutputStream);
            exporter.setParameter(JRXlsExporterParameter.IS_ONE_PAGE_PER_SHEET, Boolean.TRUE);
            exporter.setParameter(JRXlsExporterParameter.IS_WHITE_PAGE_BACKGROUND, Boolean.FALSE);
            exporter.setParameter(JRXlsExporterParameter.IS_REMOVE_EMPTY_SPACE_BETWEEN_ROWS, Boolean.TRUE);
            exporter.exportReport();
            data = byteArrayOutputStream.toByteArray();
            OutputStream out = new FileOutputStream("./cdn/"+ fileName +".csv");
            out.write(data);
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            if(connection != null) {
                connection.close();
                connection = null;
            }
        }
        return new ResponseEntity(data, httpHeaders, HttpStatus.OK );
    }
}