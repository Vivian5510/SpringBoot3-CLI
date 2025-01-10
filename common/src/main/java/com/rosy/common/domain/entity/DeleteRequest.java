package com.rosy.common.domain.entity;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

@Data
public class DeleteRequest implements Serializable {

    /**
     * id
     */
    private Long id;

    @Serial
    private static final long serialVersionUID = 1L;
}