package com.gamewerks.bgm.engine;

import java.awt.Color;

/** The different kinds of tetronimos in Blocky. */
public enum PieceKind {
    I,
    J,
    L,
    O,
    S,
    T,
    Z;
  
    /** All possible pieces. */
    public static final PieceKind[] ALL = {
        I, J, L, O, S, T, Z
    };

    /** @return the color associated with this piece */
    public Color getColor() {
        switch (this) {
            case I: return Color.RED;
            case J: return Color.BLUE;
            case L: return Color.ORANGE;
            case O: return Color.YELLOW;
            case S: return Color.PINK;
            case T: return Color.CYAN;
            case Z: return Color.GREEN;
            default:
                // N.B., cannot happen!
                throw new IllegalStateException();
        }
    }
}
