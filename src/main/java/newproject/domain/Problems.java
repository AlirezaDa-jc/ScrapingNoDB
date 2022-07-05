package newproject.domain;

import lombok.*;

import java.util.List;

/**
 * Created By Alireza Dolatabadi
 * Date: 7/4/2022
 * Time: 7:44 PM
 */
@Builder
@AllArgsConstructor
@Setter
@Getter
@NoArgsConstructor
public class Problems {
    String num;
    List<String> tags;
    String name;
    String rating;
}
