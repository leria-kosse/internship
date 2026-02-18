package com.gamewerks.bgm.engine;

import java.util.HashMap;
import java.util.Map;

/**
 * Records the state of the various inputs to the game. <code>InputState</code>
 * records whether a key has been pressed and, if so, how long it has been held.
 */
public class InputState {

    /**
     * The state of a particular key, whether it is pressed and if so, how long.
     */
    private static class KeyState {
        /** True iff the key is currently pressed. */
        public boolean isDown = false;
        /**
         * The number of frames the key has been held or -1 if it is not being
         * pressed.
         */
        public int heldFrames = -1;
    }

    private Map<KeyKind, KeyState> state;

    /** Constructs a new state tracking all possible keys. */
    public InputState() {
        state = new HashMap<>();
        for (KeyKind key : KeyKind.ALL) {
            state.put(key, new KeyState());
        }
    }

    /**
     * Records that the given key has been pressed.
     * @param key the pressed key
     */
    public void keyDown(KeyKind key) {
        KeyState st = state.get(key);
        // N.B., on mac, keyboard repeat will fire, so only register "down" if
        // the key was not already down to begin with!
        if (!st.isDown) {
            st.isDown = true;
            st.heldFrames = 0;
        }
    }

    /**
     * Records that the given key has been released.
     * @param key the released key
     */
    public void keyUp(KeyKind key) {
        KeyState st = state.get(key);
        st.isDown = false;
        st.heldFrames = -1;
    }
   
    /**
     * @param key the key to be tested
     * @return true iff the given key is being held down
     */
    public boolean isHeld(KeyKind key) {
        return state.get(key).isDown;
    }

    /**
     * @param key the key to be tested
     * @return true iff the given key has just been pressed
     */
    public boolean isJustPressed(KeyKind key) {
        return state.get(key).isDown && state.get(key).heldFrames == 0;
    }

    /** 
     * @param key the key to be tested
     * @return the number of frames that this key has been held.
     */
    public int getFramesHeld(KeyKind key) {
        return state.get(key).heldFrames;
    }

    /** Steps this state one frame forward in time */
    public void step() {
        for (KeyKind k : state.keySet()) {
            KeyState st = state.get(k);
            if (st.isDown) {
                st.heldFrames += 1;
            } else {
                st.heldFrames = 0;
            }
        }
    }
}