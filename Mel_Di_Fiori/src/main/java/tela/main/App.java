package tela.main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.Parent;
import javafx.stage.Stage;
import javafx.stage.Screen;

public class App extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        // Carrega a tela de login
        Parent root = FXMLLoader.load(getClass().getResource("/telas/view/TelaLogin.fxml"));

        Scene scene = new Scene(root);
        
        // Configura a janela para tela cheia
        primaryStage.setTitle("Login - Sistema de Adestramento");
        primaryStage.setScene(scene);
        primaryStage.setMaximized(true); // Tela cheia
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}