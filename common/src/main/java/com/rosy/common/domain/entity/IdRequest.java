package com.rosy.common.domain.entity;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

@Data
public class IdRequest implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    /**
     * id
     */
    private Long id;
}