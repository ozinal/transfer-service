package com.rbs.transfer.service;

import com.rbs.transfer.domain.InputData;

public interface TransferService {
    boolean transfer(InputData input) throws Exception;
}
