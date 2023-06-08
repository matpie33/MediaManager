package org.travelling.ticketer.controllers;


import com.google.gson.Gson;
import net.sf.jasperreports.engine.JRException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;
import org.travelling.ticketer.business.*;
import org.travelling.ticketer.constants.DateTimeFormats;
import org.travelling.ticketer.dto.*;
import org.travelling.ticketer.entity.Connection;
import org.travelling.ticketer.entity.Ticket;
import org.travelling.ticketer.entity.Train;
import org.travelling.ticketer.pdfgenerator.PdfExportManager;
import org.travelling.ticketer.utility.QrCodeImageGenerator;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.transaction.Transactional;
import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Set;

@RestController
public class MainRestController {

    private final Gson gson;

    private final SeatsService seatsService;

    private final TrainsService trainsService;

    private final TravelConnectionService travelConnectionService;

    private final TicketsService ticketsService;

    private final PdfExportManager pdfExportManager;

    private final QrCodeImageGenerator qrCodeImageGenerator;

    private final QrCodeValidator qrCodeValidator;

    private final DelayService delayService;



    @Autowired
    public MainRestController(QrCodeImageGenerator qrCodeImageGenerator, PdfExportManager pdfExportManager, Gson gson, SeatsService seatsService, TrainsService trainsService, TravelConnectionService travelConnectionService, TicketsService ticketsService, QrCodeValidator qrCodeValidator, DelayService delayService) {
        this.gson = gson;
        this.seatsService = seatsService;
        this.trainsService = trainsService;
        this.travelConnectionService = travelConnectionService;
        this.ticketsService = ticketsService;
        this.pdfExportManager = pdfExportManager;
        this.qrCodeImageGenerator = qrCodeImageGenerator;
        this.qrCodeValidator = qrCodeValidator;
        this.delayService = delayService;
    }

    @GetMapping("/connection/{from}/to/{to}/sinceHour/{travelDateTime}")
    public String getConnectionsByStationAndTime(@PathVariable String from, @PathVariable String to, @PathVariable @DateTimeFormat(pattern = DateTimeFormats.DATE_TIME_FORMAT) LocalDateTime travelDateTime){
        return gson.toJson(travelConnectionService.getConnectionsWithFreeSeats(from,to, travelDateTime));
    }

    @Transactional
    @PostMapping("/assignTicket/{connectionId}/user/{userId}/ticket_type/{ticketType}/travelDate/{travelDateTime}")
    public void assignTicketToUser(@PathVariable long connectionId, @PathVariable long userId, @PathVariable String ticketType, @PathVariable @DateTimeFormat(pattern = DateTimeFormats.DATE_TIME_FORMAT) LocalDateTime travelDateTime){

        Connection connection = travelConnectionService.getConnectionById(connectionId);
        seatsService.updateSeats(connection, travelDateTime, connectionId);
        ticketsService.assignTicketToUser(userId, ticketType,travelDateTime, connection);
    }

    @GetMapping("/tickets/{userId}")
    public String getTicketsOfUser(@PathVariable long userId){
        return gson.toJson(ticketsService.getTicketsOfUser(userId));
    }

    @PostMapping("connection/from/{from}/to/{to}/atTime/{time}/trainId/{trainId}")
    public void addConnection (@PathVariable String from, @PathVariable String to, @PathVariable String time,
                               @PathVariable long trainId ){
        Train train = trainsService.getTrainById(trainId);
        travelConnectionService.addNewConnection(from, to, time, train);
    }

    @GetMapping("trains")
    public String getTrains (){
        return gson.toJson(trainsService.getTrainsInformation());
    }

    @PostMapping("delayNotification")
    public void sendEmailNotifications (@RequestBody ConnectionDelayAndUrlDTO connectionDelayAndUrlDTO){
        delayService.createEmail(connectionDelayAndUrlDTO);
    }

    @GetMapping("ticket/{id}/pdf")
    public byte[] getTicketAsPdf (@PathVariable long id) throws JRException, IOException, InvalidAlgorithmParameterException, IllegalBlockSizeException, NoSuchPaddingException, BadPaddingException, NoSuchAlgorithmException, KeyStoreException, InvalidKeyException {
        Ticket ticket = ticketsService.getTicket(id);
        QrCodeContentDTO qrCodeContentDTO = new QrCodeContentDTO();
        qrCodeContentDTO.setConnectionId(ticket.getConnection().getId());
        qrCodeContentDTO.setUserId(ticket.getAppUser().getId());
        qrCodeContentDTO.setTicketId(ticket.getId());
        qrCodeImageGenerator.createQrCodeImage(qrCodeContentDTO, ticket.getInitializationVector());
        return pdfExportManager.exportToPdf(ticketsService.getTicketForPdf(ticket));
    }

    @PostMapping("decode")
    public String getDecryptedData (@RequestBody String ticketData) throws InvalidAlgorithmParameterException, NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException, BadPaddingException, KeyStoreException, IOException, InvalidKeyException {
        QrCodeContentDTO qrCodeContentDTO = qrCodeImageGenerator.parseContent(ticketData);
        boolean isValid = qrCodeValidator.getValidationStatus(qrCodeContentDTO);
        TicketCheckDTO ticketCheckDTO;
        if (!isValid){
            ticketCheckDTO = new TicketCheckDTO();
            ticketCheckDTO.setValid(false);
        }
        else{
            ticketCheckDTO = ticketsService.getTicketForChecking(qrCodeContentDTO.getTicketId());
            ticketCheckDTO.setValid(true);
        }
        return gson.toJson(ticketCheckDTO);

    }

    @GetMapping("/connections")
    public String getAllConnections (){
        Collection<ConnectionDTO> connections = travelConnectionService.getAllConnections();
        return gson.toJson(connections);
    }

    @GetMapping("/trainsWithDelaysNow/{userId}")
    public String getCurrentDelaysForUserTickets (@PathVariable long userId){
        Set<TicketWithDelayDTO> ticketsWithDelay = ticketsService.getTicketsOfUserValidNow(userId);
        return gson.toJson(ticketsWithDelay);
    }

    @PostMapping("/delay/{value}/connection/{connectionId}/date/{date}")
    public void addDelay (@PathVariable int value, @PathVariable long connectionId, @PathVariable @DateTimeFormat(pattern = DateTimeFormats.DATE_FORMAT) LocalDate date){
        delayService.addDelay(value, date, connectionId);
    }





}
