package com.rbs.transfer.controller;

import com.rbs.transfer.domain.InputData;
import com.rbs.transfer.exception.AccountException;
import com.rbs.transfer.exception.TransferRollbackException;
import com.rbs.transfer.service.TransferService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController(value = "transferController")
@RequestMapping(value = "/v1", consumes = MediaType.APPLICATION_JSON_VALUE)
public class TransferController {

    private static final Logger LOG = LoggerFactory.getLogger(TransferController.class);


    private final TransferService transferService;

    @Autowired
    public TransferController(final TransferService transferService) {
        this.transferService = transferService;
    }

    @PostMapping(value = "/transfer", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity transfer(@RequestBody @Valid final InputData input)
            throws AccountException, TransferRollbackException {
        boolean success = transferService.transfer(input);
        HttpStatus status = success ? HttpStatus.NO_CONTENT : HttpStatus.BAD_REQUEST;
        return new ResponseEntity(status);
    }
}