package agh.ics.oop.model;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;

import java.util.HashMap;
import java.util.Map;

public class WorldElementBox extends VBox {
    private static final Map<String, Image> imageCache = new HashMap<>();

    private static Image loadImage(String resourceName) {
        return imageCache.computeIfAbsent(resourceName, Image::new);
    }

    public WorldElementBox(WorldElement element) {
        Image image = loadImage(element.getResourceName());
        ImageView imageView = new ImageView(image);
        imageView.setFitHeight(20);
        imageView.setFitWidth(20);
        imageView.setPreserveRatio(true);
        Label label = new Label(element.getPosition().toString());
        this.getChildren().addAll(imageView, label);
        this.setAlignment(Pos.CENTER);
    }
}