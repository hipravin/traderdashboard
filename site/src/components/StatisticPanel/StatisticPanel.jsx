import React from 'react';
import PropTypes from 'prop-types';
import './StatisticPanel.css';

class StatisticPanel extends React.Component {
    constructor(props) {
        super(props);
    }

    componentDidMount() {
    }

    render() {
        const buyM = (this.props.stats.visiblebuy / 1000000).toFixed(1)
        const sellM = (this.props.stats.visiblesell / 1000000).toFixed(1)

        return (
            <div className="StatisticPanel">
                <div className="statblock">
                    <span>Visible (M)</span>
                    <span className="buyamount">{buyM}</span>
                    <span className="sellamount">{sellM}</span>
                </div>
            </div>
        );
    }
}

StatisticPanel.propTypes = {};

StatisticPanel.defaultProps = {};

export default StatisticPanel;
