// Final Project
// CIS2348 - Natalia Fofanova
// Code by Edreih Aldana

import com.opencsv.CSVReader;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import java.io.*;
import java.sql.*;

public class ContactDBMain extends Application {
    private TableView table = new TableView();
    ListView listView;
    TextField fName, lName, email, phone, address, company;
    TextArea notes;
    Button newcontact, view, delete, edit, exit, loadTable, export, importDB;
    ComboBox record;
    HBox controls, extra;
    VBox list, layout;
    ObservableList names = FXCollections.observableArrayList();

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) {
        Scene scene = new Scene(new Group());
        stage.setTitle("Contact DB GUI");
        stage.setWidth(350);
        stage.setHeight(520);

        // Image
        VBox logo = new VBox();
        Image image = new Image("logo.png");
        ImageView iv1 = new ImageView();
        iv1.setImage(image);
        logo.setAlignment(Pos.CENTER);
        logo.getChildren().addAll(iv1);

        // Select a name label
        final Label label1 = new Label("Select a Name");
        label1.setFont(new Font("Arial", 16));
        label1.setStyle("-fx-font-weight: bold");

        // VBox for List
        list = new VBox();
        list.setAlignment(Pos.CENTER);
        listView = new ListView(names);
        listView.setPrefSize(200, 200);
        listView.setStyle("-fx-border-color: purple; -fx-border-width: 3px");
        fillListBox(); // Fills ListView with First Names from Database
        list.getChildren().addAll(listView);

        // Select an action label
        final Label label2 = new Label("Select an Action");
        label2.setFont(new Font("Arial", 16));
        label2.setStyle("-fx-font-weight: bold");
        label2.setPadding(new Insets(10, 0, 0, 0));

        // HBox for Controls
        controls = new HBox();
        controls.setSpacing(10);
        newcontact = new Button("New");
        view = new Button("View");
        delete = new Button("Delete");
        delete.setStyle("-fx-text-fill: red; -fx-font-weight: bold");
        edit = new Button("Edit");
        exit = new Button("Exit");
        controls.getChildren().addAll(newcontact, view, delete, edit, exit);

        // Validate name is selected
        if (listView.getSelectionModel().isEmpty()){
            view.setDisable(true);
            delete.setDisable(true);
            edit.setDisable(true);
        }
        listView.setOnMouseClicked(l -> {
            view.setDisable(false);
            delete.setDisable(false);
            edit.setDisable(false);
        });

        // Database Tools label
        Image image2 = new Image("tool.png");
        ImageView iv2 = new ImageView();
        iv2.setImage(image2);
        final Label label3 = new Label("Database Tools", iv2);
        label3.setFont(new Font("Arial", 16));
        label3.setStyle("-fx-font-weight: bold");
        label3.setPadding(new Insets(10, 0, 0, 0));

        // HBox for Extra Controls
        extra = new HBox();
        extra.setSpacing(10);
        loadTable = new Button("Show Table");
        export = new Button("Export to Excel");
        importDB = new Button("Import CSV");
        extra.getChildren().addAll(loadTable, export, importDB);

        // Add all elements to layout
        layout = new VBox();
        layout.setSpacing(5);
        layout.setPadding(new Insets(10, 0, 0, 10));
        layout.getChildren().addAll(logo, label1, list, label2, controls, label3, extra);

        ((Group) scene.getRoot()).getChildren().addAll(layout);
        stage.setScene(scene);
        stage.show();

        // newcontact button
        newcontact.setOnAction(e -> {
            BorderPane layout = new BorderPane();
            Stage record = new Stage();
            record.initModality(Modality.APPLICATION_MODAL);
            record.setTitle("New Record");
            layout.setPadding(new Insets(10, 10, 10, 10));

            // LEFT
            VBox leftMenu = new VBox();
            Label lbl = new Label("First Name");
            Label lbl2 = new Label("Last Name");
            Label lbl3 = new Label("Email");
            Label lbl4 = new Label("Phone");
            Label lbl5 = new Label("Address");
            Label lbl6 = new Label("Company");
            Label lbl7 = new Label("Notes");
            leftMenu.getChildren().addAll(lbl, lbl2, lbl3, lbl4, lbl5, lbl6, lbl7);
            leftMenu.setSpacing(15);

            // RIGHT
            VBox rightMenu = new VBox();
            rightMenu.setPrefWidth(100);
            TextField fName = new TextField();
            TextField lName = new TextField();
            TextField email = new TextField();
            TextField phone = new TextField();
            TextField address = new TextField();
            TextField company = new TextField();
            TextArea notes = new TextArea();
            notes.setMaxHeight(100);
            notes.setEditable(true);
            rightMenu.getChildren().addAll(fName, lName, email, phone, address, company, notes);
            rightMenu.setSpacing(5);
            rightMenu.setPadding(new Insets(0, 0, 0, 5));

            // BOTTOM
            HBox bottom = new HBox();
            Button save = new Button("Save");
            save.setPadding(new Insets(5, 10, 5, 10));
            Button clear = new Button("Clear");
            clear.setPadding(new Insets(5, 10, 5, 10));
            bottom.setSpacing(10);
            bottom.setAlignment(Pos.BOTTOM_RIGHT);
            bottom.getChildren().addAll(save, clear);

            // Add left, right, bottom menus
            layout.setLeft(leftMenu);
            layout.setCenter(rightMenu);
            layout.setBottom(bottom);

            // clear button action
            clear.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    fName.clear();
                    lName.clear();
                    email.clear();
                    phone.clear();
                    address.clear();
                    company.clear();
                    notes.clear();
                }
            });

            save.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    // Create Record
                    try {
                        final String DB_URL = "jdbc:derby:ContactDB";
                        // Create a connection to the database.
                        Connection conn = DriverManager.getConnection(DB_URL);

                        // Create a string with a SELECT statement.
                        String sqlStatement = "INSERT INTO Contact (FirstName, LastName, Email, Phone, Address, Company, Notes) VALUES(?,?,?,?,?,?,?)";

                        // Create a Statement object.
                        PreparedStatement stmt = conn.prepareStatement(sqlStatement);

                        stmt.setString(1, fName.getText());
                        stmt.setString(2, lName.getText());
                        stmt.setString(3, email.getText());
                        stmt.setString(4, phone.getText());
                        stmt.setString(5, address.getText());
                        stmt.setString(6, company.getText());
                        stmt.setString(7, notes.getText());

                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setTitle("Information");
                        alert.setHeaderText(null);
                        alert.setContentText("New contact has been added to the database.");
                        alert.showAndWait();

                        stmt.execute();
                        stmt.close();
                        conn.close();

                        //clear fields
                        fName.clear();
                        lName.clear();
                        email.clear();
                        phone.clear();
                        address.clear();
                        company.clear();
                        notes.clear();
                    } catch (Exception ex) {
                        System.out.println("ERROR: " + ex.getMessage());
                    }
                    fillListBox(); // refresh listview
                }
            });
            Scene scene1 = new Scene(layout, 300, 400);
            record.setScene(scene1);
            record.showAndWait();
        });

        // View Button
        view.setOnAction(e -> {
            BorderPane layout = new BorderPane();
            Stage record = new Stage();
            record.initModality(Modality.APPLICATION_MODAL);
            record.setTitle("View Record");
            layout.setPadding(new Insets(10, 10, 10, 10));

            // LEFT
            VBox leftMenu = new VBox();
            Label lbl = new Label("First Name");
            Label lbl2 = new Label("Last Name");
            Label lbl3 = new Label("Email");
            Label lbl4 = new Label("Phone");
            Label lbl5 = new Label("Address");
            Label lbl6 = new Label("Company");
            Label lbl7 = new Label("Notes");
            leftMenu.getChildren().addAll(lbl, lbl2, lbl3, lbl4, lbl5, lbl6, lbl7);
            leftMenu.setSpacing(15);

            // RIGHT
            VBox rightMenu = new VBox();
            rightMenu.setPrefWidth(100);
            fName = new TextField();
            lName = new TextField();
            email = new TextField();
            phone = new TextField();
            address = new TextField();
            company = new TextField();
            notes = new TextArea();
            notes.setMaxHeight(100);
            // Editable false for every field
            fName.setEditable(false);
            lName.setEditable(false);
            email.setEditable(false);
            phone.setEditable(false);
            address.setEditable(false);
            company.setEditable(false);
            notes.setEditable(false);
            rightMenu.getChildren().addAll(fName, lName, email, phone, address, company, notes);
            rightMenu.setSpacing(5);
            rightMenu.setPadding(new Insets(0, 0, 0, 5));

            // Add left and right menus
            layout.setLeft(leftMenu);
            layout.setCenter(rightMenu);

            fillFields(); // fill record

            Scene scene1 = new Scene(layout, 300, 340);
            record.setScene(scene1);
            record.showAndWait();
        });

        // Edit button
        edit.setOnAction(e -> {
            BorderPane layout = new BorderPane();
            Stage record = new Stage();
            record.initModality(Modality.APPLICATION_MODAL);
            record.setTitle("Edit Record");
            layout.setPadding(new Insets(10, 10, 10, 10));

            // LEFT
            VBox leftMenu = new VBox();
            Label lbl = new Label("First Name");
            Label lbl2 = new Label("Last Name");
            Label lbl3 = new Label("Email");
            Label lbl4 = new Label("Phone");
            Label lbl5 = new Label("Address");
            Label lbl6 = new Label("Company");
            Label lbl7 = new Label("Notes");
            leftMenu.getChildren().addAll(lbl, lbl2, lbl3, lbl4, lbl5, lbl6, lbl7);
            leftMenu.setSpacing(15);

            // RIGHT
            VBox rightMenu = new VBox();
            rightMenu.setPrefWidth(100);
            fName = new TextField();
            lName = new TextField();
            email = new TextField();
            phone = new TextField();
            address = new TextField();
            company = new TextField();
            notes = new TextArea();
            notes.setMaxHeight(100);
            notes.setEditable(true);
            rightMenu.getChildren().addAll(fName, lName, email, phone, address, company, notes);
            rightMenu.setSpacing(5);
            rightMenu.setPadding(new Insets(0, 0, 0, 5));

            // BOTTOM
            HBox bottom = new HBox();
            Button update = new Button("Update");
            update.setPadding(new Insets(5, 10, 5, 10));
            bottom.setAlignment(Pos.BOTTOM_RIGHT);
            bottom.getChildren().addAll(update);

            // Add left, right, bottom menus
            layout.setLeft(leftMenu);
            layout.setCenter(rightMenu);
            layout.setBottom(bottom);

            fillFields(); // fill record

            // Update record
            update.setOnAction(u -> {
                try {
                    final String DB_URL = "jdbc:derby:ContactDB";
                    // Create a connection to the database.
                    Connection conn = DriverManager.getConnection(DB_URL);

                    // Create a string with a SELECT statement.
                    //String sqlStatement = "UPDATE Contact SET FirstName=?, LastName=?, Email=?, Phone=?, Address=?, Company=?, Notes=? WHERE FirstName='"+fName.getText()+"'";
                    String sqlStatement = "UPDATE Contact SET FirstName=?, LastName=?, Email=?, Phone=?, Address=?, Company=?, Notes=? WHERE FirstName=?";

                    // Create a Statement object.
                    PreparedStatement stmt = conn.prepareStatement(sqlStatement);

                    stmt.setString(1, fName.getText());
                    stmt.setString(2, lName.getText());
                    stmt.setString(3, email.getText());
                    stmt.setString(4, phone.getText());
                    stmt.setString(5, address.getText());
                    stmt.setString(6, company.getText());
                    stmt.setString(7, notes.getText());
                    stmt.setString(8, (String) listView.getSelectionModel().getSelectedItem());

                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Information");
                    alert.setHeaderText(null);
                    alert.setContentText("Contact has been updated in the database.");
                    alert.showAndWait();

                    stmt.execute();
                    stmt.close();
                    conn.close();
                } catch (Exception ex) {
                    System.out.println("ERROR: " + ex.getMessage());
                }
                fillListBox(); // refresh listview
            });

            Scene scene1 = new Scene(layout, 300, 400);
            record.setScene(scene1);
            record.showAndWait();
        });

        // Delete button
        delete.setOnAction(e -> {
            final String DB_URL = "jdbc:derby:ContactDB";
            try {
                // Create a connection to the database.
                Connection conn = DriverManager.getConnection(DB_URL);

                // sql statement
                String sqlStatement = "DELETE FROM Contact WHERE FirstName=?";

                // Create a Statement object.
                PreparedStatement stmt = conn.prepareStatement(sqlStatement);
                stmt.setString(1, (String) listView.getSelectionModel().getSelectedItem());
                stmt.executeUpdate();

                // Close the connection.
                conn.close();
            } catch (Exception ex) {
                System.out.println("ERROR: " + ex.getMessage());
            }
            fillListBox();
        });

        // Exit Button
        exit.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Platform.exit();
            }
        });

        loadTable.setOnAction(t -> {
            BorderPane layout = new BorderPane();
            Stage record = new Stage();
            record.initModality(Modality.APPLICATION_MODAL);
            record.setTitle("Contact Database Table View");
            layout.setPadding(new Insets(10, 10, 10, 10));

            // CENTER
            VBox tableView = new VBox();
            table.setEditable(true);

            TableColumn firstname = new TableColumn("First Name");
            firstname.setMinWidth(40);
            firstname.setCellValueFactory(
                    new PropertyValueFactory<ContactClass, String>("firstname"));

            TableColumn lastname = new TableColumn("Last Name");
            lastname.setMinWidth(40);
            lastname.setCellValueFactory(
                    new PropertyValueFactory<ContactClass, String>("lastname")
            );

            TableColumn email = new TableColumn("Email");
            email.setMinWidth(200);
            email.setCellValueFactory(
                    new PropertyValueFactory<ContactClass, String>("email")
            );

            TableColumn phone = new TableColumn("Phone");
            phone.setMinWidth(100);
            phone.setCellValueFactory(
                    new PropertyValueFactory<ContactClass, String>("phone")
            );

            TableColumn address = new TableColumn("Address");
            address.setMinWidth(200);
            address.setCellValueFactory(
                    new PropertyValueFactory<ContactClass, String>("address")
            );

            TableColumn company = new TableColumn("Company");
            company.setMinWidth(200);
            company.setCellValueFactory(
                    new PropertyValueFactory<ContactClass, String>("company")
            );

            TableColumn notes = new TableColumn("Notes");
            notes.setMaxWidth(200);
            notes.setCellValueFactory(
                    new PropertyValueFactory<ContactClass, String>("notes")
            );

            table.setItems(getDataObsList());
            table.getColumns().addAll(firstname, lastname, email, phone, address, company, notes);
            tableView.getChildren().addAll(table);


            // Add left, right, center, bottom elements
            layout.setCenter(tableView);

            Scene scene1 = new Scene(layout, 1100, 450);
            record.setScene(scene1);
            record.showAndWait();
        });

        export.setOnAction(x -> {
            try {
                final String DB_URL = "jdbc:derby:ContactDB";
                // Create a connection to the database.
                Connection conn = DriverManager.getConnection(DB_URL);

                String sqlStatement = "SELECT * FROM Contact";

                // Create a Statement object.
                PreparedStatement stmt = conn.prepareStatement(sqlStatement);


                // Send the statement to the DBMS.
                ResultSet result = stmt.executeQuery();

                XSSFWorkbook wb = new XSSFWorkbook();
                XSSFSheet sheet = wb.createSheet("Contact Database");

                XSSFRow header = sheet.createRow(0);
                header.createCell(0).setCellValue("First Name");
                header.createCell(1).setCellValue("Last Name");
                header.createCell(2).setCellValue("Email");
                header.createCell(3).setCellValue("Phone");
                header.createCell(4).setCellValue("Address");
                header.createCell(5).setCellValue("Company");
                header.createCell(6).setCellValue("Notes");

                sheet.autoSizeColumn(0);
                sheet.autoSizeColumn(1);
                sheet.autoSizeColumn(2);
                sheet.autoSizeColumn(3);
                sheet.autoSizeColumn(4);
                sheet.autoSizeColumn(5);
                sheet.autoSizeColumn(6);
                //sheet.setColumnWidth(3, 256*25);//256-character width
                sheet.setZoom(100);//scale-100%

                int index = 1;
                while (result.next()) {
                    XSSFRow row = sheet.createRow(index);
                    row.createCell(0).setCellValue(result.getString("FirstName"));
                    row.createCell(1).setCellValue(result.getString("LastName"));
                    row.createCell(2).setCellValue(result.getString("Email"));
                    row.createCell(3).setCellValue(result.getString("Phone"));
                    row.createCell(4).setCellValue(result.getString("Address"));
                    row.createCell(5).setCellValue(result.getString("Company"));
                    row.createCell(6).setCellValue(result.getString("Notes"));
                    index++;
                }

                FileOutputStream fileOut = new FileOutputStream("Contact_DB_Export.xlsx");
                wb.write(fileOut);
                fileOut.close();

                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Information");
                alert.setHeaderText(null);
                alert.setContentText("Contact Database Exported to Excel Successfully.");
                alert.showAndWait();

                // Close the connection.
                stmt.close();
                conn.close();
            } catch (Exception ex) {
                System.out.println("ERROR: " + ex.getMessage());
            }
        });

        importDB.setOnAction(i ->{
            try {
                final String DB_URL = "jdbc:derby:ContactDB";
                Connection conn = DriverManager.getConnection(DB_URL);
                CSVLoader loader = new CSVLoader(conn);

                loader.loadCSV("ImportToDB.csv", "Contact", false);

            } catch (Exception e) {
                e.printStackTrace();
            }
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Information");
            alert.setHeaderText(null);
            alert.setContentText("Contacts Imported into Database Successfully.");
            alert.showAndWait();
            fillListBox();
        });
    }

    public ObservableList<ContactClass> getDataObsList() {
        final String DB_URL = "jdbc:derby:ContactDB";
        ObservableList<ContactClass> dbData = FXCollections.observableArrayList();

        try {
            // Create a connection to the database.
            Connection conn = DriverManager.getConnection(DB_URL);

            // Create a Statement object.
            Statement stmt = conn.createStatement();

            // Create a string with a SELECT statement.
            String sqlStatement = "SELECT * FROM Contact";

            // Send the statement to the DBMS.
            ResultSet result = stmt.executeQuery(sqlStatement);

            // Display the contents of the result set.
            while (result.next()) {
                dbData.add(new ContactClass(result.getString("FirstName"),
                        result.getString("LastName"),
                        result.getString("Email"),
                        result.getString("Phone"),
                        result.getString("Address"),
                        result.getString("Company"),
                        result.getString("Notes")));
            }

            // Close the connection.
            conn.close();
        } catch (Exception ex) {
            System.out.println("ERROR: " + ex.getMessage());
        }
        return dbData;
    }

    public void fillListBox() {
        names.clear();
        final String DB_URL = "jdbc:derby:ContactDB";
        try {
            // Create a connection to the database.
            Connection conn = DriverManager.getConnection(DB_URL);

            // Create a Statement object.
            Statement stmt = conn.createStatement();

            // Create a string with a SELECT statement.
            String sqlStatement = "SELECT FirstName FROM Contact";

            // Send the statement to the DBMS.
            ResultSet result = stmt.executeQuery(sqlStatement);

            // Display the contents of the result set.
            while(result.next()) {
                names.add(result.getString("FirstName"));
            }

            // Close the connection.
            conn.close();
        } catch (Exception ex) {
            System.out.println("ERROR: " + ex.getMessage());
        }
    }

    public void fillFields() {
        // Fill record
        final String DB_URL = "jdbc:derby:ContactDB";
        try {
            // Create a connection to the database.
            Connection conn = DriverManager.getConnection(DB_URL);

            // Create a string with a SELECT statement.
            String sqlStatement = "SELECT * FROM Contact WHERE FirstName = ?";

            // Create a Statement object.
            PreparedStatement stmt = conn.prepareStatement(sqlStatement);

            stmt.setString(1, (String)listView.getSelectionModel().getSelectedItem());

            // Send the statement to the DBMS.
            ResultSet result = stmt.executeQuery();

            // Display the contents of the result set.
            while(result.next()) {
                fName.setText(result.getString("FirstName"));
                lName.setText(result.getString("LastName"));
                email.setText(result.getString("Email"));
                phone.setText(result.getString("Phone"));
                address.setText(result.getString("Address"));
                company.setText(result.getString("Company"));
                notes.setText(result.getString("Notes"));
            }

            // Close the connection.
            stmt.close();
            conn.close();
        } catch (Exception ex) {
            System.out.println("ERROR: " + ex.getMessage());
        }
    }

}
