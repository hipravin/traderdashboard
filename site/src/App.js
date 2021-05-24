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

class App extends React.Component {

    timeMultiplier = 100; // animate x time faster than real trades
    animationTickMillis = 0;
    animationInterval;
    animationProperties = AnimationProperties.defaultAnimationProperties();
    animationProgress = AnimationProgress.awaitAnimationStart();
    marginFromCenterY = 10;

    constructor(props) {
        super(props);
        this.state = {
            // tradeAgg: {
            //     priceGrid: {
            //         minPrice: 0,
            //         maxPrice: 1000,
            //         priceGrid: [0, 100, 200, 300, 400, 500, 600, 700, 800, 900, 1000],
            //
            //     },
            //     tradeFrames: []
            // },
        };
    }

    handleConfigUpdate({emcode, daystring, framesize}) {
        notify("Loading started...");
        const tradeService = new TradeService();
        tradeService.loadTradeAggregates(emcode, daystring, framesize)
            .then(res => {
                notify("Loading comleted");
                this.animationProgress = AnimationProgress.awaitAnimationStart();
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
        this.animationInterval = setInterval(() => {
            this.animationTick();
        }, 50);
    }

    // startAnimation() {
    //     console.log("Animation started");
    //     this.animationStartedTime = new Date().getTime();
    //     this.animationInterval = setInterval(() => {
    //         this.animationTick();
    //     }, 50);
    // }

    animationTick() {
        console.log(`Animation tick ${this.animationProgress.getAnimationPassedMillis()} ms`);
        this.animationProgress.animationFrame();

        if (this.isAfterAppearence(this.state.tradeAgg.end)) {
            console.log(`Animation completed after ${this.animationProgress.getAnimationPassedMillis()} ms`);
            clearInterval(this.animationInterval);
        } else if (this.state.tradeAgg.start && this.state.tradeAgg.end) {
            const tradeAgg = this.state.tradeAgg;
            this.setState({tradeAgg: tradeAgg});
        }
    }

    // animationTick() {
    //     const rtimePassed = new Date().getTime() - this.animationStartedTime;
    //     const xtimePassed = rtimePassed * this.timeMultiplier;
    //     const timeonscreen = 30000 * this.timeMultiplier;
    //     this.xtime = new Date(this.state.tradeAgg.start.getTime() + xtimePassed);
    //
    //     console.log("Animation tick " + xtimePassed);
    //
    //     if (this.state.tradeAgg.start && this.state.tradeAgg.end
    //         && this.state.tradeAgg.start.getTime() + xtimePassed > this.state.tradeAgg.end.getTime()) {
    //         console.log("Animation finished after " + rtimePassed + " ms");
    //         clearInterval(this.animationInterval);
    //     } else if (this.state.tradeAgg.start && this.state.tradeAgg.end) {
    //         this.state.tradeAgg.tradeFrames.filter(tf => {
    //             return tf.tradetimeStart.getTime() <= this.state.tradeAgg.start.getTime() + xtimePassed
    //                 && tf.tradetimeEnd.getTime() >= this.state.tradeAgg.start.getTime() + xtimePassed - timeonscreen
    //         }).forEach(tf => {
    //             tf.visible = true;
    //             tf.xtimeonscreen = (this.state.tradeAgg.start.getTime() + xtimePassed) - tf.tradetimeStart.getTime();
    //         });
    //
    //         this.state.tradeAgg.tradeFrames.filter(tf => {
    //             return tf.tradetimeEnd.getTime() < this.state.tradeAgg.start.getTime() + xtimePassed - timeonscreen
    //         }).forEach(tf => {
    //             tf.visible = false;
    //             tf.xtimeonscreen = (this.state.tradeAgg.start.getTime() + xtimePassed) - tf.tradetimeStart.getTime();
    //         });
    //
    //         const tradeAgg = this.state.tradeAgg;
    //         this.setState({tradeAgg: tradeAgg});
    //     }
    // }

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

        const currentTradeTime = this.animationProperties.calcCurrentTradeTime(this.state.tradeAgg.start, this.animationProgress);

        const tfComponents = visibleTradeFrames.map(tf => {
            return <TradeFrame key={tf.tradetimeStart} screenProps={screenProps} tradeFrame={tf}
                               priceGrid={this.state.tradeAgg.priceGrid}
                               yshift={this.yshiftForTradeFrame(tf.tradetimeStart)}
            />
        });

        return (
            <div className="maindiv">
                <Notifier/>
                <ConfigPanel onConfigUpdate={this.handleConfigUpdate.bind(this)}/>
                <div>
                    <svg className="mainsvg" width={screenProps.width} height={screenProps.height} version="1.1"
                         xmlns="http://www.w3.org/2000/svg">

                        <text x="20" y="20"
                              className="small">{currentTradeTime.toLocaleDateString() + " " + currentTradeTime.toLocaleTimeString()}</text>

                        <PriceAxis screenProps={screenProps} priceGrid={this.state.tradeAgg.priceGrid}/>

                        {tfComponents}

                    </svg>
                </div>
            </div>
        )
    }
}


export default App;
