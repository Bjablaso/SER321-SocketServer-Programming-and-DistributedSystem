package enitityfolder;

public enum PointScale {
    MAXPOINT(100),
    MINPOINT(50),
    NOPOINT(0);

    private final int value;

    PointScale(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}

