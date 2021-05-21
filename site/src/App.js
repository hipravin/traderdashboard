import React from 'react';
import './App.css';
import 'bootstrap/dist/css/bootstrap.css';
import PriceAxis from "./components/PriceAxis/PriceAxis";

class App extends React.Component {

    constructor(props) {
        super(props);
        this.state = {
            tradeAgg: {
                priceGrid: {
                    minPrice: 27000,
                    maxPrice: 28000,
                    priceGrid: [27000.00,27050.00,27100.00,27150.00,27200.00,27250.00,27300.00,27350.00,27400.00,27450.00,27500.00,27550.00,27600.00,27650.00,27700.00,27750.00,27800.00,27850.00,27900.00,27950.00,28000.00]
                }
            }
        };
    }

    componentDidMount() {
    }

    defineScreenProps() {
        const width = window.innerWidth * 0.95;
        const height = window.innerHeight * 0.95;
        const padding = window.innerHeight * 0.05;

        return {
            "width": width,
            "height": height,
            "middleX": width * 0.5,
            "middleY": height * 0.5,
            "padding": padding
        };
    }

    render() {

        const screenProps = this.defineScreenProps();

        return (
            <div className="maindiv">
                <div>
                    <svg className="mainsvg" width={screenProps.width} height={screenProps.height} version="1.1"
                         xmlns="http://www.w3.org/2000/svg">

                        <PriceAxis screenProps={screenProps} priceGrid={this.state.tradeAgg.priceGrid}/>

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
