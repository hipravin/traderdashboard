import React from 'react';
import Theme from "../Theme";
import ScreenUtils from "../ScreenUtils";

class TradeFrame extends React.Component {

    componentDidMount() {
    }

    render() {
        const sp = this.props.screenProps;

        if(this.props.tradeFrame.buyTradeGroup && this.props.tradeFrame.sellTradeGroup) {

            const btx = ScreenUtils.priceToX(this.props.tradeFrame.buyTradeGroup.minPrice, this.props.priceGrid, this.props.screenProps);
            const stx = ScreenUtils.priceToX(this.props.tradeFrame.sellTradeGroup.minPrice, this.props.priceGrid, this.props.screenProps);
            const yshift = ScreenUtils.xtimeonscreenToShift(this.props.tradeFrame.xtimeonscreen) + 10;

            const ty = sp.middleY;
            const rbuy = ScreenUtils.valueToRadius(this.props.tradeFrame.buyTradeGroup.totalValue);
            const rsell = ScreenUtils.valueToRadius(this.props.tradeFrame.sellTradeGroup.totalValue);

            return (
                <g>
                    <circle key="buy" cx={btx} cy={ty - yshift} r={rbuy} stroke="black" fill="green" strokeWidth="1"/>
                    <circle key="sell" cx={stx} cy={ty + yshift} r={rsell} stroke="black" fill="red" strokeWidth="1"/>
                </g>
            );
        } else {
            return (
                <g></g>
            );
        }
    }
}

export default TradeFrame;