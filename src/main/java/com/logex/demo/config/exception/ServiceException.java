package com.logex.demo.config.exception;


import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.InvalidDataAccessResourceUsageException;

import java.io.IOException;


public class ServiceException extends BaseRuntimeException {

    public ServiceException(String errorMessage, Exception e) {
        super(errorMessage);
        if (e instanceof RecordNotFoundException) {
            throw new RecordNotFoundException(errorMessage);
        } else if (e instanceof BadRequestException){
            throw new BadRequestException(errorMessage);
        } else if(e instanceof DuplicateRecordException){
            throw new DuplicateRecordException(errorMessage);
        }else if(e instanceof DataIntegrityViolationException){
            throw new DuplicateRecordException(e.getMessage());
        } else if(e instanceof InvalidDataAccessResourceUsageException){
            throw new DbConnectionException("Database connection failed, try again later");
        }
        else {
            throw new BaseException("Some error occurred, try again");
        }
    }

}
