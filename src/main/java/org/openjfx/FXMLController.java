package org.openjfx;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;

import java.util.ArrayList;
import java.util.List;

public class FXMLController {
    State state;
    List<ToggleButton> buttons;

    @FXML
    Label label,label1,label2,error,gamer1,gamer2;
    @FXML
    private GridPane grid;
    @FXML
    private Pane pane1,pane2;
    @FXML
    TextField text_field1,text_field2;
    @FXML
    private void handleButtonAction(ActionEvent event) {
        int buttonindex = getbutton();
        error.setText("");

        if(!state.isgoal(buttons)) {
            if (state.avaliable(buttonindex)) {
                state.put(buttonindex);
                if (buttons.size() == 12) {
                    buttons.get(buttonindex).setOpacity(0.4);
                    buttons.get(buttonindex).setDisable(true);
                    buttons = State.setlist2(buttonindex, buttons);
                } else {
                    buttons.get(buttonindex).setDisable(true);
                    buttons.remove(buttonindex).setOpacity(0.4);
                }
            }
            else error.setText("rossz lépés");
        }
        else {
            endGame(buttonindex);
        }
        label1.setText(state.score1+"");
        label2.setText(state.score2+"");
    }


    public void startButton(ActionEvent actionEvent) {
        String space1=text_field1.getText().replaceAll("\\s+", "");
        String space2=text_field2.getText().replaceAll("\\s+", "");
        if(!space1.isEmpty() && !space2.isEmpty() && !space1.equals(space2)) {
            state.name1=space1;
            state.name2=space2;
            gamer1.setText(state.name1);
            gamer2.setText(state.name2);
            grid.setVisible(true);
            pane1.setVisible(false);
        }
    }

    public void restart(ActionEvent actionEvent) {
        pane2.setVisible(false);
        grid.setOpacity(1);
        swap();
        gamer1.setText(state.name1);
        gamer2.setText(state.name2);
        initialize();
    }

    public void exit(ActionEvent actionEvent) {
        System.exit(0);
    }

    public void score(ActionEvent actionEvent) {
    }


    public void initialize() {
        state=new State();
        buttons= new ArrayList<>();
        grid.getChildren().filtered(node -> node instanceof ToggleButton)
                      .forEach(node -> buttons.add((ToggleButton) node));

        for(int i=0;i<12;i++){
            buttons.get(i).setText(state.coins.get(i).toString());
            buttons.get(i).setDisable(false);
            buttons.get(i).setOpacity(1);
        }
        label1.setText(0+"");
        label2.setText(0+"");
    }


    public int getbutton(){

        for (int i=0;i<this.buttons.size();i++){
            if (buttons.get(i).isSelected()){
                buttons.get(i).setSelected(false);
                return i;
            }
        }
        return -1;
    }

    public void endGame(int buttonindex){
        state.put(buttonindex);
        buttons.remove(buttonindex).setOpacity(0.4);
        grid.setOpacity(0.5);
        pane2.setVisible(true);
        if(state.score1>state.score2) {
            label.setText(state.name1);
        }
        else if (state.score1<state.score2){
            label.setText(state.name2);
        }
        else{
            label.setText("Döntetlen");
        }
    }

    public void swap(){
        String temp="";
        temp=state.name1;
        state.name1=state.name2;
        state.name2=temp;
    }



}
