package uz.pdp.library_management_system.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import lombok.extern.slf4j.Slf4j;
import uz.pdp.library_management_system.exception.CustomizedRequestException;
import uz.pdp.library_management_system.exception.InvalidHeadersException;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Set;

/**
 * @author Abbos Norinboyev
 * Date: 15.07.2025
 */
@Slf4j
public class Util {
    public static String convertObjectToJsonString(Object object) {
        ObjectMapper objectMapper = new ObjectMapper();
        String jsonString = null;
        try {
            jsonString = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(object);
        } catch (JsonProcessingException e) {
            throw new CustomizedRequestException("Error while converting object to string: " + e.getMessage(), 2, 400);
        }
        return jsonString;
    }

    public static void validate(Object validate) {
        try (ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory()) {
            Validator validator = validatorFactory.getValidator();
            Set<ConstraintViolation<Object>> violations = validator.validate(validate);
            if (!violations.isEmpty()) {
                StringBuilder stringBuilder = new StringBuilder();
                for (ConstraintViolation<Object> violation : violations) {
                    String interpolatedMessage = violation.getMessage();
                    String propertyPath = violation.getPropertyPath().toString();
                    stringBuilder.append(propertyPath)
                            .append(": ")
                            .append(interpolatedMessage)
                            .append(System.lineSeparator());
                }
                throw new RuntimeException(stringBuilder.toString());
            }
        }
    }

    public static void validateHeader(Object validate, String errorMessage) {
        try (ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory()) {
            Validator validator = validatorFactory.getValidator();
            Set<ConstraintViolation<Object>> violations = validator.validate(validate);
            if (!violations.isEmpty()) {
                throw new InvalidHeadersException(errorMessage);
            }
        }
    }

    public static String timeFormatter(Date date) {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return date.toInstant()
                .atZone(ZoneId.of("UTC+5"))
                .toLocalDateTime()
                .format(dateTimeFormatter);
    }

    public static Date convertToLocalDateTimeToDateWithMinutesAdded(int minute) {
        ZoneId zoneId = ZoneId.systemDefault();
        ZonedDateTime utcPlus5DZonedDateTime = LocalDateTime.now()
                .plusMinutes(minute)
                .atZone(zoneId)
                .withZoneSameInstant(ZoneId.of("UTC+5"));
        try {
            DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            String formattedDateTime = utcPlus5DZonedDateTime.format(dateTimeFormatter);
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");
            return dateFormat.parse(formattedDateTime);
        } catch (ParseException e) {
            if (log.isErrorEnabled()) {
                log.error("DATE_PARSING {}", e.getMessage());
            }
        }
        return Date.from(utcPlus5DZonedDateTime.toInstant());
    }

    public static Date convertToLocalDateTimeToDate() {
        ZoneId zoneId = ZoneId.systemDefault();
        ZonedDateTime utcPlus5DZonedDateTime = LocalDateTime.now()
                .atZone(zoneId)
                .withZoneSameInstant(ZoneId.of("UTC+5"));
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
            String formattedDateTime = utcPlus5DZonedDateTime.format(formatter);
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
            simpleDateFormat.parse(formattedDateTime);
        } catch (ParseException e) {
            if (log.isErrorEnabled()) {
                log.error("DATE_PARSING {}", e.getMessage());
            }
        }
        return Date.from(utcPlus5DZonedDateTime.toInstant());
    }

    public static String convertToLocalDateTimeToString(String pattern) {
        ZoneId zoneId = ZoneId.systemDefault();
        ZonedDateTime utcPlus5DZonedDateTime = LocalDateTime.now()
                .atZone(zoneId)
                .withZoneSameInstant(ZoneId.of("UTC+5"));
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
        return utcPlus5DZonedDateTime.format(formatter);
    }

    public static String convertToLocalDateTimePlusMonthToString(String pattern, int months, int plusDays) {
        ZoneId zoneId = ZoneId.systemDefault();
        ZonedDateTime utcPlus5DZonedDateTime = LocalDateTime.now()
                .plusMonths(months)
                .plusDays(plusDays)
                .atZone(zoneId)
                .withZoneSameInstant(ZoneId.of("UTC+5"));
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
        return utcPlus5DZonedDateTime.format(formatter);
    }
}
