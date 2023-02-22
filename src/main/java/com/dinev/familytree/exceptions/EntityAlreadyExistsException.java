package com.dinev.familytree.exceptions;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.text.MessageFormat;

@Slf4j
@ResponseStatus(HttpStatus.BAD_GATEWAY)
public class EntityAlreadyExistsException extends RuntimeException{
    private static final String ENTITY_ALREADY_FOUND =
            "Entity ''{0}'' with id ''{1}'' already exists";

    public EntityAlreadyExistsException(Class entityClass, int id){
        log.error(MessageFormat.format(ENTITY_ALREADY_FOUND, entityClass.getName(), id));
    }

    public EntityAlreadyExistsException(Class entityClass, String name){
        log.error(MessageFormat.format(ENTITY_ALREADY_FOUND, entityClass.getName(), name));
    }

}
