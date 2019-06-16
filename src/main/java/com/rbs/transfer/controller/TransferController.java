package com.rbs.transfer.controller;

import com.rbs.transfer.domain.InputData;
import com.rbs.transfer.service.TransferService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController(value = "transferController")
@RequestMapping(value = "/v1", consumes = MediaType.APPLICATION_JSON_VALUE)
public class TransferController {

    private TransferService transferService;

    public TransferController(TransferService transferService) {
        this.transferService = transferService;
    }

    @PostMapping(value = "/transfer", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity transfer(@RequestBody @Valid final InputData input) {
        boolean result = transferService.transfer();
        return new ResponseEntity(result ? HttpStatus.NO_CONTENT : HttpStatus.BAD_REQUEST);
    }
}