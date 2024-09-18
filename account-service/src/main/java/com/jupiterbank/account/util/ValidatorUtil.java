package com.jupiterbank.account.util;

import com.jupiterbank.account.exception.ErrorDto;
import org.springframework.context.MessageSource;
import org.springframework.validation.BindingResult;
import org.springframework.validation.DataBinder;
import org.springframework.validation.Validator;

import java.util.List;
import java.util.Locale;

/**
 * @author Julian Jupiter
 */
public class ValidatorUtil {
    private ValidatorUtil() {
    }

    public static BindingResult validate(Validator validator, Object target, String objectName) {
        var dataBinder = new DataBinder(target, objectName);
        dataBinder.addValidators(validator);
        dataBinder.validate();

        return dataBinder.getBindingResult();
    }

    public static List<ErrorDto> validationErrors(BindingResult bindingResult, MessageSource messageSource) {
        return bindingResult
                .getFieldErrors()
                .stream()
                .map(fieldError -> {
                    String fieldErrorCode = fieldError.getCode();
                    String field = fieldError.getField();
                    String[] resolveMessageCodes = bindingResult.resolveMessageCodes(fieldErrorCode);
                    var key = resolveMessageCodes[0] + "." + field;
                    var message = messageSource.getMessage(key, new Object[]{fieldError.getRejectedValue()}, Locale.ENGLISH);
                    return new ErrorDto(message);
                })
                .toList();
    }
}
