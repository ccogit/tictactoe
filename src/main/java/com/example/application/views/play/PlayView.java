package com.example.application.views.play;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.component.textfield.TextFieldVariant;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouteAlias;

import java.util.Arrays;

@PageTitle("Play")
@Route(value = "play")
@RouteAlias(value = "")
public class PlayView extends FormLayout {
    TextField winner = new TextField();
    Button reset = new Button("Reset");
    String[][] inputs = new String[3][3];

    public PlayView() {
        getStyle().setMargin("20px");
        setWidth("200px");
        setResponsiveSteps(
                new ResponsiveStep("0", 3));
        reset.addClickListener(e -> clearAllTextFields());
        createLayout();
        for (int i = 0; i < 3; i++) {
            Arrays.fill(inputs[i], "");
        }
    }

    private void clearAllTextFields() {
        this.getChildren()
                .filter(c -> c instanceof TextField)
                .forEach(c -> ((TextField) c).clear());
        winner.clear();
    }

    public void createLayout() {
        this.add(winner);
        this.setColspan(winner, 3);
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                TextField textField = new TextField();
                textField.addThemeVariants(TextFieldVariant.LUMO_ALIGN_CENTER);
                int finalI = i;
                int finalJ = j;
                textField.setValueChangeMode(ValueChangeMode.LAZY);
                textField.addValueChangeListener(e -> {
                    inputs[finalI][finalJ] = textField.getValue();
                    if (checkForWinner()) winner.setValue("We have a winner");
                });
                this.add(textField);
            }
        }
        this.add(reset);
        this.setColspan(reset, 3);
    }

    public boolean checkForWinner() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (checkForRowsAndColumns()) return true;
            }
        }
        return false;
    }

    private boolean checkForRowsAndColumns() {
        for (int k = 0; k < 3; k++) {
            if ((inputs[k][0].equals(inputs[k][1]) && inputs[k][0].equals(inputs[k][2])) && !inputs[k][0].isEmpty() ||
                    (inputs[0][k].equals(inputs[1][k]) && inputs[0][k].equals(inputs[2][k]) && !inputs[0][k].isEmpty()) ||
                    (inputs[0][0].equals(inputs[1][1]) && inputs[0][0].equals(inputs[2][2]) && !inputs[0][0].isEmpty()) ||
                    (inputs[0][2].equals(inputs[1][1]) && inputs[0][2].equals(inputs[2][0]) && !inputs[0][2].isEmpty()))
                return true;
        }
        return false;
    }
}
