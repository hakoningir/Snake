package vidmot;


import javafx.scene.shape.Rectangle;

import java.util.ArrayList;

/******************************************************
 *   Nafn    : Hákon Ingi Rafnsson
 *   T-póstur: hir12@hi.is
 *
 *   Lýsing  : Klasi sem að sér um helstu eiginleika snáka og eitursnáka.
 *
 ******************************************************/
public class Eitursnakur extends Rectangle {

    private final int offset = 10;
    private int counter = 10;
    private ArrayList<Rectangle> eiturTail = new ArrayList<>();

    /**
     * fall sem að sér um mest alla hreyfingu á snákunum.
     */
    public void afram(){
        int x = (int)((SnakurBord)getParent()).getWidth();
        int y = (int)((SnakurBord)getParent()).getHeight();
        setX((getX() + x + (int) Math.cos(Math.toRadians(getRotate())) * offset) % x);
        setY((getY() + y - (int) Math.sin(Math.toRadians(getRotate())) * offset) % y);
    }

    /**
     * getter fyrir hala eitursnáka.
     * @return
     */
    public ArrayList<Rectangle> getEiturTail() {
        return eiturTail;
    }

    /**
     * setter fyrir hala eitursnáka.
     * @param x x hnit fyrir búta í hala.
     * @param y y hnit fyrir búta í hala.
     * @param w breidd eins búts í hala.
     * @param l lengd eins búts í hala.
     */
    public void setEiturTail(int x, int y, int w, int l) {
        for (int i = 0; i < 5; i++) {
            eiturTail.add(new Rectangle (x,y,w,l));
        }
    }

    /**
     * setur stefnu.
     * @param s stefnan.
     */
    public void setStefna(int s){
        setRotate(s*90);
    }

    /**
     * getter fyrir counter
     * @return skilar counter.
     */
    public int getCounter() {
        return counter;
    }

    /**
     * counter sem að sér um hreyfingu eitursnáka og finnur nýja tölu á milli 0 og 50 sem er
     * næsta skipti sem að snákur beygjir.
     * @return skilar hvort snákur eigi að beygja eða ekki.
     */
    public boolean decCounter(){
        counter--;
        if (counter <= 0) {
            counter = (int) (Math.random()*50);
            return true;
        }
        return false;
    }

    /*
    super fyrir snák.
     */
    public Eitursnakur (int x, int y, int width, int height ){
        super (x,y,width,height);
    }
}
