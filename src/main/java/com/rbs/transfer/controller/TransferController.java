package com.rbs.transfer.controller;

import com.rbs.transfer.service.TransferService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController(value = "transferController")
@RequestMapping(value = "/v1", consumes = MediaType.APPLICATION_JSON_VALUE)
public class TransferController {

    private TransferService transferService;

    public TransferController(TransferService transferService) {
        this.transferService = transferService;
    }

    @PostMapping(value = "/transfer", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity transfer() {
        boolean result = transferService.transfer();
        return new ResponseEntity(result? HttpStatus.NO_CONTENT:HttpStatus.BAD_REQUEST);
    }
}