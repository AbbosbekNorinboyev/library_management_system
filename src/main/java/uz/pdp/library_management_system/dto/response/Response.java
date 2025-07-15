package uz.pdp.library_management_system.dto.response;

import com.fasterxml.jackson.annotation.*;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonPropertyOrder({"success","done","error"})
@JsonIgnoreProperties(ignoreUnknown = true)
public class Response<T,V> implements Serializable {
    @JsonProperty("success")
    @Schema(description = "status", type = "boolean", defaultValue = "false")
    private boolean success;
    @JsonProperty("done")
    private T done;
    @JsonProperty("error")
    private V error;
}