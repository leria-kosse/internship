package com.gamewerks.bgm.engine;

/**
 * Key game attributes that govern various timers and delays.
 * 
 * @param are Entry delay (ARE) in frames.
 * @param lineAre Entry delay after line clear (line ARE) in frames.
 * @param das Delayed auto shift (DAS) in frames.
 * @param lockDelay Lock delay in frames.
 * @param lineClearDleay Line clear delay in frames.
 * @param gravity Number of lines to drop per frame in <code>1/Constants.GRAVITY_UNIT</code>
 *                of lines.
 */
public record GameAttributes(int are, int lineAre, int das, int lockDelay,
                             int lineClearDleay, int gravity) { }
