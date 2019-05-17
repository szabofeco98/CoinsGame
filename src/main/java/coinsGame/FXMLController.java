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
    Database database=new Database();
    List<ToggleButton> buttons;

    @FXML
    TableView<Gamer> ranklist;
    @FXML
    TableColumn<Object, Object> name;
    @FXML
    TableColumn<Object, Object> listscore;
    @FXML
    Label winner,error,gamer1,gamer2,gamerscore1,gamerscore2,nextPlayer;
    @FXML
    private GridPane gameMenu;
    @FXML
    private Pane mainMenu, popUpMenu,RankingPane, helpMenu;
    @FXML
    TextField getName1, getName2;


    @FXML
    private void handleButtonAction(ActionEvent event) {
        int buttonindex = getbutton();
        error.setText("");

        if(!state.isgoal()) {
            if (state.available(buttonindex)) {
                setnextPlayer();
                state.setPlayerScore(buttonindex);
                if (buttons.size() == 12) {
                    firststep(buttonindex);
                }
                else otherstep(buttonindex);
            }
            else error.setText("rossz lépés");
        }
        else {
            endGame(buttonindex);
        }

        gamerscore1.setText(state.firstPLayer.playerScore +"");
        gamerscore2.setText(state.secondPlayer.playerScore +"");
    }

    @FXML
    public void startButton(ActionEvent actionEvent) {
        initialize();
        state.firstPLayer.playerName = getName1.getText().replaceAll("\\s+", "");
        state.secondPlayer.playerName = getName2.getText().replaceAll("\\s+", "");
        String firstName=state.firstPLayer.playerName;
        String secondName=state.secondPlayer.playerName;
        if(!firstName.isEmpty() && !secondName.isEmpty()
                    && !firstName.equals(secondName)) {
            gamer1.setText(firstName);
            gamer2.setText(secondName);
            gameMenu.setVisible(true);
            mainMenu.setVisible(false);
            nextPlayer.setText(firstName);
            error.setText("");
            gameMenu.setOpacity(1);
        }
    }

    @FXML
    public void restart(ActionEvent actionEvent) {
        popUpMenu.setVisible(false);
        gameMenu.setOpacity(1);
        swap();
        gamer1.setText(state.firstPLayer.playerName);
        gamer2.setText(state.secondPlayer.playerName);
        initialize();
        state.firstPLayer.playerName=gamer1.getText();
        state.secondPlayer.playerName=gamer2.getText();
        state.roundNumber =0;
    }

    @FXML
    public void exit(ActionEvent actionEvent) {
        System.exit(0);
    }

    @FXML
    public void score(ActionEvent actionEvent) {
        mainMenu.setVisible(false);
        RankingPane.setVisible(true);
        set_Rank();
    }

    @FXML
    public void back(ActionEvent actionEvent) {
        mainMenu.setVisible(true);
        RankingPane.setVisible(false);
        ranklist.getItems().clear();
    }

    @FXML
    public void goToMainMenu(ActionEvent actionEvent) {
        getName1.setText("");
        getName2.setText("");
        popUpMenu.setVisible(false);
        gameMenu.setVisible(false);
        mainMenu.setVisible(true);
    }

    @FXML
    public void help(ActionEvent actionEvent) {
        gameMenu.setOpacity(0.1);
        helpMenu.setVisible(true);
    }

    @FXML
    public void back_Game(ActionEvent actionEvent) {
        gameMenu.setOpacity(1);
        helpMenu.setVisible(false);
    }

    public void initialize() {
        state=new State();
        buttons= getAllToggleButton();

        for(int i=0;i<12;i++){
            buttons.get(i).setText(state.coins.get(i).toString());
            buttons.get(i).setDisable(false);
            buttons.get(i).setOpacity(1);
        }

        gamerscore1.setText("0");
        gamerscore2.setText("0");
    }

    public List getAllToggleButton(){
        List<ToggleButton> Togglebuttons= new ArrayList<>();
        gameMenu.getChildren().filtered(node -> node instanceof ToggleButton)
                .forEach(node -> Togglebuttons.add((ToggleButton) node));
        return Togglebuttons;
    }

    public void set_Rank(){
        ObservableList<Gamer> obslist=FXCollections.observableList(database.ranklist());
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
        state.setPlayerScore(buttonindex);
        buttons.get(buttonindex).setDisable(true);
        buttons.remove(buttonindex).setOpacity(0.4);
        gameMenu.setOpacity(0.5);
        popUpMenu.setVisible(true);
        int score1=state.firstPLayer.playerScore;
        int score2=state.secondPlayer.playerScore;

        String winner=score1 >score2 ? state.firstPLayer.playerName :
                score1 <score2 ? state.secondPlayer.playerName :"Döntetlen";
        this.winner.setText(winner);
        database.dataset(winner,state);
        //Database.deleteTable();
    }

    public void swap(){

        String temp="";
        temp=state.firstPLayer.playerName;
        state.firstPLayer.playerName =state.secondPlayer.playerName;
        state.secondPlayer.playerName =temp;
    }

    public void firststep(int buttonindex){
        buttons.get(buttonindex).setOpacity(0.4);
        buttons.get(buttonindex).setDisable(true);
        buttons = State.setlist(buttonindex, buttons);
    }

    public void otherstep(int buttonindex){
        buttons.get(buttonindex).setDisable(true);
        buttons.remove(buttonindex).setOpacity(0.4);
    }

    public void setnextPlayer(){
        String firstGamer=state.firstPLayer.playerName;
        String secondGamer=state.secondPlayer.playerName;
        if(state.roundNumber %2!=0)
            nextPlayer.setText(firstGamer);
        else nextPlayer.setText(secondGamer);

    }

}
