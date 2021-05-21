import React from 'react';
import Theme from "../Theme";
import ScreenUtils from "../ScreenUtils";
import TradeGroup from "./TradeGroup";

class TradeFrame extends React.Component {

    componentDidMount() {
    }

    render() {
        const sp = this.props.screenProps;

        const groups = [];

        const yshift = ScreenUtils.xtimeonscreenToShift(this.props.tradeFrame.xtimeonscreen) + 10;

        if (this.props.tradeFrame.buyTradeGroup) {
            groups.push(
                <TradeGroup key="buy" screenProps={this.props.screenProps} yshift={-yshift} buysell="B"
                            tradeGroup={this.props.tradeFrame.buyTradeGroup} priceGrid={this.props.priceGrid}/>
            );
        }
        if (this.props.tradeFrame.sellTradeGroup) {
            groups.push(
                <TradeGroup key="sell" screenProps = {this.props.screenProps} yshift={yshift} buysell="S"
                            tradeGroup={this.props.tradeFrame.sellTradeGroup} priceGrid={this.props.priceGrid}/>
            );
        }

        return (
            groups
        );
    }
}

export default TradeFrame;