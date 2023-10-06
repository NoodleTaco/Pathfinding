## Ship Design

The ship is an n x n matrix of open and closed tiles. 
The path is randomly generated with the constraint that a closed tile can be opened only if it has 1 open neighbor. 
At the end of the generation, half of the deadends are opened at random. 

## Experiment Design
A bot, a fire, and a button are spawned randomly around the ship. 
At each time step, the bot moves towards the button in accordance to one of its pathfinding algorithims and the fire spreads. 
If the robot reaches the button, the experiment is a success. If the bot is inside of a fire tile, the experiment fails. 
The rate at which the fire is spread can be controlled. 
