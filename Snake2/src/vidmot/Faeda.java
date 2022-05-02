package vidmot;

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

/******************************************************
 *   Nafn    : Hákon Ingi Rafnsson
 *   T-póstur: hir12@hi.is
 *
 *   Lýsing  : Klasi sem að sér um helstu eiginleika fæðu.
 *
 ******************************************************/
public class Faeda extends Circle {

    /**
     * enum sem að segir til hvernig fæðutýpur um er verið að ræða.
     */
    enum type {
        SHOT,
        SPEED
    }

    private type t;

    /**
     * smiður fyrir fæðu.
     * @param x x hnit fæðu.
     * @param y y hnit fæðu.
     * @param radius radíus fæðunnar.
     * @param t týpa af fæðu.
     */
    public Faeda(double x, double y, double radius, type t){
        super(x, y, radius);
        if (t == type.SHOT) {
            setFill(Color.GREEN);
        }
        else setFill(Color.RED);
        this.t = t;
    }

    /**
     * getter fyrir fæðutýpu.
     * @return skilar týpu.
     */
    public type getT() {
        return t;
    }
}
