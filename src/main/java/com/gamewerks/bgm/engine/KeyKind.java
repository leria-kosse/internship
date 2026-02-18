package com.gamewerks.bgm.engine;

/** The kinds of keys in Blocky. */
public enum KeyKind {
    MOVE_LEFT,
    MOVE_RIGHT,
    SONIC_DROP,
    SOFT_DROP,
    ROTATE_COUNTERCLOCKWISE,
    ROTATE_CLOCKWISE;

    /** A list of all possible keys in Blocky. */
    public static final KeyKind[] ALL = {
        MOVE_LEFT,
        MOVE_RIGHT,
        SONIC_DROP,
        SOFT_DROP,
        ROTATE_COUNTERCLOCKWISE,
        ROTATE_CLOCKWISE
    };
}