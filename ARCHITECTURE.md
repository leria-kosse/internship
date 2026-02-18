# Architecture

Blocky the Grandmaster is a block-placing game in the lineage of [Tetris the Grandmaster](https://tetris.wiki/Tetris_The_Grand_Master_(series)). This document summarizes the architecture of the game.

## Entry Point

The Blocky game starts in the _____ class. The program:

1. Initializes the game engine and user interface
2. Registers relevant key bindings, and
3. Creates the game loop in a new thread of execution.

The default key bindings for the game are:

+ Move left: _____
+ Move right: _____
+ [Soft Drop](https://tetris.wiki/Drop#Soft_drop): _____
+ [Sonic Drop](https://tetris.wiki/Drop#Instant_drop): _____
+ Rotate clockwise: _____
+ Rotate counterclockwise: _____

Each iteration of the game loop executes one _frame_ of the game and the loop runs at a fixed rate (60 frames per second). In the loop, we call the _____ method of the game engine to advance the game state by one frame. We then trigger a re-rendering of the game to the screen via the `paintImmediately` method.

## Game Engine

The _____ class represents the core game engine. A blocky game consists of two components, _____ and _____, along with auxiliary state. When running a frame, the following methods are called:

1.  _____: spawns a new active piece if one does not exist. Also checks to see if the game is over (if the newly spawned active piece collides with the well).
2.  `processInput()`: _____
3.  _____: enacts gravity on the active piece, pushing it down the well.
4.  _____: _____

## Pieces

The Piece class captures the active piece. A piece is a [tetromino](https://en.wikipedia.org/wiki/Tetromino). The different kinds of pieces can be found in the _____ enum and the layouts of each piece can be found in the ____ directory of the project.

The underlying layout of a piece is represented by a value of type _____ where an entry is marked `true` if the piece occupies that cell. The piece's position determines where this layout appears in the well at any point in time. The layouts for all the possible pieces are loaded via the `Loader` class's _____ static method.

The well is captured by the aptly named `Well` class. The underlying data type backing the well is _____, similarly to a piece's layout. Row/col pair (0, 0) corresponds to _____ in the well.

## Rendering

The _____ class is responsible for rendering the game to the screen. The `paintComponent` method performs the actual drawing by, first, rendering _____ and, then, _____.