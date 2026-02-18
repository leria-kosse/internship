package com.gamewerks.bgm.engine;

import java.awt.Color;
import java.io.IOException;
import java.util.HashMap;

import com.gamewerks.bgm.util.Loader;
import com.gamewerks.bgm.util.Position;

/** A piece is a (non-locked) tetromino that the user controls. */
public class Piece {
   
    /**
     * A mapping from each possible piece to its rotation data. The rotation
     * data is stored as an array of grids. The grids are a 2d array of
     * booleans indicating each cell that the piece occupies.
     */
    private static HashMap<PieceKind, boolean[][][]> rotationData = null;
    
    static {
        try {
            rotationData = Loader.loadAllRotationData();
        } catch (IOException ex) {
            System.out.println("Exception occurred loading rotation data");
            System.exit(-1);
        }
    }
    
    private PieceKind kind;
    private int orientation;
    private Position pos;
   
    /**
     * Constructs a new piece from the given parameters.
     * @param kind the kind of the piece
     * @param pos the initial position of the piece
     */
    public Piece(PieceKind kind, Position pos) {
        this.kind = kind;
        orientation = 0;
        this.pos = pos;
    }

    /**
     * @return the position of this piece
     */
    public Position getPosition() {
        return pos;
    }
   
    /**
     * Moves this piece to the given position
     * @param p the position to move this piece to
     */
    public void moveTo(Position p) {
        pos = p;
    }
   
    /**
     * @return the current grid-layout of this piece.
     */
    public boolean[][] getLayout() {
        return rotationData.get(kind)[orientation];
    }
   
    /**
     * Rotates this piece in the given direction
     * @param clockwise true if we should rotate clockwise, else counterclockwise
     */
    public void rotate(boolean clockwise) {
        if (clockwise) {
            orientation = (orientation + 1) % 4;
        } else {
            int k = orientation - 1;
            orientation = k < 0 ? 3 : k;
        }
    }

    /**
     * @return the color of this piece
     */
    public Color getColor() {
        return kind.getColor();
    }
}
