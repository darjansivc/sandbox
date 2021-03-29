package pojo.pojo_classes;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class UseCasesPojo {
    private String title;
    private String description;
    @JsonProperty("expected_result")
    private String expectedResult;
    @JsonProperty("teststeps")
    private List<String> testSteps;
    private boolean automated;
}

