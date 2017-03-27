import java.io.Serializable;

public class TempValue implements Serializable {
    private double value;
    
    public TempValue(double v) {
        value = v;
    }
    
    public double getValue() {
        return value;
    }
}