package core;

/**
 * @file src/core/Timer.java
 * @author cchaine
 *
 * @brief Classe utilitaire de la gestion du temps
 */
public class Timer {

    private double lastLoopTime;

    /**
     * @brief Initialise le timer
     */
    public void init() {
        lastLoopTime = getTime();
    }

    /**
     * @return Récupère le temps en secondes
     */
    public double getTime() {
        return System.nanoTime() / 1000000000.0;
    }

    /**
     * @brief Calcul le temps entre la dernière récupération du temps et le 
     * moment actuel
     * @return La différence de temps
     */
    public float getEllapsedTime() {
        double time = getTime();
        float ellapsedTime = (float) (time - lastLoopTime);
        lastLoopTime = time;
        return ellapsedTime;
    }

    /**
     * @return le temps de la dernière mise à jour
     */
    public double getLastLoopTime() {
        return lastLoopTime;
    }
}