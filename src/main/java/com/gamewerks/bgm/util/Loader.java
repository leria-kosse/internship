package com.gamewerks.bgm.util;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Scanner;

import com.gamewerks.bgm.engine.PieceKind;

/**
 * The loader provides static methods for reading in the rotation data for each piece.
 */
public class Loader {
    /**
     * @param in a <code>Scanner</code> attached to a data file
     * @return the rotational data parsed from one data file
     */
    private static boolean[][] readRotation(Scanner in) {
        boolean[][] rotation = new boolean[4][4];
        for (int row = 3; row >= 0; row--) {
            String line = in.nextLine();
            for (int col = 0; col < 4; col++) {
                rotation[row][col] = line.charAt(col) == 'x';
            }
        }
        return rotation;
    }
    
   
    /**
     * @param piece the kind of piece
     * @return the rotational data for the given piece-kind
     * @throws IOException
     */
    public static boolean[][][] loadRotationData(PieceKind piece) throws IOException {
        boolean[][][] data = new boolean[4][][];
        File file = new File(Constants.DATA_PATH, piece.toString() + ".data");
        Scanner in = new Scanner(file);
        for (int i = 0; i < 4; i++) {
            data[i] = readRotation(in);
            if (in.hasNextLine()) {
                in.nextLine();
            }
        }
        in.close();
        return data;
    }

    /**
     * @return the rotation data for all pieces
     * @throws IOException
     */
    public static HashMap<PieceKind, boolean[][][]> loadAllRotationData() throws IOException {
        HashMap<PieceKind, boolean[][][]> ret = new HashMap<>();
        for (int i = 0; i < PieceKind.ALL.length; i++) {
            PieceKind piece = PieceKind.ALL[i];
            ret.put(piece, loadRotationData(piece));
        }
        return ret;
    }
}
