package pojo.pojo_classes;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TechnologiesPojo {
    @JsonProperty("technology_title")
    private String technologyTitle;
}
