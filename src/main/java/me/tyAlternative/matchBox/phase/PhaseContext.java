package me.tyalternative.matchbox.phase;

/**
 * Contexte partagé entre toutes les phases
 */
public class PhaseContext {

    private final int roundNumber;
    private long phaseStartTime;
    private long phaseDuration;

    public PhaseContext(int roundNumber) {
        this.roundNumber = roundNumber;
    }

    public int getRoundNumber() {
        return roundNumber;
    }

    public long getPhaseStartTime() {
        return phaseStartTime;
    }

    public void setPhaseStartTime(long phaseStartTime) {
        this.phaseStartTime = phaseStartTime;
    }

    public long getPhaseDuration() {
        return phaseDuration;
    }

    public void setPhaseDuration(long phaseDuration) {
        this.phaseDuration = phaseDuration;
    }

    public long getRemainingTime() {
        long elapsed = System.currentTimeMillis() - phaseStartTime;
        return Math.max(0, phaseDuration - elapsed);
    }

    public int getRemainingSeconds() {
        return (int) Math.ceil(getRemainingTime() / 1000.0);
    }

}
