package pojo.pojo_classes;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class UseCasesPojo {
    private String title;
    private String description;
    private String expected_result;
    private List<String> teststeps;
    private boolean automated;
}
