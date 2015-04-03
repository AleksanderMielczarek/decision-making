package com.gmail.alekmiel91.decisionmaking.toolkit.data;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.NotNull;

/**
 * @author Aleksander Mielczarek
 *         Created on 2015-04-01.
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Output {

    @NotEmpty(message = "{error.output.name.not.empty}")
    private String name;

    @NotNull(message = "{error.output.value.not.null}")
    private Double value;

    @Override
    public String toString() {
        return name + "(" + value + ")";
    }
}
