import React from 'react';
import './App.css';
import 'bootstrap/dist/css/bootstrap.css';
import PriceAxis from "./components/PriceAxis/PriceAxis";
import TradeService from "./service/TradeService";
import ScreenUtils from "./components/ScreenUtils";
import TradeFrame from "./components/trades/TradeFrame";
import notify from "./lib/notify";
import Notifier from "./components/Notifier";
import ConfigPanel from "./components/ConfigPanel/ConfigPanel";
import AnimationProperties from "./components/AnimationProperties";
import AnimationProgress from "./components/AnimationProgress";
import TimeLabel from "./components/TimeAxis/TimeLabel";

class App extends React.Component {

    animationTickMillis = 0;
    animationInterval;
    animationProperties = AnimationProperties.defaultAnimationProperties();
    animationProgress = AnimationProgress.awaitAnimationStart();
    marginFromCenterY = 10;

    constructor(props) {
        super(props);
        this.state = {
        };
    }

    handleConfigUpdate({emcode, daystring, framesize}) {
        notify("Loading started...");
        const tradeService = new TradeService();
        tradeService.loadTradeAggregates(emcode, daystring, framesize)
            .then(res => {
                notify("Loading completed");
                this.animationProgress = AnimationProgress.awaitAnimationStart();
                this.animationProperties.frameSizeMs = parseInt(framesize, 10);
                this.setState({tradeAgg: res})
            })
            .then(res => {
                this.initiallyStartAnimation();
            })
            .catch(e => {
                notify("Loading failed: " + e.toString());
                console.log("Failed to load data: " + e);
            });
    }

    componentDidMount() {
    }

    initiallyStartAnimation() {
        console.log("Animation started");
        this.animationProgress.startAnimation();

        window.requestAnimationFrame(() => {
            this.animationTick();
        });
    }

    animationTick() {
        console.log(`Animation tick ${this.animationProgress.getAnimationPassedMillis()} ms`);
        this.animationProgress.animationFrame();

        if (this.isAfterAppearence(this.state.tradeAgg.end)) {
            console.log(`Animation completed after ${this.animationProgress.getAnimationPassedMillis()} ms`);
        } else if (this.state.tradeAgg.start && this.state.tradeAgg.end) {
            const tradeAgg = this.state.tradeAgg;
            this.setState({tradeAgg: tradeAgg});

            window.requestAnimationFrame(() => {
                this.animationTick();
            });
        }


    }

    render() {
        return (this.state.tradeAgg) ? this.renderTradeAgg() : this.renderEmpty();
    }

    renderEmpty() {
        return (
            <div className="maindiv">
                <Notifier/>
                <ConfigPanel onConfigUpdate={this.handleConfigUpdate.bind(this)}/>

                <div>
                    <h1>Waiting for data...</h1>
                </div>
            </div>
        );

    }

    yshiftForTradeFrame(tradeTime) {
        return this.animationProperties.calcYShiftPx(this.state.tradeAgg.start, tradeTime,
            this.animationProgress.getAnimationPassedMillis()) + this.marginFromCenterY
    }

    isAfterAppearence(tradeTime) {
        return this.animationProperties.shouldShowTradeFrame(this.state.tradeAgg.start, tradeTime,
            this.animationProgress.getAnimationPassedMillis());
    }

    renderTradeAgg() {
        const screenProps = ScreenUtils.defineScreenProps();
        const visibleTradeFrames = this.state.tradeAgg.tradeFrames.filter(tf => {
            return this.isAfterAppearence(tf.tradetimeStart) && this.yshiftForTradeFrame(tf.tradetimeStart) <= screenProps.height * 0.4;
        });

        const tfComponents = visibleTradeFrames.map(tf => {
            return <TradeFrame key={tf.tradetimeStart} screenProps={screenProps} tradeFrame={tf}
                               priceGrid={this.state.tradeAgg.priceGrid}
                               yshift={this.yshiftForTradeFrame(tf.tradetimeStart)}
            />
        });

        const timeLabels = [];
        this.state.tradeAgg.tradeFrames.forEach((tf, idx, tfs) => {
            if(idx % (this.animationProperties.getTimeLabelDivider()) === 0
                && this.isAfterAppearence(tf.tradetimeStart) && this.yshiftForTradeFrame(tf.tradetimeStart) <= screenProps.height * 0.4) {
                const timelabelString = (tf.tradetimeStart.toLocaleTimeString()).slice(0, 5);
                timeLabels.push(
                    <TimeLabel key={timelabelString} timestring={timelabelString} yshift={this.yshiftForTradeFrame(tf.tradetimeStart)}
                               screenProps={screenProps}
                    />
                );
            }
        });

        return (
            <div className="maindiv">
                <Notifier/>
                <ConfigPanel onConfigUpdate={this.handleConfigUpdate.bind(this)}/>
                <div>
                    <svg className="mainsvg" width={screenProps.width} height={screenProps.height} version="1.1"
                         xmlns="http://www.w3.org/2000/svg">

                        <PriceAxis screenProps={screenProps} priceGrid={this.state.tradeAgg.priceGrid}/>

                        {tfComponents}
                        {timeLabels}
                    </svg>
                </div>
            </div>
        )
    }
}


export default App;
