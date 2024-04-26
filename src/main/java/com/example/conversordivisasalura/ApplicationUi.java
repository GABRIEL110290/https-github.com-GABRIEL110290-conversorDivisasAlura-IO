package com.example.conversordivisasalura;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.awt.Desktop;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("viewFx.fxml"));
        primaryStage.setTitle("Conversor de Divisas");
        primaryStage.setScene(new Scene(root, 500, 500));
        primaryStage.show();

        // Añadir imágenes a los iconos
        addIcons(root);

        // Asignar acciones a los iconos
        setIconActions(root);
    }

    private void addIcons(Parent root) {
        ImageView githubIcon = new ImageView(new Image(getClass().getResourceAsStream("icons8-github-48.png")));
        ImageView linkedinIcon = new ImageView(new Image(getClass().getResourceAsStream("icons8-linkedin-48.png")));
        ImageView whatsappIcon = new ImageView(new Image(getClass().getResourceAsStream("icons8-whatsapp-64.png")));

        // Ajustar tamaño de los iconos si es necesario
        githubIcon.setFitHeight(30);
        githubIcon.setFitWidth(30);
        linkedinIcon.setFitHeight(30);
        linkedinIcon.setFitWidth(30);
        whatsappIcon.setFitHeight(30);
        whatsappIcon.setFitWidth(30);

        // Añadir los iconos al VBox
        ((VBox) root).getChildren().addAll(githubIcon, linkedinIcon, whatsappIcon);
    }

    private void setIconActions(Parent root) {
        VBox vBox = (VBox) root;
        vBox.getChildren().forEach(node -> {
            if (node instanceof ImageView) {
                ImageView icon = (ImageView) node;
                icon.setOnMouseClicked(event -> {
                    // Obtener URL basado en el icono
                    String url = "";
                    if (icon.getImage().getUrl().contains("github")) {
                        url = "https://github.com/";
                    } else if (icon.getImage().getUrl().contains("linkedin")) {
                        url = "https://www.linkedin.com/";
                    } else if (icon.getImage().getUrl().contains("whatsapp")) {
                        url = "https://web.whatsapp.com/";
                    }

                    // Abrir enlace en el navegador
                    try {
                        Desktop.getDesktop().browse(new URI(url));
                    } catch (IOException | URISyntaxException e) {
                        e.printStackTrace();
                    }
                });
            }
        });
    }

    public static void main(String[] args) {
        launch(args);
    }
}

