package humanbeing.ejb.model;

public enum WeaponType {
    AXE,
    PISTOL,
    KNIFE,
    MACHINE_GUN;

    public static boolean isWeaponType(String value) {
        for (WeaponType weapon : WeaponType.values()) {
            if (weapon.name().equalsIgnoreCase(value)) {
                return true;
            }
        }
        return false;
    }

    public static WeaponType fromString(String value) {
        for (WeaponType weapon : WeaponType.values()) {
            if (weapon.name().equalsIgnoreCase(value)) {
                return weapon;
            }
        }
        return null;
    }
}
