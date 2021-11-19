package com.itnxd.servicebase.exceptionhandler;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author ITNXD
 * @create 2021-11-03 11:24
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class GuliException extends RuntimeException{

    @ApiModelProperty(value = "状态码")
    private Integer code;
    private String msg;
}
