
import cyen122.Menu;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MenuButton {

    private int x, y, w, h;
    private Class c;
    private String arg;

    public MenuButton(int x, int y, int w, int h, Class c, String arg) {
        this.x = x;
        this.y = y;
        this.w = w;
        this.h = h;
        this.c = c;
        this.arg = arg;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getWidth() {
        return w;
    }

    public int getHeight() {
        return h;
    }

    protected Object isClicked() {
        try {
            return c.getConstructor(c).newInstance(arg);
        } catch (Exception ex) {
            Logger.getLogger(Menu.class.getName()).log(Level.SEVERE, null, ex);
            return "Fail";
        }
    }
}
