package uz.pdp.library_management_system.dto;

import com.fasterxml.jackson.annotation.*;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

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
                .message(success().message)
                .data(data)
                .code(200)
                .build();
    }

    public static <T, V> Response<T, V> success() {
        return Response.<T, V>builder()
                .message(success().message)
                .data(null)
                .code(200)
                .build();
    }

    public static <T, V> Response<T, V> success(T data, String message) {
        return Response.<T, V>builder()
                .message(message)
                .data(data)
                .code(200)
                .build();
    }

    public static <T, V> Response<T, V> error(V error) {
        return Response.<T, V>builder()
                .error(error)
                .data(null)
                .code(200)
                .build();
    }

    public static <T, V> Response<T, V> error(String message) {
        return Response.<T, V>builder()
                .message(message)
                .data(null)
                .build();
    }

    public static <T, V> Response<T, V> error(Integer code, String message) {
        return Response.<T, V>builder()
                .message(message)
                .code(code)
                .data(null)
                .build();
    }
}