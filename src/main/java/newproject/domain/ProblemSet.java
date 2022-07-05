package newproject.domain;

import lombok.*;

/**
 * Created By Alireza Dolatabadi
 * Date: 7/4/2022
 * Time: 9:38 AM
 */
@Builder
@AllArgsConstructor
@Setter
@Getter
@NoArgsConstructor
public class ProblemSet {
    String num;
    String title;
    String url;
    String acceptance;
    String difficulty;
}
