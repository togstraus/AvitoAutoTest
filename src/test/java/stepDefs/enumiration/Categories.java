package stepDefs.enumiration;

import lombok.Getter;

@Getter
@SuppressWarnings({"NonAsciiCharacters", "unused"})
public enum Categories {
    оргтехника(0, "Оргтехника и расходники");

    private final int id;
    private final String name;

    Categories(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public int getId() {
        return id;
    }
}
