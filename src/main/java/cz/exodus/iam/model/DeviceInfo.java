package cz.exodus.iam.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DeviceInfo {

    private String os;
    private String osVersion;
    private String manufacturer;
    private String model;
    private String applicationVersion;
    private String applicationId;
    private String hwId;

}
