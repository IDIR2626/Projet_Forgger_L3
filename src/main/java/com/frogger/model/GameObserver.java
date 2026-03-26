package com.frogger.model;

public interface GameObserver {
    /*
     * appelé a chaque mise a jour du jeu
     * (déplacement, collision, score...)
     * dans la vue : ca déclenche le render() du Canavas
     */
    void update();
}
