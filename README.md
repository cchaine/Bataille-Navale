# Bataille-Navale
This is the project I made in Java for an exam in Computer Science.

<img width="800" alt="menustate" src="https://cloud.githubusercontent.com/assets/18381262/15688364/001c5afa-277a-11e6-91dc-095424b4fa31.png">

There are two modes :
- Split-screen multiplayer
- Single player against a computer

## Core
The game is based upon an object called the core in which we can find the main gameloop.
This loop is run at the same speed whatever the speed of your computer using a loop regulation.
This loops then tells the state manager to whether update or render the current state.

## State manager
As seen above, the state manager knows the current state and according to the core, updates or renders that state.
The goal of this object is to make this program modular, meaning you don't need to modify the core when adding a new state.
States extends the superclass state so they can be stored in the state manager regardless of their type.

<img width="800" alt="core" src="https://cloud.githubusercontent.com/assets/18381262/15688289/9bfa85b0-2779-11e6-906d-24253e737d8d.png">

## Artificial intelligence
Essentially, the artificial intelligence is composed of two states :
- The computer is following a boat, meaning it's going to try each direction in order to destroy the boat
- The computer is not following and is going to choose randomly where to shoot
Furthermore, when choosing randomly, the computer analyse squares around the one he chose. The more squares around are already shot or are walls, the less chance the computer will shoot there.
Doing so, the computer has a more "human" behaviour.
