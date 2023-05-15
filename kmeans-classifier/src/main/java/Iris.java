import org.jetbrains.annotations.NotNull;

public class Iris {
    public double x1;
    public double x2;
    public double x3;
    public double x4;
    private String label;

    public Iris(double x1, double x2, double x3, double x4, String label) {
        this.x1 = x1;
        this.x2 = x2;
        this.x3 = x3;
        this.x4 = x4;
        this.label = label;
    }

    public Iris(double x1, double x2, double x3, double x4) {
        this.x1 = x1;
        this.x2 = x2;
        this.x3 = x3;
        this.x4 = x4;
    }

    public double distanceTo(@NotNull Iris other) {
        double dx = this.x1 - other.x1;
        double dy = this.x2 - other.x2;
        double dz = this.x3 - other.x3;
        double dw = this.x4 - other.x4;

        return Math.sqrt(dx * dx + dy * dy + dz * dz + dw * dw);
    }

    @Override
    public String toString() {
        return String.format("%.1f, %.1f, %.1f, %.1f", x1, x2, x3, x4);
    }

    public String getLabel() {
        return label;
    }
}