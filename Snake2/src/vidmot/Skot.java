package vidmot;

import javafx.scene.shape.Ellipse;

/******************************************************
 *   Nafn    : Hákon Ingi Rafnsson
 *   T-póstur: hir12@hi.is
 *
 *   Lýsing  : Klasi sem að sér um helstu eiginleika skotanna.
 *
 ******************************************************/
public class Skot extends Ellipse {

    private final int offset = 10; //nota offset til að láta skot fara hraðar en snákur
    private boolean active = false;

    /**
     * getter fyrir hvort hafi verið skotið eða ekki.
     *
     * @return skilar stöðu skotsins.
     */
    public boolean getActive() {
        return active;
    }

    /**
     * fall sem að setur skot sem active þegar er skotið.
     */
    public void skjota() {
        active = true; //þegar ýtt er á space
    }

    /**
     * áfram fall fyrir skotin. Sér um að setja þau í -500 eftir að þau fari af mappi svo þau sjáist ekki lengur.
     * ég er svo sniðugur:)
     */
    public void afram() {
        if (active) {
            int x = (int) ((SnakurBord) getParent()).getWidth();
            int y = (int) ((SnakurBord) getParent()).getHeight();
            int newX = (int) (getCenterX() + Math.cos(Math.toRadians(getRotate())) * offset);
            int newY = (int) (getCenterY() - Math.sin(Math.toRadians(getRotate())) * offset);
            if (newX < 0 || newX > x) {
                active = false;
                setCenterX(-5000);
                return;
            }
            if (newY < 0 || newY > y) {
                active = false;
                setCenterY(-5000);
                return;
            }
            this.setCenterX(newX);
            this.setCenterY(newY);
        }
    }

    public Skot(double x, double y, int width, int height) {
        super(x, y, width, height);
    }
}
