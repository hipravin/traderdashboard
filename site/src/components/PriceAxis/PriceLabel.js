import React from 'react';
import Theme from "../Theme";
import ScreenUtils from "../ScreenUtils";

class PriceLabel extends React.Component {

    componentDidMount() {
    }

    render() {
        const sp = this.props.screenProps;
        const th = Theme.theme;

        const labelx = ScreenUtils.priceToX(this.props.price, this.props.priceGrid, this.props.screenProps);
        const labely = sp.middleY;

        return (
            <g>
                <text x="0" y="0" className="labeltext" transform={`translate(${labelx+5} ${labely+20}) rotate(270)`}>{this.props.price}</text>
                <line x1={labelx} x2={labelx} y1={labely-150} y2={labely+150} stroke={th.mainLinesColor} fill="transparent" strokeWidth="1" strokeDasharray={[1, 5]}/>
            </g>
        );
    }


}

export default PriceLabel;