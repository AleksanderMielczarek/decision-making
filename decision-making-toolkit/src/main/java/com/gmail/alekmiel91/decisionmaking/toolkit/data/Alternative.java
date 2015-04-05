package com.gmail.alekmiel91.decisionmaking.toolkit.data;

import com.gmail.alekmiel91.decisionmaking.toolkit.Context;
import com.gmail.alekmiel91.decisionmaking.toolkit.data.validation.DoubleRange;
import lombok.*;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.NotNull;
import java.text.MessageFormat;
import java.util.Collections;
import java.util.List;

/**
 * @author Aleksander Mielczarek
 *         Created on 2015-04-01.
 */
@Getter
@Setter
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
public class Alternative implements Defaultable {

    @NotEmpty(message = "{error.alternative.name.not.empty}")
    private String name;

    @NotNull(message = "{error.alternative.risk.not.null}")
    @DoubleRange(min = 0.0, max = 1.0, message = "{error.alternative.risk.double.range}")
    private Double risk;

    public Alternative(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name + "(" + risk + ")";
    }

    @Override
    public List<String> applyDefaultAndLog() {
        if (risk == null) {
            risk = 0.5;
            String log = MessageFormat.format(Context.INSTANCE.getResources().getString("log.default.alternative.risk"), name, risk);
            return Collections.singletonList(log);
        }
        return Collections.emptyList();
    }
}
