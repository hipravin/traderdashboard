import React from 'react';
import './PriceAxis.css';
import Theme from "../Theme";
import PriceLabel from "./PriceLabel";

class PriceAxis extends React.Component {

    componentDidMount() {
    }

    render() {
        const sp = this.props.screenProps;
        const th = Theme.theme;

        const labels = this.props.priceGrid.priceGrid.map(priceValue => {
                return <PriceLabel key={priceValue} price={priceValue} priceGrid={this.props.priceGrid} screenProps={this.props.screenProps}/>;
            }
        );
        return (
            <g>
                {labels}
                <line x1="0" x2={sp.width} y1={sp.middleY} y2={sp.middleY} stroke={th.mainLinesColor}
                      fill={th.mainLinesColor} strokeWidth={th.lineStrokeWidthSmall}/>
            </g>
        );
    }
}

export default PriceAxis;