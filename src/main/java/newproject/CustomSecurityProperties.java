package newproject;

import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * Created By Alireza Dolatabadi
 * Date: 7/4/2022
 * Time: 8:38 PM
 */
@Component
public class CustomSecurityProperties extends SecurityProperties {
    public CustomSecurityProperties() {
        // the default list is empty
        List<String> ignoredPaths = new ArrayList<>();
        ignoredPaths.add("none");
    }
}
