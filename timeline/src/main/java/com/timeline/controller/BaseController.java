package com.timeline.controller;


import com.timeline.error.BussinessException;
import com.timeline.error.EmBussinessError;
import com.timeline.response.CommonReturnType;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

public class BaseController {
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public Object handlerException(HttpServletRequest request, Exception ex) {
        Map<String, Object> map = new HashMap<>();
        if (ex instanceof BussinessException) {
            BussinessException exception = (BussinessException) ex;
            map.put("errCode", exception.getErrorCode());
            map.put("errMsg", exception.getErrorMessage());
        } else {
            System.out.println(ex.getMessage());
            map.put("errCode", EmBussinessError.UNKNOWN_ERROR.getErrorCode());
            map.put("errMsg", EmBussinessError.UNKNOWN_ERROR.getErrorMessage());
        }
        return CommonReturnType.create(map, "fail");
    }
}
