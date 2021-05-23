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
                // priceGrid: {
                //     minPrice: 27000,
                //     maxPrice: 28000,
                //     priceGrid: [27000.00, 27050.00, 27100.00, 27150.00, 27200.00, 27250.00, 27300.00, 27350.00, 27400.00, 27450.00, 27500.00, 27550.00, 27600.00, 27650.00, 27700.00, 27750.00, 27800.00, 27850.00, 27900.00, 27950.00, 28000.00],
                //
                // },
                priceGrid: {
                    minPrice: 0.7000,
                    maxPrice: 0.7100,
                    priceGrid: [0.7000,0.7005,0.7010,0.7015,0.7020,0.7025,0.7030,0.7035,0.7040,0.7045,0.7050,0.7055,0.7060,0.7065,0.7070,0.7075,0.7080,0.7085,0.7090,0.7095,0.7100],

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
