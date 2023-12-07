public class Cuadrado {

    public enum border {
        PARED, PUERTA, VACIO
    }

    private border top;
    private border right;
    private border bottom;
    private border left;
    private final int pivotX;
    private final int pivotY;

    public Cuadrado(int pivotX, int pivotY) {
        this.pivotX = pivotX;
        this.pivotY = pivotY;
        setTop(border.VACIO);
        setRight(border.VACIO);
        setBottom(border.VACIO);
        setLeft(border.VACIO);
    }

    public int getPivotX() {
        return pivotX;
    }

    public  int getPivotY() {
        return pivotY;
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

    public boolean isInRange(double x, double y) {
        return x >= pivotX && x < pivotX + 1 && y >= pivotY && y < pivotY + 1;
    }

}