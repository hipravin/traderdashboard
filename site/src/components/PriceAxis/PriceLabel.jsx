import React from 'react';
import Theme from "../Theme";
import ScreenUtils from "../ScreenUtils";
import "./PriceLabel.css"

class PriceLabel extends React.Component {

    componentDidMount() {
    }

    render() {
        const sp = this.props.screenProps;
        const th = Theme.theme;

        const labelx = ScreenUtils.priceToX(this.props.price, this.props.priceGrid, this.props.screenProps);
        const labely = 50;
        const labelBottomy = this.props.screenProps.height - 50;
        const middley = sp.middleY;
        const labelLineLen = 50;
        let percentPrice = (this.props.price - this.props.priceGrid.minPrice) / this.props.priceGrid.minPrice;
        percentPrice = (percentPrice * 100).toFixed(1);



        return (
            <g>
                <text x="0" y="0" className="labeltext" transform={`translate(${labelx-15} ${labely+20}) rotate(300)`}>{this.props.price}</text>
                <text x="0" y="0" className="labeltext" transform={`translate(${labelx-15} ${labelBottomy+45}) rotate(300)`}>{percentPrice + "%"}</text>
                <line x1={labelx} x2={labelx} y1={middley-labelLineLen} y2={middley+labelLineLen} stroke={th.mainLinesColor} fill="transparent" strokeWidth="1" strokeDasharray={[1, 5]}/>
            </g>
        );
    }


}

export default PriceLabel;