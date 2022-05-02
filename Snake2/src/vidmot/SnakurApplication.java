package vidmot;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

import java.util.HashMap;


public class SnakurApplication extends Application {
    private static final int UPP = 90;
    private static final int VINSTRI = 180;
    private static final int NIDUR = 270;
    private static final int HAEGRI = 360;
    private static int SKJOTA;
    private int dir;

    private final HashMap<KeyCode, Integer> map = new HashMap<KeyCode, Integer>();


    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("snakurAdal-view.fxml"));
        Parent root = loader.load();

        SnakurController sc = loader.getController();
        primaryStage.setTitle("Snake");
        Scene s = new Scene(root, 1000, 1000);
        orvatakkar(sc, s);
        primaryStage.setScene(s);
        primaryStage.show();
    }

    /**
     * Fall sem að lætur örvatakka og space stýra snák og leyfir honum að skóta.
     *
     * @param sc kall á controller.
     * @param s  kall á senu.
     */
    private void orvatakkar(SnakurController sc, Scene s) {
        map.put(KeyCode.UP, UPP);
        map.put(KeyCode.RIGHT, HAEGRI);
        map.put(KeyCode.DOWN, NIDUR);
        map.put(KeyCode.LEFT, VINSTRI);
        map.put(KeyCode.SPACE, SKJOTA);
        s.addEventHandler(KeyEvent.ANY,
                event -> {
                    if (KeyCode.SPACE == event.getCode()) {
                        sc.skjota(dir);
                    }
                    if (KeyCode.UP == event.getCode()) {
                        sc.setStefna(map.get(event.getCode()));
                        dir = map.get(event.getCode());
                    } else if (KeyCode.RIGHT == event.getCode()) {
                        sc.setStefna(map.get(event.getCode()));
                        dir = map.get(event.getCode());
                    } else if (KeyCode.DOWN == event.getCode()) {
                        sc.setStefna(map.get(event.getCode()));
                        dir = map.get(event.getCode());
                    } else if (KeyCode.LEFT == event.getCode()) {
                        sc.setStefna(map.get(event.getCode()));
                        dir = map.get(event.getCode());
                    }
                });
    }


    public static void main(String[] args) {
        launch(args);
    }
}
