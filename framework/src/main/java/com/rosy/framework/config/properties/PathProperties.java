package com.rosy.framework.config.properties;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "path")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PathProperties {
    public String fileUploadPath;
}
