import React from 'react';
import './App.css';
import 'bootstrap/dist/css/bootstrap.css';
import PriceAxis from "./components/PriceAxis/PriceAxis";
import TradeService from "./service/TradeService";
import ScreenUtils from "./components/ScreenUtils";
import TradeFrame from "./components/trades/TradeFrame";

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
                    minPrice: 27000,
                    maxPrice: 28000,
                    priceGrid: [27000.00, 27050.00, 27100.00, 27150.00, 27200.00, 27250.00, 27300.00, 27350.00, 27400.00, 27450.00, 27500.00, 27550.00, 27600.00, 27650.00, 27700.00, 27750.00, 27800.00, 27850.00, 27900.00, 27950.00, 28000.00],

                },
                tradeFrames: []
            },
        };
    }

    componentDidMount() {
        const tradeService = new TradeService();
        tradeService.loadTradeAggregates("VTBR", "2021-05-20", 6300)
            .then(res => this.setState({tradeAgg: res}))
            .then(res => {
                this.startAnimation();
            });
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
                return  tf.tradetimeEnd.getTime() < this.state.tradeAgg.start.getTime() + xtimePassed - timeonscreen
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
                <div>
                    <svg className="mainsvg" width={screenProps.width} height={screenProps.height} version="1.1"
                         xmlns="http://www.w3.org/2000/svg">

                        <text x="20" y="20" className="small">{this.xtime.toLocaleDateString() + " " + this.xtime.toLocaleTimeString()}</text>

                        <PriceAxis screenProps={screenProps} priceGrid={this.state.tradeAgg.priceGrid}/>

                        {tfComponents}

                        {/*<rect id="rect1" x="10" y="10" width="30" height="30" stroke="black" fill="transparent"*/}
                        {/*      strokeWidth="5"/>*/}


                        {/*<text x="20" y="35" className="small">Мой</text>*/}
                        {/*<text x="60" y="35" className="heavy">кот</text>*/}
                        {/*<text x="60" y="55" className="small">очень</text>*/}

                        {/*<text x="100" y="55" className="Rrrrr">Сердит!</text>*/}

                        {/*<rect x="60" y="10" rx="10" ry="10" width="30" height="30" stroke="black" fill="transparent"*/}
                        {/*      strokeWidth="5"/>*/}

                        {/*<circle cx="25" cy="75" r="20" stroke="red" fill="transparent" strokeWidth="5"/>*/}
                        {/*<ellipse cx="75" cy="75" rx="20" ry="5" stroke="red" fill="transparent" strokeWidth="5"/>*/}

                        {/*<line x1="10" x2="50" y1="110" y2="150" stroke="orange" fill="transparent" strokeWidth="5"/>*/}
                        {/*<polyline points="60 110 65 120 70 115 75 130 80 125 85 140 90 135 95 150 100 145"*/}
                        {/*          stroke="orange" fill="transparent" strokeWidth="5"/>*/}

                        {/*<polygon points="50 160 55 180 70 180 60 190 65 205 50 195 35 205 40 190 30 180 45 180"*/}
                        {/*         stroke="green" fill="transparent" strokeWidth="5"/>*/}

                        {/*<path d="M20,230 Q40,205 50,230 T90,230" fill="none" stroke="blue" strokeWidth="5"/>*/}
                    </svg>
                </div>
            </div>
        )
    }
}

// function App() {
//   return (
//     <div className="App">
//       <header className="App-header">
//         <img src={logo} className="App-logo" alt="logo" />
//         <p>
//           Edit <code>src/App.js</code> and save to reload.
//         </p>
//         <a
//           className="App-link"
//           href="https://reactjs.org"
//           target="_blank"
//           rel="noopener noreferrer"
//         >
//           Learn React
//         </a>
//       </header>
//     </div>
//   );
// }

export default App;
