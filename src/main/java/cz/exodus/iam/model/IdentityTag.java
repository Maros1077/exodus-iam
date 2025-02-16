package cz.exodus.iam.model;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class IdentityTag {

    @JsonProperty(required = true)
    private String type;

    @JsonProperty(required = true)
    private String value;
}
