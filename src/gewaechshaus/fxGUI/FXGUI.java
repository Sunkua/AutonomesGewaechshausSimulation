package gewaechshaus.fxGUI;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Separator;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Screen;
import javafx.stage.Stage;


/**
 * Created by sunku on 24.05.2017.
 */
public class FXGUI extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) {


        GridPane grid = new GridPane();
        grid.setAlignment(Pos.TOP_LEFT);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));


        // Set width and height
        Scene scene = new Scene(grid, 500, 200, Color.BLACK);

        // Left Pane
        Text scenetitle = new Text("Welcome");
        scenetitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
        grid.add(scenetitle, 0, 0, 1, 1);

        Separator titleSeparator = new Separator();
        grid.add(titleSeparator, 0, 2, 2, 2);

        // Left-Right separator
        Separator leftRightSeparator = new Separator();
        leftRightSeparator.setOrientation(Orientation.VERTICAL);
        grid.add(leftRightSeparator, 2, 2, 1, 10);


        Aktionsgrid interaktionsGrid = new Aktionsgrid();

        grid.add(interaktionsGrid, 3, 2);


        // Canvas-Building, Event-Listeners redraw on rescale
        Canvas canvas = new Canvas(900, 900);
        GraphicsContext gc = canvas.getGraphicsContext2D();
        drawShapes(gc);
        canvas.widthProperty().addListener(observable -> drawShapes(gc));
        canvas.heightProperty().addListener(observable -> drawShapes(gc));
        grid.add(canvas, 0, 3, 2, 2);


        // Stage building
        stage.setScene(scene);
        stage.setTitle("Gewächshaus-Roboter");

        Rectangle2D primaryScreenBounds = Screen.getPrimary().getVisualBounds();
        //set Stage boundaries to visible bounds of the main screen
        stage.setX(primaryScreenBounds.getMinX());
        stage.setY(primaryScreenBounds.getMinY());
        stage.setWidth(primaryScreenBounds.getWidth() - 150);
        stage.setHeight(primaryScreenBounds.getHeight() - 150);

        stage.show();
    }

    private void drawShapes(GraphicsContext gc) {


    }


}