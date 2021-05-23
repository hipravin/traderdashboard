import React from 'react';
import Theme from "../Theme";
import ScreenUtils from "../ScreenUtils";
import './ConfigPanel.css';
import {Button} from "@material-ui/core";
import DirectionsRunIcon from '@material-ui/icons/DirectionsRun';

const styleRunButton = {
    margin: 0,
    font: '14px Roboto',
};

class ConfigPanel extends React.Component {

    defaultConfig = {
        emcode: "LKOH",
        daystring: "2021-05-21",
        framesize: 6300
    };

    componentDidMount() {
        this.props.onConfigUpdate(this.defaultConfig);
    }

    render() {
        return (
            <div className="configpanel">
                <p>This is one</p>
                <p>And this is second</p>
                <Button
                    variant="contained"
                    color="primary"
                    style={styleRunButton}
                    endIcon={<DirectionsRunIcon/>}>
                    Run
                </Button>
            </div>
        );
    }


}

export default ConfigPanel;