import AnimationProgress from "./AnimationProgress";

class AnimationProperties {
    pixelsPerAnimationSecond;
    frameSizeMs;
    tradeFramesPerAnimationSecond;
    tradeFramesPerTimeLabel;

    constructor({pixelsPerAnimationSecond, frameSizeMs, tradeFramesPerAnimationSecond, tradeFramesPerTimeLabel}) {
        this.pixelsPerAnimationSecond = pixelsPerAnimationSecond;
        this.frameSizeMs = frameSizeMs;
        this.tradeFramesPerAnimationSecond = tradeFramesPerAnimationSecond;
        this.tradeFramesPerTimeLabel = tradeFramesPerTimeLabel;
    }

    //tradeFramesPerTradeTime = (1000 / frameSizeMs)
    //tradeFramePerRealTime =

    static defaultAnimationProperties() {
        return new AnimationProperties({
            frameSizeMs: 6000,
            pixelsPerAnimationSecond: 10,
            tradeFramesPerAnimationSecond: 16,
            tradeFramesPerTimeLabel: 60
        });
    }

    calcTradeSecondsPerAnimationSeconds() {
        return this.tradeFramesPerAnimationSecond * (this.frameSizeMs / 1000)
    }

    shouldShowTradeFrame(tradeStartTime, tradeTime, animationTimePassedMillis) {
        return animationTimePassedMillis * this.calcTradeSecondsPerAnimationSeconds() > (tradeTime.getTime() - tradeStartTime.getTime());
    }

    calcYShiftPx(tradeStartTime, tradeTime, animationTimePassedMillis) {
        const secOnScreen =
            (animationTimePassedMillis - (tradeTime.getTime() - tradeStartTime.getTime()) / this.calcTradeSecondsPerAnimationSeconds()) / 1000;
        return secOnScreen * this.pixelsPerAnimationSecond;
    }

    calcCurrentTradeTime(tradeAggStart, animationProgress) {
        return new Date(tradeAggStart.getTime() + animationProgress.getAnimationPassedMillis() * this.calcTradeSecondsPerAnimationSeconds());
    }

    getTimeLabelDivider() {
        const divider =  this.tradeFramesPerTimeLabel;
        return divider <=1 ? 1 : Math.ceil(divider);
    }

}

export default AnimationProperties;