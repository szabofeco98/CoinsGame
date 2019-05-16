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

import java.nio.file.LinkOption;
import java.util.ArrayList;
import java.util.List;

public class FXMLController {
    State state;
    List<ToggleButton> buttons;
    List<String> buttonNames;
    List<ToggleButton> loadedButton;

    @FXML
    TableView<Gamer> ranklist;
    @FXML
    TableColumn<Object, Object> name;
    @FXML
    TableColumn<Object, Object> listscore;




    @FXML
    Label winner,error,gamer1,gamer2,gamerscore1,gamerscore2,nextPlayer;
    @FXML
    private GridPane grid;
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

        gamerscore1.setText(state.firstPlayerScore +"");
        gamerscore2.setText(state.secondPlayerScore +"");
    }

    @FXML
    public void startButton(ActionEvent actionEvent) {
        initialize();
        state.firstGamer = getName1.getText().replaceAll("\\s+", "");
        state.secondGamer = getName2.getText().replaceAll("\\s+", "");
        if(!state.firstGamer.isEmpty() && !state.secondGamer.isEmpty() && !state.firstGamer.equals(state.secondGamer)) {
            gamer1.setText(state.firstGamer);
            gamer2.setText(state.secondGamer);
            grid.setVisible(true);
            mainMenu.setVisible(false);
            nextPlayer.setText(state.firstGamer);
            error.setText("");
            grid.setOpacity(1);
        }
    }

    @FXML
    public void restart(ActionEvent actionEvent) {
        popUpMenu.setVisible(false);
        grid.setOpacity(1);
        swap();
        gamer1.setText(state.firstGamer);
        gamer2.setText(state.secondGamer);
        initialize();
        state.firstGamer=gamer1.getText();
        state.secondGamer=gamer2.getText();
        state.roundnumber=0;
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
        grid.setVisible(false);
        mainMenu.setVisible(true);
    }

    @FXML
    public void save(ActionEvent actionEvent) {
        buttonNames=new ArrayList<>();
        for(ToggleButton tgb:buttons){
            buttonNames.add(tgb.getId());
        }
        state.saveGamer(buttonNames);
    }

    @FXML
    public void load(ActionEvent actionEvent)  {
        if(state.savedIsPresent()){
            state=State.load();
            deactivateButton();
            buttons=activateButton();
            goToGame();
        }
    }

    @FXML
    public void help(ActionEvent actionEvent) {
        grid.setOpacity(0.1);
        helpMenu.setVisible(true);
    }

    @FXML
    public void back_Game(ActionEvent actionEvent) {
        grid.setOpacity(1);
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
        grid.getChildren().filtered(node -> node instanceof ToggleButton)
                .forEach(node -> Togglebuttons.add((ToggleButton) node));
        return Togglebuttons;
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
        state.setPlayerScore(buttonindex);
        buttons.get(buttonindex).setDisable(true);
        buttons.remove(buttonindex).setOpacity(0.4);
        grid.setOpacity(0.5);
        popUpMenu.setVisible(true);
        String winner=state.firstPlayerScore >state.secondPlayerScore ? state.firstGamer :
                state.firstPlayerScore <state.secondPlayerScore ? state.secondGamer :"Döntetlen";
        this.winner.setText(winner);
        state.dataset(winner);
        state.deleteTable();
    }

    public void swap(){
        String temp="";
        temp=state.firstGamer;
        state.firstGamer =state.secondGamer;
        state.secondGamer =temp;
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
        if(state.roundnumber%2==0)
            nextPlayer.setText(state.secondGamer);
        else nextPlayer.setText(state.firstGamer);

    }

    public void goToGame(){

        mainMenu.setVisible(false);
        grid.setVisible(true);
        gamer1.setText(state.firstGamer);
        gamer2.setText(state.secondGamer);
        gamerscore1.setText(state.firstPlayerScore+"");
        gamerscore2.setText(state.secondPlayerScore+"");
        error.setText("");
    }

    public void deactivateButton(){
        buttons= getAllToggleButton();
        for(int i=0;i<12;i++){
            buttons.get(i).setText(state.savedCoins.get(i).toString());
            buttons.get(i).setDisable(true);
            buttons.get(i).setOpacity(0.4);
        }
    }

    public List activateButton(){
        List<ToggleButton> buttonsNew=new ArrayList<>();
        for(String s:state.getButtonsId()){
            for (ToggleButton tgb:buttons){
                if(tgb.getId().equals(s)){
                    tgb.setDisable(false);
                    tgb.setOpacity(1);
                    buttonsNew.add(tgb);
                }
            }
        }
        return buttonsNew;
    }



}
