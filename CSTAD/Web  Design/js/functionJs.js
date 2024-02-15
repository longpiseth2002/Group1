
'use strict';

// Function in  JavaScript
// has 2 types
// 1. Normal function
// 2. Arrow function

// 1. Normal function
 function sayHello() {
        console.log('Hello');
}
sayHello();

let player = {
    name: "Chou",
    level: 15,
    attack: 1000,
    defend: 500,
    hp: 1000,
    takeDamage: function(damage) {
        this.hp -= damage;
        console.log(this.name + ' take ' + damage + ' damage');
    },
    attackEnemy: function(enemy) {
        enemy.takeDamage(this.attack);

        console.log(this.name + ' attack ' + enemy.name + ' with ' + this.attack + ' damage');
    }

}

let enemy = {
    name: "Nana",
    level: 15,
    attack: 1000,
    defend: 500,
    hp: 1500,
    takeDamage: function(damage) {
        this.hp -= damage;
        console.log(this.name + ' take ' + damage + ' damage');
    },
    attackEnemy: function(player) {
        player.takeDamage(this.attack);
        console.log(this.name + ' attack ' + player.name + ' with ' + this.attack + ' damage');
    }

}

player.attackEnemy(enemy);