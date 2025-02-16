package cz.exodus.iam.model;


import cz.exodus.iam.db.entity.AuthPointTypeEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import org.springframework.lang.Nullable;

@Data
@AllArgsConstructor
@Getter
public class AuthPointEntityWithValue {

    private AuthPointTypeEntity type;

    @Nullable
    private String value;
}
