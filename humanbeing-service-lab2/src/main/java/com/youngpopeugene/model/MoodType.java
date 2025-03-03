package com.youngpopeugene.model;

public enum MoodType {
    GREAT,
    OK,
    DEPRESSIVE;

    public static boolean isMoodType(String value) {
        for (MoodType mood : MoodType.values()) {
            if (mood.name().equalsIgnoreCase(value)) {
                return true;
            }
        }
        return false;
    }

    public static MoodType fromString(String value) {
        for (MoodType mood : MoodType.values()) {
            if (mood.name().equalsIgnoreCase(value)) {
                return mood;
            }
        }
        return null;
    }
}

