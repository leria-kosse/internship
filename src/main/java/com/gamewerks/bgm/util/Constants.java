package com.gamewerks.bgm.util;

/**
 * Global constants that govern a Blocky game.
 */
public class Constants {
    /** The width of the board. */
    public static final int BOARD_WIDTH = 10;
    /** The height of the board. */
    public static final int BOARD_HEIGHT = 22;

    /**
     * The denominator of fractional height unit, describing the (potential
     * fractional) speed of gravity in the well. The speed at which gravity
     * pulls pieces down is defined in units of <code>1 / GRAVITY_UNIT</code>. 
     */
    public static final int GRAVITY_UNIT = 256;

    /**
     * "1G" speed, i.e., the gravity at which pieces fall one row per frame
     * expressed in terms of gravity units.
     */
    public static final int GRAVITY_1G = BOARD_HEIGHT * GRAVITY_UNIT;

    /**
     * "20G" speed, i.e., the gravity at which pieces fall instaneously to the
     * bottom of the well expressed in terms of gravity units. This is also
     * the speed at which we hard drop a piece.
     */
    public static final int GRAVITY_20G = 20 * GRAVITY_1G;

    /**
     * Soft drop gravity, i.e., the gravity used when soft dropping a piece.
     */
    public static final int GRAVITY_SOFT_DROP = GRAVITY_1G / 4;
   
    /** The path to the data directory. */
    public static final String DATA_PATH = "data";
}
