package databuilder.data.models.requests;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateConsumerRequest {

    @NotNull
    @NotEmpty
    @JsonProperty
    private String consumerName;

    @NotNull
    @NotEmpty
    @JsonProperty
    private String mobileNumber;
}
