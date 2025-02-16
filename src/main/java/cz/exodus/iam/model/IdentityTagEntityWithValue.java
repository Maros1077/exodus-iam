package cz.exodus.iam.model;


import com.fasterxml.jackson.annotation.JsonProperty;
import cz.exodus.iam.db.entity.TagTypeEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@Getter
public class IdentityTagEntityWithValue {

    private TagTypeEntity type;

    private String value;
}
