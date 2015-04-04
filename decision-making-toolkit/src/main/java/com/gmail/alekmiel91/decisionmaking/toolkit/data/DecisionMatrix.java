package com.gmail.alekmiel91.decisionmaking.toolkit.data;

import com.gmail.alekmiel91.decisionmaking.toolkit.Context;
import com.google.common.collect.ArrayTable;
import com.google.common.collect.Table;
import lombok.AccessLevel;
import lombok.Getter;
import org.nocrala.tools.texttablefmt.BorderStyle;
import org.nocrala.tools.texttablefmt.CellStyle;
import org.nocrala.tools.texttablefmt.ShownBorders;

import java.text.MessageFormat;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Aleksander Mielczarek
 *         Created on 2015-03-31.
 */
@Getter
public class DecisionMatrix {

    //row - alternative
    //column - scene

    private final RawDecisionMatrix rawDecisionMatrix;

    private final Table<Alternative, Scene, Output> decisionTable;
    private final List<Table<String, Scene, Double>> factorsTables;

    @Getter(AccessLevel.NONE)
    private final String printTable;

    DecisionMatrix(DecisionMatrixBuilder builder) {
        rawDecisionMatrix = builder.getRawDecisionMatrix();

        decisionTable = ArrayTable.create(rawDecisionMatrix.getAlternatives(), rawDecisionMatrix.getScenes());

        Iterator<Output> outputIterator = rawDecisionMatrix.getOutputs().iterator();
        for (Alternative alternative : decisionTable.rowKeySet()) {
            for (Scene scene : decisionTable.columnKeySet()) {
                decisionTable.put(alternative, scene, outputIterator.next());
            }
        }

        factorsTables = rawDecisionMatrix.getFactors().stream()
                .map(factor -> {
                            Table<String, Scene, Double> factorTable = ArrayTable.create(factor.getFactorsNames(), rawDecisionMatrix.getScenes());

                            Iterator<Double> factorIterator = factor.getFactorsOutputsValues().iterator();
                            for (String factorName : factorTable.rowKeySet()) {
                                for (Scene scene : factorTable.columnKeySet()) {
                                    factorTable.put(factorName, scene, factorIterator.next());
                                }
                            }
                            return factorTable;
                        }
                )
                .collect(Collectors.toList());

        printTable = createPrintTable();
    }

    public static DecisionMatrixBuilder builder(List<Alternative> alternatives, List<Scene> scenes, List<Output> outputs) {
        return new DecisionMatrixBuilder(alternatives, scenes, outputs);
    }

    private String createPrintTable() {
        org.nocrala.tools.texttablefmt.Table table = new org.nocrala.tools.texttablefmt.Table(rawDecisionMatrix.getScenes().size() + 1, BorderStyle.CLASSIC, ShownBorders.ALL);

        table.addCell(Context.INSTANCE.getResources().getString("decision.matrix.table.alternatives.scenes"));

        decisionTable.columnKeySet().stream()
                .map(Scene::toString)
                .forEach(table::addCell);

        for (Alternative alternative : decisionTable.rowKeySet()) {
            table.addCell(alternative.toString());
            for (Scene scene : decisionTable.columnKeySet()) {
                table.addCell(decisionTable.get(alternative, scene).toString());
            }
        }

        Iterator<String> factorNameIterator = rawDecisionMatrix.getFactors().stream()
                .map(Factor::getFactorName)
                .collect(Collectors.toList()).iterator();

        Iterator<String> chosenFactorIterator = rawDecisionMatrix.getFactors().stream()
                .map(Factor::getChosenFactor)
                .collect(Collectors.toList()).iterator();

        for (Table<String, Scene, Double> factorTable : factorsTables) {

            table.addCell(factorNameIterator.next(), new CellStyle(CellStyle.HorizontalAlign.center), rawDecisionMatrix.getScenes().size() + 1);
            String chosenFactor = chosenFactorIterator.next();

            for (String factor : factorTable.rowKeySet()) {
                if (factor.equals(chosenFactor)) {
                    table.addCell(MessageFormat.format(Context.INSTANCE.getResources().getString("decision.matrix.table.chosen.factor"), chosenFactor));
                } else {
                    table.addCell(factor);
                }
                for (Scene scene : factorTable.columnKeySet()) {
                    table.addCell(factorTable.get(factor, scene).toString());
                }
            }
        }
        return table.render();
    }

    @Override
    public String toString() {
        return printTable;
    }

}