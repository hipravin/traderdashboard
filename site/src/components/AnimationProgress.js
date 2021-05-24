import AnimationProperties from "./AnimationProperties";

class AnimationProgress {
    animationPassedMillis;
    animationLastStartedTime = null;

    static awaitAnimationStart() {
        const ap = new AnimationProgress();
        ap.animationPassedMillis = 0;

        return ap;
    }

    startAnimation() {
        this.animationLastStartedTime = new Date().getTime();
    }

    pause() {
        this.animationFrame();
        this.animationLastStartedTime = null;
    }

    animationFrame() {
        if(this.animationLastStartedTime !== null) {
            const now = new Date().getTime();

            this.animationPassedMillis += now - this.animationLastStartedTime;
            this.animationLastStartedTime = now;
        }
    }

    getAnimationPassedMillis() {
        return this.animationPassedMillis;
    }
}

export default AnimationProgress;