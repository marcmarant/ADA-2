public class Cuadrado {

    public enum border {
        PARED, PUERTA, VACIO
    }

    private border top;
    private border right;
    private border bottom;
    private border left;

    public Cuadrado() {
        setTop(border.VACIO);
        setRight(border.VACIO);
        setBottom(border.VACIO);
        setLeft(border.VACIO);
    }

    public border getTop() {
        return top;
    }

    public border getRight() {
        return right;
    }

    public border getBottom() {
        return bottom;
    }

    public border getLeft() {
        return left;
    }

    public void setTop(border newTop) {
        top = newTop;
    }

    public void setRight(border newRight) {
        right = newRight;
    }

    public void setBottom(border newBottom) {
        bottom = newBottom;
    }

    public void setLeft(border newLeft) {
        left = newLeft;
    }

}