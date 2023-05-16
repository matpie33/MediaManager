package org.travelling.ticketer.pdfgenerator;

import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;
import org.springframework.stereotype.Component;
import org.travelling.ticketer.constants.Filenames;
import org.travelling.ticketer.dto.TicketPdfDTO;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

@Component
public class PdfExportManager {


    public static final String REPORT_NAME = "Tickets.jrxml";
    public static final String PARAMETER_NAME = "Name";
    public static final String PARAMETER_EMAIL = "Email";
    public static final String PARAMETER_TRAVEL_DATE = "TravelDate";
    public static final String PARAMETER_FROM_STATION = "FromStation";
    public static final String PARAMETER_TO_STATION = "ToStation";
    public static final String PARAMETER_TICKET_TYPE = "TicketType";
    public static final String PARAMETER_TRAIN = "Train";
    public static final String PARAMETER_TRAIN_TIME = "TrainTime";
    public static final String QR_CODE_PATH = "QrCodePath";

    public byte[] exportToPdf(TicketPdfDTO ticket) throws JRException {
        Map<String, Object> params = createReportParameters(ticket);
        JasperPrint jasperPrint = fillReport(params);
        return exportReport(jasperPrint).toByteArray();
    }

    private ByteArrayOutputStream exportReport(JasperPrint jasperPrint) throws JRException {
        JRPdfExporter jasperExporter = new JRPdfExporter();
        jasperExporter.setExporterInput(new SimpleExporterInput(jasperPrint));
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        jasperExporter.setExporterOutput(new SimpleOutputStreamExporterOutput(outputStream));
        jasperExporter.exportReport();
        return outputStream;
    }

    private JasperPrint fillReport(Map<String, Object> params) throws JRException {
        InputStream input = getClass().getClassLoader().getResourceAsStream(REPORT_NAME);
        JasperReport jasperReport = JasperCompileManager.compileReport(input);
        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, params, new JREmptyDataSource());
        return jasperPrint;
    }

    private Map<String, Object> createReportParameters(TicketPdfDTO pdfTicketInputDTO){
        Map<String, Object> params = new HashMap<>();
        params.put(PARAMETER_NAME, pdfTicketInputDTO.getName());
        params.put(PARAMETER_EMAIL, pdfTicketInputDTO.getEmail());
        params.put(PARAMETER_TRAVEL_DATE, pdfTicketInputDTO.getTravelDate());
        params.put(PARAMETER_FROM_STATION, pdfTicketInputDTO.getFromStation());
        params.put(PARAMETER_TO_STATION, pdfTicketInputDTO.getToStation());
        params.put(PARAMETER_TICKET_TYPE, pdfTicketInputDTO.getTicketType());
        params.put(PARAMETER_TRAIN, pdfTicketInputDTO.getTrain());
        params.put(PARAMETER_TRAIN_TIME, pdfTicketInputDTO.getTravelTime());
        params.put(QR_CODE_PATH, new File(Filenames.QR_CODE).getAbsolutePath());
        return params;
    }

}
