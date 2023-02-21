package com.dinev.familytree.model;

import java.io.Serial;
import java.io.Serializable;

import javax.persistence.MappedSuperclass;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;


@Getter
@Setter
@SuperBuilder
@MappedSuperclass
public class AuditableEntity implements Serializable{

    @Serial
    private static final long serialVersionUID = -3830187817678880528L;

    
}
