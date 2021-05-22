import React from 'react';
import ScreenUtils from "../ScreenUtils";
import './TradeGroup.css';

class TradeGroup extends React.Component {

    componentDidMount() {
    }

    render() {
        const sp = this.props.screenProps;

        const tx = ScreenUtils.priceToX(this.props.tradeGroup.biggestTrade.price, this.props.priceGrid, this.props.screenProps);

        const ty = sp.middleY;
        const r = ScreenUtils.valueToRadius(this.props.tradeGroup.totalValue);
        // const yshift = this.props.yshift>0 ? this.props.yshift + r : this.props.yshift - r;
        const yshift = this.props.yshift;

        const fill = this.props.buysell === "B" ? "green" : "red";

        const valueMill = (this.props.tradeGroup.totalValue / 1000000).toFixed(0);

        const circle = <circle cx={tx} cy={ty + yshift} r={r} stroke="black" fill={fill} strokeWidth="1"/>;

        if(valueMill >= sp.valueLabelTreshold) {
            return (
                <g>
                    {circle}
                    <text x={tx - 4} y={ty + yshift + 3} className="value">{valueMill}</text>
                </g>
            );
        } else {
            return (circle);
        }
    }
}

export default TradeGroup;