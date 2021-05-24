import AnimationProgress from "./AnimationProgress";

class AnimationProperties {
    pixelsPerTradeFrame;
    frameSizeMs;
    tradeFramesPerAnimationSecond;

    tradeSecondsPerAnimationSeconds;

    constructor({pixelsPerTradeFrame, frameSizeMs, tradeFramesPerAnimationSecond}) {
        this.pixelsPerTradeFrame = pixelsPerTradeFrame;
        this.frameSizeMs = frameSizeMs;
        this.tradeFramesPerAnimationSecond = tradeFramesPerAnimationSecond;

        this.tradeSecondsPerAnimationSeconds = this.calcTradeSecondsPerAnimationSeconds();
    }

    //tradeFramesPerTradeTime = (1000 / frameSizeMs)
    //tradeFramePerRealTime =

    static defaultAnimationProperties() {
        return new AnimationProperties({
            frameSizeMs: 6000,
            pixelsPerTradeFrame: 1,
            tradeFramesPerAnimationSecond: 16
        });
    }

    calcTradeSecondsPerAnimationSeconds() {
        return this.tradeFramesPerAnimationSecond * (this.frameSizeMs / 1000)
    }

    shouldShowTradeFrame(tradeStartTime, tradeTime, animationTimePassedMillis) {
        return animationTimePassedMillis * this.tradeSecondsPerAnimationSeconds > (tradeTime.getTime() - tradeStartTime.getTime());
    }

    calcYShiftPx(tradeStartTime, tradeTime, animationTimePassedMillis) {
        const framesPassed =
            (animationTimePassedMillis * this.tradeSecondsPerAnimationSeconds - (tradeTime.getTime() - tradeStartTime.getTime())) / this.frameSizeMs;
        return framesPassed * this.pixelsPerTradeFrame;
    }

    calcCurrentTradeTime(tradeAggStart, animationProgress) {
        return new Date(tradeAggStart.getTime() + animationProgress.getAnimationPassedMillis() * this.tradeSecondsPerAnimationSeconds);
    }

}

export default AnimationProperties;