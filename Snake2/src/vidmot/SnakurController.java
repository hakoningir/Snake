package vidmot;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.util.Duration;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;


public class SnakurController implements Initializable {
    @FXML
    public HBox Skot;
    @FXML
    private SnakurBord snakurBord;

    @FXML
    private Label skor;

    @FXML
    private Button spila;


    private boolean daudur = false;


    /**
     * kall á stefnu snáks.
     *
     * @param s stefnan.
     */
    public void setStefna(int s) {
        snakurBord.setStefna(s);
    }

    /**
     * fall til að stoppa leik.
     *
     * @param timi tekur inn tíma leiks til að stoppa.
     */
    public static void bida(Timeline timi) {
        if (timi.getStatus() == Animation.Status.PAUSED) {
            timi.play();
        } else if (timi.getStatus() == Animation.Status.RUNNING) {
            timi.pause();
        }
    }

    /**
     * fall sem sýnir hve mörg skot snákur á.
     */
    private void updateSkot() {
        List<Node> skotCh = Skot.getChildren();
        for (int i = 0; i < skotCh.size(); i++) {
            skotCh.get(i).setVisible(i < snakurBord.getsCount());
        }
    }

    /**
     * setur átt sem að snákur er að skjóta í.
     *
     * @param dir áttin.
     */
    public void skjota(int dir) {
        snakurBord.skjota(dir);

    }

    /**
     * fall sem að hreinsar til af borðinu og endurstillir leikinn.
     */
    public void takaTil() {
        snakurBord.getChildren().clear();
        snakurBord.takaTil();
        timecount = 0;
    }

    private Timeline timi;
    private int timecount = 0;

    /**
     * leikjalykkja og köll á ýmis föll.
     */
    private void stillaTimeline() {
        KeyFrame k = new KeyFrame(Duration.millis(50),
                e -> {
                    snakurBord.afram();
                    updateSkot();
                    skor.setText("" + snakurBord.getSkor());
                    snakurBord.aframEitursnakar();
                    snakurBord.borda();
                    if (timecount > 150) {
                        daudur = snakurBord.daudur();
                    }
                    for (int i = 0; i < 5; i++) {
                        snakurBord.skotinn();
                        snakurBord.hreyfa();
                    }
                    if (timecount < 150) {
                        snakurBord.undead(timecount);
                    } else if (timecount == 151) {
                        snakurBord.notUndead();
                    }

                    if (daudur && timecount > 150) {
                        takaTil();
                        timi.stop();
                        spila.setVisible(true);
                    }

                    if (timecount % 500 == 0 || timecount == 500) {
                        snakurBord.tiusek();
                    }

                    timecount++;
                });
        timi = new Timeline(k);
        timi.setCycleCount(Timeline.INDEFINITE);
        timi.play();
    }

    /**
     * fall sem að endurstillir leik áður en hann byrjar.
     */
    public void startGame() {
        snakurBord.nyrLeikur();
        stillaTimeline();
        snakurBord.snackBreak(timi);
        spila.setVisible(false);
    }

    /**
     * fall sem að byrjar leikinn.
     *
     * @param url tekur inn URL.
     * @param rb  tekur inn ResourceBundle.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        spila.setOnAction(e -> {
            startGame();
        });
    }
}
