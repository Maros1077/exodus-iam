package cz.exodus.iam.model;

import lombok.*;

@Data
@NoArgsConstructor
@Getter
@Setter
@AllArgsConstructor
public class Account {

    private String idid;

    private String name;

    private String password;

    //private DeviceInfo deviceInfo;

}
