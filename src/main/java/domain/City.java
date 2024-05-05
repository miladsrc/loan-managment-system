package domain;

public enum City {
    TEHRAN("Tehran"),
    MASHHAD("Mashhad"),
    ISFAHAN("Isfahan"),
    KARAJ("Karaj"),
    TABRIZ("Tabriz"),
    SHIRAZ("Shiraz"),
    QOM("Qom"),
    AHVAZ("Ahvaz"),
    KERMAN("Kerman"),
    URUMIA("Urumia"),
    ZAHEDAN("Zahedan"),
    RASHT("Rasht"),
    HAMADAN("Hamadan"),
    KERMANSHAH("Kermanshah"),
    YAZD("Yazd"),
    ARDABIL("Ardabil"),
    BANDAR_ABBAS("Bandar Abbas"),
    ZANJAN("Zanjan"),
    SANANDAJ("Sanandaj"),
    QAZVIN("Qazvin"),
    KISH("Kish"),
    GORGAN("Gorgan"),
    SHAHR_E_KORD("Shahr-e-Kord"),
    ILAM("Ilam"),
    BOJNURD("Bojnurd"),
    BUSHEHR("Bushehr"),
    BIRJAND("Birjand"),
    SEMNAN("Semnan"),
    YASUJ("Yasuj"),
    SARI("Sari");

    private final String cityName;

    City(String cityName) {
        this.cityName = cityName;
    }

    public String getCityName() {
        return this.cityName;
    }
}
