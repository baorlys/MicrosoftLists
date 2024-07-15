package model.constants;

public enum NumberSymbol {
    NONE("_"),
    DOLLAR("$"),
    EURO("€");

    private final String symbol;

    NumberSymbol(String symbol) {
        this.symbol = symbol;
    }


    public String getSymbol() {
        return symbol;
    }
}
