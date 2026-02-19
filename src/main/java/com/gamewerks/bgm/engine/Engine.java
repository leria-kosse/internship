package com.gamewerks.bgm.engine;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import com.gamewerks.bgm.util.Constants;
import com.gamewerks.bgm.util.Position;

/**
 * The core engine of the Blocky game, responsible for managing and updating
 * the game state. A blocky game consists of the well, the currently active
 * piece, and all the internal state necessary to manage their interactions.
 */
public class Engine {
    private Well board;
    private Piece activePiece;
    private GameAttributes attrs;
    private InputState input;
    private PieceKind[] randy = PieceKind.ALL;

    private int lockCounter;
    private int entryCounter;
    private int randyIndex = PieceKind.ALL.length;
    private boolean lineWasCleared;
    private boolean isSoftDropping;
    private boolean isHardDropping;

    /** Constructs a new Blocky game engine with default attributes. */
    public Engine() {
        board = new Well();
        lockCounter = 0;
        entryCounter = 0;
        lineWasCleared = false;
        isSoftDropping = false;
        isHardDropping = false;
        attrs = new GameAttributes(48, 25, 14, 30, 40, 24);
        input = new InputState();
        trySpawnBlock();
    }

    /**
     * Tries to spawn a block at the center-top of the well. Only does so if
     * there is no currently active piece. The game ends if spawning the block
     * immediately causes a collision.
     */
    private void trySpawnBlock() {
        if (activePiece == null) {
            entryCounter += 1;
            int delay;
            if (lineWasCleared) {
                // delay time set to lineClearDelay if a line was cleared
                delay = attrs.lineClearDleay(); 
            } else {
                // delay time set to ARE if a line was not cleared(noraml delay)
                delay = attrs.are(); 
            }
            if (entryCounter >= delay) {
                activePiece = new Piece(nextPiece(), 
                        new Position(Constants.BOARD_HEIGHT - 1, Constants.BOARD_WIDTH / 2 - 2));
                entryCounter = 0;
                lineWasCleared = false;
                if (board.collides(activePiece)) {
                    System.exit(0);
                }
            }
        }
    }

    /**
     * Try to move the active piece to the new position, resetting if the
     * movement would cause a collision.
     * 
     * @param newPos the candidate position
     */
    private void tryMovePiece(Position newPos) {
        if (!board.collides(activePiece.getLayout(), newPos)) {
            activePiece.moveTo(newPos);
        }
    }

    /**
     * Tries to rotate the piece in the given direction, resetting if the
     * movement would cause a collision.
     * 
     * @param clockwise true if we wish to rotate clockwise, else rotate
     *                  counterclockwise
     */
    private void tryRotatePiece(boolean clockwise) {
        if (activePiece != null) {
            activePiece.rotate(clockwise);
            if (board.collides(activePiece)) {
                activePiece.rotate(!clockwise);
            }
        }
    }

    /** Processes the input detected on this frame. */
    private void processInput() {
        if (activePiece != null) {
            // 1. Process shifting
            if (input.isJustPressed(KeyKind.MOVE_LEFT)
                    || input.getFramesHeld(KeyKind.MOVE_LEFT) > attrs.das()) {
                tryMovePiece(activePiece.getPosition().add(0, -1));
            } else if (input.isJustPressed(KeyKind.MOVE_RIGHT)
                    || input.getFramesHeld(KeyKind.MOVE_RIGHT) > attrs.das()) {
                tryMovePiece(activePiece.getPosition().add(0, 1));
            }

            // 2. Process rotations
            if (input.isJustPressed(KeyKind.ROTATE_CLOCKWISE)) {
                tryRotatePiece(true);
            } else if (input.isJustPressed(KeyKind.ROTATE_COUNTERCLOCKWISE)) {
                tryRotatePiece(false);
            }
        }

        // 3. Process soft drop
        if (input.isHeld(KeyKind.SOFT_DROP)) {
            isSoftDropping = true;
        } else {
            isSoftDropping = false;
        }

        // 6. Process hard drop
        if (input.isJustPressed(KeyKind.SONIC_DROP)) {
            isHardDropping = true;
        }

        // 5. Updates counters for all held button presses
        input.step();
    }

    /** Processes gravity, applying downward movement to the active piece. */
    private void processGravity() {
        if (activePiece == null) {
            return;
        }
        Position candidate = activePiece.getPosition();
        int gravDelta = attrs.gravity();
        if (isHardDropping) {
            gravDelta = Constants.GRAVITY_20G;
            isHardDropping = false;
        } else if (isSoftDropping) {
            gravDelta = Constants.GRAVITY_SOFT_DROP;
        }
        List<Position> path = candidate.getPathFromGravity(gravDelta);

        // Check whether a collision occurs on each position on the path
        for (Position next : path) {
            if (board.collides(activePiece.getLayout(), next)) {
                if (candidate.equals(activePiece.getPosition())) {
                    if (isSoftDropping || lockCounter >= attrs.lockDelay()) {
                        board.addToWell(activePiece);
                        lockCounter = 0;
                        activePiece = null;
                    } else {
                        lockCounter += 1;
                    }
                    return;
                } else {
                    // The candidate is where the piece moves AND it is
                    // different from the current position. Don't continue
                    // searching through the path!
                    break;
                }
            } else {
                candidate = next;
            }
        }
        // If we get this far, then we have a new candidate distinct from the
        // original position. Move the piece there and reset the lock counter.
        lockCounter = 0;
        activePiece.moveTo(candidate);
    }

    /**
     * Returns the next piece from the array. If the list is empty it reshuffles and
     * resets index before drawing.
     * 
     * @return the next piece
     */

    private PieceKind nextPiece() {
        if (randyIndex == randy.length) {
            shuffle(randy);
            randyIndex = 0;
        }
        return randy[randyIndex++];

    }

    /**
     * Shuffles the given array of pieces in place using the Fisher-Yates algorithm
     * 
     * @param arr the array to shuffle
     */

    private void shuffle(PieceKind[] arr) {
        for (int i = arr.length - 1; i > 0; i--) {
            int randy2 = ThreadLocalRandom.current().nextInt(i + 1);
            PieceKind tempy = arr[i];
            arr[i] = arr[randy2];
            arr[randy2] = tempy;

        }

    }

    /**
     * Compute and process all cleared lines from the well
     * 
     * @return true iff at least one line is cleared
     */
    private boolean processClearedLines() {
        List<Integer> completedRows = board.getCompletedRows();
        board.deleteRows(completedRows);
        return completedRows.size() > 0;
    }

    /** Steps the game engine one frame forward. */
    public void step() {
        trySpawnBlock();
        processInput();
        processGravity();
        lineWasCleared = processClearedLines();
    }

    /** @return the well associated to this board. */
    public boolean[][] getWell() {
        return board.getGrid();
    }

    /** @return the currently active piece. */
    public Piece getActivePiece() {
        return activePiece;
    }

    /**
     * Notifies the game that the given key has been pressed.
     * 
     * @param key the pressed key
     */
    public void keyDown(KeyKind key) {
        input.keyDown(key);
    }

    /**
     * Notifies the game that the given key has been released.
     * 
     * @param key the released key
     */
    public void keyUp(KeyKind key) {
        input.keyUp(key);
    }
}
