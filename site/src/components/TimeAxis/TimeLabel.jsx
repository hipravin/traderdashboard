import React from 'react';
import Theme from "../Theme";
import "./TimeLabel.css"

class TimeLabel extends React.Component {

    componentDidMount() {
    }

    render() {
        const th = Theme.theme;
        const sp = this.props.screenProps;
        const ty = sp.middleY;

        const labelx = 10;
        const labelyb = ty - this.props.yshift;
        const labelys = ty + this.props.yshift;
        const labelLineLen = 5;


        return (
            <g>
                <text x="10" y={labelyb + 6} className="timelabeltext">{this.props.timestring}</text>
                <text x="10" y={labelys + 6} className="timelabeltext">{this.props.timestring}</text>
                <line x1={labelx - labelLineLen - 10} x2={labelx + labelLineLen} y1={labelyb} y2={labelyb}
                      stroke={th.mainLinesColor} fill="transparent" strokeWidth="1" strokeDasharray={[1, 5]}/>
                <line x1={labelx - labelLineLen - 10} x2={labelx + labelLineLen} y1={labelys} y2={labelys}
                      stroke={th.mainLinesColor} fill="transparent" strokeWidth="1" strokeDasharray={[1, 5]}/>
            </g>
        );
    }

}

export default TimeLabel;