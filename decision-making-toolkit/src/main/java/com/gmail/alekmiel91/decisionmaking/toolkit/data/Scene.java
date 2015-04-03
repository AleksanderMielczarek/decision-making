package com.gmail.alekmiel91.decisionmaking.toolkit.data;

import com.gmail.alekmiel91.decisionmaking.toolkit.data.validation.DoubleRange;
import lombok.*;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.NotNull;

/**
 * @author Aleksander Mielczarek
 *         Created on 2015-04-01.
 */
@Getter
@Setter
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
public class Scene {

    @NotEmpty(message = "{error.scene.name.not.empty}")
    private String name;

    @NotNull(message = "{error.scene.probability.not.null}")
    @DoubleRange(min = 0.0, max = 1.0, message = "{error.scene.probability.double.range}")
    private Double probability;

    public Scene(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name + "(" + probability + ")";
    }
}
