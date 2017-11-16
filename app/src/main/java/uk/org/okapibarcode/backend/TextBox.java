package uk.org.okapibarcode.backend;

/**
 * A simple text item class.
 *
 * @author <a href="mailto:rstuart114@gmail.com">Robin Stuart</a>
 * @author Daniel Gredler
 */
public class TextBox {

    /** X position that the text should be centered on horizontally. */
    public final double x;

    /** Y position of the text baseline. */
    public final double y;

    /** Text value. */
    public final String text;

    /**
     * Creates a new instance.
     *
     * @param x the X position that the text should be centered on horizontally
     * @param y the Y position of the text baseline
     * @param text the text value
     */
    public TextBox(double x, double y, String text) {
        this.x = x;
        this.y = y;
        this.text = text;
    }

    /** {@inheritDoc} */
    @Override
    public String toString() {
        return "TextBox[x=" + x + ", y=" + y + ", text=" + text + "]";
    }
}
