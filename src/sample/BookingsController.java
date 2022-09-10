package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import sample.connectivity.ConnectionClass;


import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javax.swing.JOptionPane;

public class BookingsController  implements Initializable {
    @FXML
    private TableView <ViewBookings> table;
    @FXML
    private TableColumn <ViewBookings, String> name;
    @FXML
    public TableColumn<ViewBookings,Integer> phone;
    @FXML
    public TableColumn <ViewBookings,String>source;
    @FXML
    public TableColumn<ViewBookings,String> destination;
    @FXML
    public TableColumn <ViewBookings,String>service;
    public TableColumn <ViewBookings, Time>depart;
    public TableColumn <ViewBookings,Time> arrival;
    @FXML
    public TableColumn <ViewBookings,String>date;
    @FXML
    public TableColumn <ViewBookings,String>seats;
    @FXML
    public TableColumn <ViewBookings,Integer> amount;
    @FXML
    private Button btnupdate;
    @FXML
    private Button btndelete;
    @FXML
    private TextField nametxt;
    @FXML
    private TextField sourcetxt;
    @FXML
    private TextField servicetxt;
    @FXML
    private TextField txt;
    @FXML
    private TextField phonetxt;
    @FXML
    private TextField destinationtxt;
    @FXML
    private TextField datetxt;
    @FXML
    private TextField totaltxt;
    @FXML
    private Button clear;



    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initCol();
        load();
        setcellvalue();

    }
    private void initCol(){
        name.setCellValueFactory(new PropertyValueFactory<>("name"));
        phone.setCellValueFactory(new PropertyValueFactory<>("phone"));
        source.setCellValueFactory(new PropertyValueFactory<>("source"));
        destination.setCellValueFactory(new PropertyValueFactory<>("destination"));
        service.setCellValueFactory(new PropertyValueFactory<>("service"));
        
        date.setCellValueFactory(new PropertyValueFactory<>("date"));
        seats.setCellValueFactory(new PropertyValueFactory<>("seats"));
        amount.setCellValueFactory(new PropertyValueFactory<>("amount"));


    }
    private void load(){
        ObservableList<ViewBookings> list = FXCollections.observableArrayList();
        ConnectionClass connectionClass = new ConnectionClass();
        Connection connection = connectionClass.getConnection();
        PreparedStatement pst;
        try {
            pst=connection.prepareStatement("select * from bookings");
            ResultSet resultSet = pst.executeQuery();

            while (resultSet.next()){
                list.add(new ViewBookings(resultSet.getString(1),
                        resultSet.getString(2),
                        resultSet.getString(3),
                        resultSet.getString(4),
                        resultSet.getString(5),
                        resultSet.getString(6),
                        resultSet.getString(7),
                        resultSet.getString(8)));

                table.setItems(list);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }


    }

    
    @FXML
    public void addbus(ActionEvent actionEvent) {

        try {
            Parent parent = FXMLLoader.load(getClass().getResource("AddBus.fxml"));
            Scene scene = new Scene(parent);
            Stage window = (Stage)((Node) actionEvent.getSource()).getScene().getWindow();
            window.setScene(scene);
            window.show();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    @FXML
    public void logout(ActionEvent actionEvent) {


        try {
            Parent parent = FXMLLoader.load(getClass().getResource("sample.fxml"));
            Scene scene = new Scene(parent);
            Stage window = (Stage)((Node) actionEvent.getSource()).getScene().getWindow();
            window.setScene(scene);
            window.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private void setcellvalue(){
        table.setOnMouseClicked(e-> {
            ViewBookings vb = table.getItems().get(table.getSelectionModel().getSelectedIndex());
            nametxt.setText(vb.getName());
            phonetxt.setText(vb.getPhone());
            sourcetxt.setText(vb.getSource());
            destinationtxt.setText(vb.getDestination());
            servicetxt.setText(vb.getService());
            datetxt.setText(vb.getDate());
            txt.setText(vb.getSeats());
            totaltxt.setText(vb.getAmount());



        });
        }
    ConnectionClass co = new ConnectionClass();
    @FXML
    private void update(ActionEvent event) {
        String sql = "update bookings set  name='"+nametxt.getText()+"',phone='"+phonetxt.getText()+"',source='"+sourcetxt.getText()+"',destination='"+destinationtxt.getText()+"',service='"+servicetxt.getText()+"',date='"+datetxt.getText()+"',seats='"+txt.getText()+"',amount='"+totaltxt.getText()+"'  where amount='"+totaltxt.getText()+"'";
        co.setSQL(sql);
        load();
    }

    @FXML
    private void delete(ActionEvent event) {
        String sql = "Delete from bookings where name='"+nametxt.getText()+"'";
        if(JOptionPane.showConfirmDialog(null,"Ma hubtaa inaad delete-gareesid!.","Ma hubtaa",
                JOptionPane.YES_NO_OPTION)==JOptionPane.YES_OPTION){
            co.setSQL(sql);
            load();
        }
        load();
    }

    @FXML
    private void clear(ActionEvent event) {
        nametxt.setText("");
        phonetxt.setText("");
        sourcetxt.setText("");
        destinationtxt.setText("");
        servicetxt.setText("");
        datetxt.setText("");
        txt.setText("");
        totaltxt.setText("");
    }
}
