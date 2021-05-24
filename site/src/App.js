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

class App extends React.Component {

    timeMultiplier = 100; // animate x time faster than real trades
    animationTickMillis = 0;
    animationStartedTime;
    animationInterval;
    xtime = new Date();

    constructor(props) {
        super(props);
        this.state = {
            tradeAgg: {
                priceGrid: {
                    minPrice: 0,
                    maxPrice: 1000,
                    priceGrid: [0, 100, 200, 300, 400, 500, 600, 700, 800, 900, 1000],

                },
                tradeFrames: []
            },
        };
    }

    handleConfigUpdate({emcode, daystring, framesize}) {
        notify("Loading started...");
        const tradeService = new TradeService();
        tradeService.loadTradeAggregates(emcode, daystring, framesize)
            .then(res => {
                notify("Loading comleted");
                this.setState({tradeAgg: res})
            })
            .then(res => {
                this.startAnimation();
            })
            .catch(e => {
                notify("Loading failed: " + e.toString());
                console.log("Failed to load data: " + e);
            });
    }

    componentDidMount() {
    }

    startAnimation() {
        console.log("Animation started");
        this.animationStartedTime = new Date().getTime();
        this.animationInterval = setInterval(() => {
            this.animationTick();
        }, 50);
    }

    animationTick() {
        const rtimePassed = new Date().getTime() - this.animationStartedTime;
        const xtimePassed = rtimePassed * this.timeMultiplier;
        const timeonscreen = 30000 * this.timeMultiplier;
        this.xtime = new Date(this.state.tradeAgg.start.getTime() + xtimePassed);

        console.log("Animation tick " + xtimePassed);

        if (this.state.tradeAgg.start && this.state.tradeAgg.end
            && this.state.tradeAgg.start.getTime() + xtimePassed > this.state.tradeAgg.end.getTime()) {
            console.log("Animation finished after " + rtimePassed + " ms");
            clearInterval(this.animationInterval);
        } else if (this.state.tradeAgg.start && this.state.tradeAgg.end) {
            this.state.tradeAgg.tradeFrames.filter(tf => {
                return tf.tradetimeStart.getTime() <= this.state.tradeAgg.start.getTime() + xtimePassed
                    && tf.tradetimeEnd.getTime() >= this.state.tradeAgg.start.getTime() + xtimePassed - timeonscreen
            }).forEach(tf => {
                tf.visible = true;
                tf.xtimeonscreen = (this.state.tradeAgg.start.getTime() + xtimePassed) - tf.tradetimeStart.getTime();
            });

            this.state.tradeAgg.tradeFrames.filter(tf => {
                return tf.tradetimeEnd.getTime() < this.state.tradeAgg.start.getTime() + xtimePassed - timeonscreen
            }).forEach(tf => {
                tf.visible = false;
                tf.xtimeonscreen = (this.state.tradeAgg.start.getTime() + xtimePassed) - tf.tradetimeStart.getTime();
            });

            const tradeAgg = this.state.tradeAgg;
            this.setState({tradeAgg: tradeAgg});
        }
    }

    render() {

        const screenProps = ScreenUtils.defineScreenProps();
        const visibleTradeFrames = this.state.tradeAgg.tradeFrames.filter(tf => tf.visible);

        const tfComponents = visibleTradeFrames.map(tf => {
            return <TradeFrame key={tf.tradetimeStart} screenProps={screenProps} tradeFrame={tf}
                               priceGrid={this.state.tradeAgg.priceGrid}/>
        });

        return (
            <div className="maindiv">
                <Notifier />
                <ConfigPanel onConfigUpdate={this.handleConfigUpdate.bind(this)}/>
                <div>
                    <svg className="mainsvg" width={screenProps.width} height={screenProps.height} version="1.1"
                         xmlns="http://www.w3.org/2000/svg">

                        <text x="20" y="20"
                              className="small">{this.xtime.toLocaleDateString() + " " + this.xtime.toLocaleTimeString()}</text>

                        <PriceAxis screenProps={screenProps} priceGrid={this.state.tradeAgg.priceGrid}/>

                        {tfComponents}

                    </svg>
                </div>
            </div>
        )
    }
}

export default App;
