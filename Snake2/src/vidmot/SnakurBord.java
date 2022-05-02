package vidmot;

import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.util.ArrayList;


/******************************************************
 *   Nafn    : Hákon Ingi Rafnsson
 *   T-póstur: hir12@hi.is
 *
 *   Lýsing  : Klasi fyrir borð sem að heldur utan um flest allar aðgerðir sem gerðar eru á borðinu.
 *
 ******************************************************/
public class SnakurBord extends Pane {

    private ObservableList<Eitursnakur> e = FXCollections.observableArrayList();
    private ArrayList<Eitursnakur> eDaudur = new ArrayList<>();
    private ObservableList<Faeda> allSnack = FXCollections.observableArrayList();
    private ArrayList<Rectangle> snakurTail = new ArrayList<>();
    private Snakur snakur = new Snakur(2, 2, 10, 10); // hugsa aftur hvernig snákurinn byrjar
    private double lastX = 0;
    private double lastY = 0;
    private boolean daudursnakur = false;
    private Skot skot;
    private int sCount = 3;
    private double speed = 5;
    private double moveCount = 10;
    private int skor = 0;

    /**
     * Fall sem að byrjar nýjan leik með því að stilla öllu upp.
     */
    public void nyrLeikur() {
        skor = 0;
        nyrEitursnakur(3);
        erArekstur();
        eldaMat(3, Faeda.type.SHOT);
        snakurL(40, 40, 10, 10, 3);
        buaTilSkot(new Skot(-100, -100, 15, 5));
    }

    /**
     * Fall sem að tekur allt af borði og endurstillir hraða o.fl.
     */
    public void takaTil() {
        e.clear();
        eDaudur.clear();
        allSnack.clear();
        snakurTail.clear();
        speed = 5;
        moveCount = 10;
        sCount = 3;
        daudursnakur = false;
    }

    /**
     * Boolean fall sem að athugar hvort snákur sé að rekast á einhvern part af eitursnák.
     *
     * @return skilar true ef hann er að rekast á eitursnákinn, annars false.
     */
    public boolean erArekstur() {
        for (Eitursnakur eitursnakur : e) {
            for (Rectangle rectangle : snakurTail) {
                if (eitursnakur.intersects(rectangle.getBoundsInParent())) {
                    return true;
                }
                for (Rectangle eiturRect : eitursnakur.getEiturTail()) {
                    if (eiturRect.intersects(rectangle.getBoundsInParent())) { // gera svo að hann geti klesst á allan snákinn
                        return true;
                    }
                }
            }
        }
        return false;
    }

    /**
     * Boolean fall sem að athugar hvort snákur sé dauður eða ekki.
     *
     * @return skilar boolean breytu fyrir snák.
     */
    public boolean daudur() {
        if (erArekstur()) daudursnakur = true;
        return daudursnakur;
    }

    /**
     * Void fall sem að setur mat á borðið.
     *
     * @param fjoldiSnack fjöldi matar sem er á borðinu í einu.
     * @param type        týpa mats á borðinu.
     */
    public void eldaMat(int fjoldiSnack, Faeda.type type) {
        for (int i = 0; i < fjoldiSnack; i++) {
            int j = (int) (Math.random() * getMinWidth());
            int k = (int) (Math.random() * getMinHeight());
            Faeda snack = new Faeda(j, k, 15, type);
            getChildren().addAll(snack);
            allSnack.add(snack);
        }
    }

    public int getSkor() {
        return skor;
    }

    public int getsCount() {
        return sCount;
    }

    /**
     * Void fall sem að segir til hvort snákur sé að borða eða ekki.
     * Sér um að taka mat af borði og setja annan á borðið ef að snákur borðar.
     * Stækkar snákinn ef að hann borðar og gefur honum mismunandi "upgrade" ef að hann borðar.
     */
    public void borda() {
        for (int i = 0; i < allSnack.size(); i++) {
            for (Rectangle rectangle : snakurTail) {
                if (allSnack.get(i).intersects(snakur.getBoundsInParent()) || allSnack.get(i).intersects(rectangle.getBoundsInParent())) {
                    //tekur mat í burtu þegar snákur fer yfir hann.
                    Faeda q = allSnack.remove(i);
                    getChildren().remove(q);
                    // setur annan mat þegar búið er að borða hann
                    if (q.getT() == Faeda.type.SHOT) {
                        eldaMat(1, Faeda.type.SPEED);
                        sCount = Math.min(5, sCount + 3);
                    } else {
                        eldaMat(1, Faeda.type.SHOT);
                        speed = Math.min(10, speed + 0.5);
                    }
                    // stækkar snákinn og litar
                    Rectangle r = new Rectangle(lastX, lastY, 10, 10);
                    if ((snakurTail.size()) % 2 == 1) {
                        r.setFill(Color.RED);
                    } else r.setFill(Color.BLUE);
                    snakurTail.add(r);
                    getChildren().add(r);
                    skor += 10;
                    break;
                }
            }
        }
    }

    /**
     * Passar upp á það að snákur geti ekki dáið strax í byrjun leiks.
     *
     * @param time tími moddaður við stærð snáks sem er síðan notað til að sýna að hann geti ekki dáið.
     */
    public void undead(int time) {
        int index = time % snakurTail.size();
        snakurTail.get(index).setFill(Color.BLANCHEDALMOND);
        if (index == 0) {
            index = snakurTail.size();
        }
        if (index % 2 == 0) {
            snakurTail.get(index - 1).setFill(Color.BLUE);
        } else snakurTail.get(index - 1).setFill(Color.RED);
    }

    /**
     * litar snák aftur eftir tímabilið þegar hann getur ekki dáið.
     */
    public void notUndead() {
        for (int i = 0; i < snakurTail.size(); i++) {
            if (i % 2 == 0) {
                snakurTail.get(i).setFill(Color.BLUE);
            } else snakurTail.get(i).setFill(Color.RED);
        }
    }

    /**
     * Fall sem að býr til skot og setur á borðið.
     *
     * @param skot tekur inn skot til að lita það o.fl.
     */
    public void buaTilSkot(Skot skot) {
        this.skot = skot;
        skot.setFill(Color.MEDIUMPURPLE);
        skot.setCenterX(-5000);
        getChildren().add(skot);
    }

    /**
     * Fall sem að sér um það þegar snákur skýtur. sér einnig um fjölda skota sem að hann á eftir.
     *
     * @param dir átt sem snákur er að skjóta í.
     */
    public void skjota(int dir) {
        if (sCount < 6 && sCount > 0 && !skot.getActive()) {
            skot.skjota();
            double skotX = snakur.getX();
            double skotY = snakur.getY();
            skot.setCenterX(skotX);
            skot.setCenterY(skotY);
            skot.setRotate(dir);
            sCount--;
        }
    }

    /**
     * Hreyfir skotið.
     */
    public void hreyfa() {
        skot.afram();
    }

    /**
     * Athugar hvort eitursnákur sé skotinn eða ekki. Bætir við stigum ef hann er skotinn.
     */
    public void skotinn() {
        ArrayList<Eitursnakur> daudur = new ArrayList<>();
        for (Eitursnakur eitursnakur : e) {
            for (int i = 0; i < eitursnakur.getEiturTail().size(); i++) {
                if (skot.intersects(eitursnakur.getBoundsInParent()) || skot.intersects(eitursnakur.getEiturTail().get(i).getBoundsInParent())) {
                    getChildren().remove(eitursnakur);
                    for (Rectangle r : eitursnakur.getEiturTail()) {
                        getChildren().remove(r);
                        // I am speed
                    }
                    eDaudur.add(eitursnakur);
                    daudur.add(eitursnakur);
                    skor += 10;
                    break;
                }
            }
        }
        e.removeAll(daudur);
    }

    /**
     * Á rúmlega 10 sek fresti eru settir nýjir eitursnákar. Þeir eru aldrei fleiri en 3 og ef að þeir eru 3 þá gerir þetta ekki neitt.
     */
    public void tiusek() {
        ArrayList<Eitursnakur> takeout = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            if (i > eDaudur.size() - 1) {
                break;
            }
            int x, y;
            x = (int) (Math.random() * 1000);
            y = (int) (Math.random() * 1000);
            while (x == snakur.getX() || y == snakur.getY()) { //extra
                x = (int) (Math.random() * 1000);
                y = (int) (Math.random() * 1000);
            }
            e.add(eDaudur.get(i));
            getChildren().addAll(eDaudur.get(i).getEiturTail());
            takeout.add(eDaudur.get(i));
            takeout.get(i).setX(x);
            takeout.get(i).setY(y);
            getChildren().add(eDaudur.get(i));
        }
        eDaudur.removeAll(takeout);
    }

    /**
     * Fall sem að býr til snák, litar hann og setur hann á borð.
     *
     * @param x      x hnit snáks.
     * @param y      y hnir snáks.
     * @param width  breydd snáks.
     * @param height hæð snáks.
     * @param length lengd snáks.
     */
    public void snakurL(int x, int y, int width, int height, int length) {
        snakur = new Snakur(x, y, width, height);
        snakurTail = new ArrayList<>();
        for (int i = 1; i <= length; i++) {
            Rectangle r = new Rectangle(x + i * width, y, width, height);
            if (i % 2 == 0) {
                r.setFill(Color.BLUE);
            } else r.setFill(Color.RED);
            snakurTail.add(r);
            getChildren().add(r);
        }
        getChildren().add(snakur);
    }

    /**
     * Fall sem að býr til nýja eitursnáka.
     *
     * @param fjoldiEitursnaka fjöldi eitursnáka á borðinu.
     */
    private void nyrEitursnakur(int fjoldiEitursnaka) {
        int wh = 10;
        for (int i = 0; i < fjoldiEitursnaka; i++) {
            int x, y;
            x = (int) (Math.random() * 1000);
            y = (int) (Math.random() * 1000);
            int s = (int) (Math.random() * 4) + 1;
            while (x == snakur.getX() || y == snakur.getY()) { //extra
                x = (int) (Math.random() * 1000);
                y = (int) (Math.random() * 1000);
            }
            Eitursnakur eitursnakur = new Eitursnakur(x, y, wh, wh);
            eitursnakur.setFill(Color.RED);
            eitursnakur.setStefna(s);
            e.add(eitursnakur);
            getChildren().add(eitursnakur);
            eDaudur = new ArrayList<>();
            switch ((int) eitursnakur.getRotate()) {
                case 90:
                    eitursnakur.setEiturTail(x, y - i * wh, wh, wh);
                    break;
                case 180:
                    eitursnakur.setEiturTail(x - i * wh, y, wh, wh);
                    break;
                case 270:
                    eitursnakur.setEiturTail(x, y + i * wh, wh, wh);
                    break;
                case 360:
                    eitursnakur.setEiturTail(x + i * wh, y, wh, wh);
                    break;
                default:
                    break;
            }
            getChildren().addAll(eitursnakur.getEiturTail());
        }
    }

    /**
     * Stoppar leik ef klikkað er á skjá.
     *
     * @param timi tekur inn tíma leiks til að stoppa.
     */
    public void snackBreak(Timeline timi) {
        setOnMousePressed(event -> {
            SnakurController.bida(timi);
        });
    }

    /**
     * Setur stefnu snáksins.
     *
     * @param s stefnan.
     */
    public void setStefna(int s) {
        snakur.setRotate(s);
    }

    /**
     * Færir snákinn áfram og breytir hraða hans.
     */
    public void afram() {
        moveCount -= speed;
        if (moveCount > 0) {
            return;
        }
        moveCount = 10 + moveCount;
        lastX = snakurTail.get(snakurTail.size() - 1).getX();
        lastY = snakurTail.get(snakurTail.size() - 1).getY();
        for (int i = snakurTail.size() - 1; i > 0; i--) {
            snakurTail.get(i).setX(snakurTail.get(i - 1).getX());
            snakurTail.get(i).setY(snakurTail.get(i - 1).getY());
        }
        snakurTail.get(0).setX(snakur.getX());
        snakurTail.get(0).setY(snakur.getY());
        snakur.afram();
        getRotate();
    }

    /**
     * Færir eitursnáka áfram.
     */
    public void aframEitursnakar() {
        if (e.size() == 0) {
            return;
        }
        for (Eitursnakur eiturs : e) {
            if (eiturs == null) {
                continue;
            }
            if (eiturs.decCounter()) {
                int x = (int) (Math.random() * 4);
                eiturs.setStefna(x);
            }
            for (int i = eiturs.getEiturTail().size() - 1; i > 0; i--) {
                eiturs.getEiturTail().get(i).setX(eiturs.getEiturTail().get(i - 1).getX());
                eiturs.getEiturTail().get(i).setY(eiturs.getEiturTail().get(i - 1).getY());
            }
            eiturs.getEiturTail().get(0).setX(eiturs.getX());
            eiturs.getEiturTail().get(0).setY(eiturs.getY());
            eiturs.afram();
        }
    }
}
