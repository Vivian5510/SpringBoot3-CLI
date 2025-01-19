package com.rosy.common.domain.dto.uploadfile;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * 文件上传请求
 */
@Data
public class UploadFileRequest implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;
    /**
     * 业务
     */
    private String biz;
}