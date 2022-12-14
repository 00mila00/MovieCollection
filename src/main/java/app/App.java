package app;
import javafx.scene.image.Image;
import manager.DatabaseManager;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import org.apache.log4j.BasicConfigurator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.sql.SQLException;


public class App extends Application{

    private static final Logger logger = LoggerFactory.getLogger(App.class);

    /**
     * Main method that starts program
     */
    public static void main(String[] args) {
        BasicConfigurator.configure();
        launch(args);

    }

    /**
     * Main entry point for app
     */
    @Override
    public void start(Stage stage) throws Exception {
        logger.info("Application is starting");

        // Creating database and tables
        try (DatabaseManager databaseManager = new DatabaseManager()){
            try {
                databaseManager.createDatabase();
            } catch (SQLException e) {
                logger.info("Database was already created");
            }
            try {
                databaseManager.createAccount();
            } catch (SQLException e) {
                logger.info("Table account was already created");
            }
            try {
                databaseManager.createMovie();
            } catch (SQLException e) {
                logger.info("Table movie was already created");
            }
            try {
                databaseManager.createComment();
            } catch (SQLException e) {
                logger.info("Table comment was already created");
            }
            try {
                databaseManager.createUserFav();
            } catch (SQLException e) {
                logger.info("Table favourite video was already created");
            }
            try {
                databaseManager.createUserRates();
            } catch (SQLException e) {
                logger.info("Table user rates was already created");
            }
        } catch (SQLException e) {
            logger.info("Cant connect to database");
        }

        //Opening window for login
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(this.getClass().getResource("/fxml/loginWindow.fxml"));
        AnchorPane anchorPane = loader.load();

        Scene scene = new Scene(anchorPane);

        scene.getStylesheets().add("style/style.css");
        stage.setScene(scene);
        stage.getIcons().add(new Image("/img/cam.png"));
        stage.setTitle("MOVIECOLLECTION");
        stage.setResizable(false);
        stage.show();
    }


    /**
     * Method for changing scene
     * @param old   current pane
     * @param name  name of new window from /fxml/
     */
    public static void changeScene(Pane old, String name) {
        Parent root;
        try {
            root = FXMLLoader.load(App.class.getResource("/fxml/" + name + ".fxml"));
            Stage stage = (Stage) old.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        }
        catch (IOException e) {
            logger.error("Cant load new window.");
            e.printStackTrace();
        }
    }
}
