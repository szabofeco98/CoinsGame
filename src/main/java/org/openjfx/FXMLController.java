package org.openjfx;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import org.openjfx.modell.Gamer;

import java.util.ArrayList;
import java.util.List;

public class FXMLController {
    State state;
    List<ToggleButton> buttons;
    @FXML
    TableView<Gamer> ranklist;
    @FXML
    TableColumn<Object, Object> name;
    @FXML
    TableColumn<Object, Object> listscore;
    @FXML
    Label label,error,gamer1,gamer2,gamerscore1,gamerscore2;
    @FXML
    private GridPane grid;
    @FXML
    private Pane Main_menu, Pop_up_Menu,RankingPane,help_Menu;
    @FXML
    TextField get_name1,get_name2;


    @FXML
    private void handleButtonAction(ActionEvent event) {
        int buttonindex = getbutton();
        error.setText("");

        if(!state.isgoal(buttons)) {
            if (state.avaliable(buttonindex)) {
                state.put(buttonindex);
                if (buttons.size() == 12) {
                    firststep(buttonindex);

                } else {
                    otherstep(buttonindex);
                }
            }
            else error.setText("rossz lépés");
        }
        else {
            endGame(buttonindex);
        }
        gamerscore1.setText(state.score1+"");
        gamerscore2.setText(state.score2+"");


    }

    public void startButton(ActionEvent actionEvent) {
        state.name1=get_name1.getText().replaceAll("\\s+", "");
        state.name2=get_name2.getText().replaceAll("\\s+", "");
        if(!state.name1.isEmpty() && !state.name2.isEmpty() && !state.name1.equals(state.name2)) {
            gamer1.setText(state.name1);
            gamer2.setText(state.name2);
            grid.setVisible(true);
            Main_menu.setVisible(false);
        }
    }

    public void restart(ActionEvent actionEvent) {
        Pop_up_Menu.setVisible(false);
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
        Main_menu.setVisible(false);
        RankingPane.setVisible(true);
        set_Rank();
    }

    public void back(ActionEvent actionEvent) {
        Main_menu.setVisible(true);
        RankingPane.setVisible(false);
    }

    public void main_menu(ActionEvent actionEvent) {
        Pop_up_Menu.setVisible(false);
        grid.setVisible(false);
        Main_menu.setVisible(true);
        grid.setOpacity(1);
        initialize();
    }

    public void save(ActionEvent actionEvent) {
    }

    public void help(ActionEvent actionEvent) {
        grid.setOpacity(0.1);
        help_Menu.setVisible(true);
    }

    public void back_Game(ActionEvent actionEvent) {
        grid.setOpacity(1);
        help_Menu.setVisible(false);
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

        gamerscore1.setText("0");
        gamerscore2.setText("0");
    }

    public void set_Rank(){
        ObservableList<Gamer> obslist=FXCollections.observableList(state.ranklist());
        name.setCellValueFactory(new PropertyValueFactory<>("user_name"));
        listscore.setCellValueFactory(new PropertyValueFactory<>("score"));
        ranklist.setItems(obslist);
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
        Pop_up_Menu.setVisible(true);
        String winner=state.score1>state.score2 ? state.name1:
                state.score1<state.score2 ? state.name2:"Döntetlen";
        label.setText(winner);
        state.dataset(winner);
    }

    public void swap(){
        String temp="";
        temp=state.name1;
        state.name1=state.name2;
        state.name2=temp;
    }

    public void firststep(int buttonindex){
        buttons.get(buttonindex).setOpacity(0.4);
        buttons.get(buttonindex).setDisable(true);
        buttons = State.setlist2(buttonindex, buttons);
    }

    public void otherstep(int buttonindex){
        buttons.get(buttonindex).setDisable(true);
        buttons.remove(buttonindex).setOpacity(0.4);
    }



}
