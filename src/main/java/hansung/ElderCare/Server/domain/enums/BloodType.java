package hansung.ElderCare.Server.domain.enums;

public enum BloodType {
    RH_NEGATIVE_A("RH-A"),
    RH_POSITIVE_A("RH+A"),
    RH_NEGATIVE_B("RH-B"),
    RH_POSITIVE_B("RH+B"),
    RH_NEGATIVE_O("RH-O"),
    RH_POSITIVE_O("RH+O"),
    RH_NEGATIVE_AB("RH-AB"),
    RH_POSITIVE_AB("RH+AB");

    private final String displayName;

    BloodType(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }

    // 문자열을 Enum으로 변환하는 메서드
    public static BloodType fromString(String bloodType) {
        for (BloodType type : BloodType.values()) {
            if (type.getDisplayName().equalsIgnoreCase(bloodType)) {
                return type;
            }
        }
        throw new IllegalArgumentException("Unknown blood type: " + bloodType);
    }
}
