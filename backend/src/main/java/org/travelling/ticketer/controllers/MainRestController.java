package org.travelling.ticketer.controllers;


import com.google.gson.Gson;
import net.sf.jasperreports.engine.JRException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.travelling.ticketer.business.SeatsManager;
import org.travelling.ticketer.business.TicketsManager;
import org.travelling.ticketer.business.TrainsManager;
import org.travelling.ticketer.business.TravelConnectionManager;
import org.travelling.ticketer.constants.DateTimeFormats;
import org.travelling.ticketer.entity.Connection;
import org.travelling.ticketer.entity.Ticket;
import org.travelling.ticketer.entity.Train;
import org.travelling.ticketer.pdfgenerator.PdfExportManager;
import org.travelling.ticketer.utility.QrCodeGenerator;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.transaction.Transactional;
import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;

@RestController
public class MainRestController {

    private final Gson gson;

    private final SeatsManager seatsManager;

    private final TrainsManager trainsManager;

    private final TravelConnectionManager travelConnectionManager;

    private final TicketsManager ticketsManager;

    private final PdfExportManager pdfExportManager;

    private final QrCodeGenerator qrCodeGenerator;

    @Autowired
    public MainRestController(QrCodeGenerator qrCodeGenerator, PdfExportManager pdfExportManager, Gson gson, SeatsManager seatsManager, TrainsManager trainsManager, TravelConnectionManager travelConnectionManager, TicketsManager ticketsManager) {
        this.gson = gson;
        this.seatsManager = seatsManager;
        this.trainsManager = trainsManager;
        this.travelConnectionManager = travelConnectionManager;
        this.ticketsManager = ticketsManager;
        this.pdfExportManager = pdfExportManager;
        this.qrCodeGenerator = qrCodeGenerator;
    }

    @GetMapping("/connection/{from}/to/{to}/sinceHour/{travelDateTime}")
    public String getConnectionsByStationAndTime(@PathVariable String from, @PathVariable String to, @PathVariable @DateTimeFormat(pattern = DateTimeFormats.DATE_TIME_FORMAT) LocalDateTime travelDateTime){
        return gson.toJson(travelConnectionManager.getConnectionsWithFreeSeats(from,to, travelDateTime));
    }

    @Transactional
    @GetMapping("/assignTicket/{connectionId}/user/{userId}/ticket_type/{ticketType}/travelDate/{travelDateTime}")
    public void assignTicketToUser(@PathVariable long connectionId, @PathVariable long userId, @PathVariable String ticketType, @PathVariable @DateTimeFormat(pattern = "dd.MM.yyyy HH:mm") LocalDateTime travelDateTime){

        Connection connection = travelConnectionManager.getConnectionById(connectionId);
        seatsManager.updateSeats(connection, travelDateTime, connectionId);
        ticketsManager.assignTicketToUser(userId, ticketType,travelDateTime, connection);
    }

    @GetMapping("/tickets/{userId}")
    public String getTicketsOfUser(@PathVariable long userId){
        return gson.toJson(ticketsManager.getTicketsOfUser(userId));
    }

    @PostMapping("connection/from/{from}/to/{to}/atTime/{time}/trainId/{trainId}")
    public void addConnection (@PathVariable String from, @PathVariable String to, @PathVariable String time,
                               @PathVariable long trainId ){
        Train train = trainsManager.getTrainById(trainId);
        travelConnectionManager.addNewConnection(from, to, time, train);
    }

    @GetMapping("trains")
    public String getTrains (){
        return gson.toJson(trainsManager.getTrainsInformation());
    }

    @GetMapping("ticket/{id}/pdf")
    public byte[] getTicketAsPdf (@PathVariable long id) throws JRException, IOException, InvalidAlgorithmParameterException, IllegalBlockSizeException, NoSuchPaddingException, BadPaddingException, NoSuchAlgorithmException, KeyStoreException, InvalidKeyException {
        Ticket ticket = ticketsManager.getTicket(id);
        qrCodeGenerator.getQrCode(ticket.getId(), ticket.getConnection().getId(), ticket.getInitializationVector());
        return pdfExportManager.exportToPdf(ticket);
    }




}
