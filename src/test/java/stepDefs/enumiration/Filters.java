package stepDefs.enumiration;

import lombok.Getter;

@Getter
@SuppressWarnings({"NonAsciiCharacters", "unused"})
public enum Filters {

    По_умолчанию(0, "По умолчанию"),
    Дороже(1, "Дороже"),
    Дешевле(2,"Дешевле"),
    По_дате(3,"По дате");

    private final int id;
    private final String name;
    Filters(int id, String name){
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
