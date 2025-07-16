package uz.pdp.library_management_system.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonPropertyOrder({"success", "done", "error"})
@JsonIgnoreProperties(ignoreUnknown = true)
public class Response<T, V> implements Serializable {
    @JsonProperty("success")
    @Schema(description = "status", type = "boolean", defaultValue = "false")
    private boolean success;
    @JsonProperty("message")
    private String message;
    @JsonProperty("data")
    private T data;
    @JsonProperty("error")
    private V error;
    @JsonProperty("code")
    private Integer code;

    public static <T, V> Response<T, V> success(T data) {
        return Response.<T, V>builder()
                .success(true)
                .message(success().message)
                .code(HttpStatus.OK.value())
                .data(data)
                .build();
    }

    public static <T, V> Response<T, V> success() {
        return Response.<T, V>builder()
                .success(true)
                .message(success().message)
                .code(HttpStatus.OK.value())
                .data(null)
                .build();
    }

    public static <T, V> Response<T, V> success(T data, String message) {
        return Response.<T, V>builder()
                .success(true)
                .message(message)
                .code(HttpStatus.OK.value())
                .data(data)
                .build();
    }

    public static <T, V> Response<T, V> error(V error) {
        return Response.<T, V>builder()
                .success(false)
                .error(error)
                .code(HttpStatus.BAD_REQUEST.value())
                .data(null)
                .build();
    }

    public static <T, V> Response<T, V> error(String message) {
        return Response.<T, V>builder()
                .success(false)
                .message(message)
                .code(HttpStatus.BAD_REQUEST.value())
                .data(null)
                .build();
    }

    public static <T, V> Response<T, V> error(Integer code, String message) {
        return Response.<T, V>builder()
                .success(false)
                .message(message)
                .code(HttpStatus.BAD_REQUEST.value())
                .data(null)
                .build();
    }

    public static <T, V> Response<T, V> notFound(String message) {
        return Response.<T, V>builder()
                .success(false)
                .message(message)
                .code(HttpStatus.NOT_FOUND.value())
                .data(null)
                .build();
    }
}