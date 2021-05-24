import React from 'react';
import Theme from "../Theme";
import ScreenUtils from "../ScreenUtils";
import './ConfigPanel.css';
import {Button} from "@material-ui/core";
import DirectionsRunIcon from '@material-ui/icons/DirectionsRun';
import TextField from '@material-ui/core/TextField';
import MenuItem from '@material-ui/core/MenuItem';

const styleRunButton = {
    margin: '15px 10px',
    font: '14px Roboto',
};

const DATE_DEPTH = 30;


class ConfigPanel extends React.Component {

    dateOptions = this.generateDates(DATE_DEPTH);

    constructor(props) {
        super(props);

        this.state = {
            selectedConfig: {
                emcode: "VTBR",
                daystring: this.formatDateDased(new Date()),
                framesize: 6000
            }
        };
    }


    handleRun = (event) => {
        this.props.onConfigUpdate(this.state.selectedConfig);
    };
    handleEmCodeChange = (event) => {
        const updatedConfig = this.state.selectedConfig;
        updatedConfig.emcode = event.target.value;
        this.setState(updatedConfig);
    };
    handleDateChange = (event) => {
        const updatedConfig = this.state.selectedConfig;
        updatedConfig.daystring = event.target.value;
        this.setState(updatedConfig);
    };
    handleFrameChange = (event) => {
        const updatedConfig = this.state.selectedConfig;
        updatedConfig.framesize = event.target.value;
        this.setState(updatedConfig);
    };

    async componentDidMount() {
        //read config from server

    }

    render() {
        const emcode = this.state.selectedConfig.emcode;
        const date = this.state.selectedConfig.daystring;

        return (
            <div className="configpanel">
                {/*<form noValidate autoComplete="off">*/}
                <div className="configelem">
                    <TextField
                        id="select-emcode"
                        select
                        label="Emitent"
                        value={emcode}
                        onChange={this.handleEmCodeChange}
                        SelectProps={{
                            native: true,
                        }}
                        helperText="Choose emitent"
                    >
                        {emitents.map((option) => (
                            <option key={option} value={option}>
                                {option}
                            </option>
                        ))}
                    </TextField>
                </div>
                <div className="configelem">
                    <TextField
                        id="select-date"
                        select
                        label="Date"
                        value={date}
                        onChange={this.handleDateChange}
                        SelectProps={{
                            native: true,
                        }}
                        helperText="Select date"
                    >
                        {this.dateOptions.map((option) => (
                            <option key={option} value={option}>
                                {option}
                            </option>
                        ))}
                    </TextField>
                </div>

                <div className="configelem">
                    <TextField
                        id="select-framesize"
                        select
                        label="Frame"
                        value={this.state.selectedConfig.framesize}
                        onChange={this.handleFrameChange}
                        SelectProps={{
                            native: true,
                        }}
                        helperText="Select framesize"
                    >
                        {frameOptions.map((option) => (
                            <option key={option} value={option}>
                                {option}
                            </option>
                        ))}
                    </TextField>
                </div>
                <div className="configelem">
                    <Button
                        variant="contained"
                        color="primary"
                        style={styleRunButton}
                        onClick={this.handleRun}
                        endIcon={<DirectionsRunIcon/>}>
                        Run
                    </Button>
                </div>
                {/*</form>*/}
            </div>
        );
    }

    generateDates(count) {
        const datesFormatted = [];

        for (let i = 0; i < count; i++) {
            const date = new Date();
            date.setDate(date.getDate() - i);
            datesFormatted.push(this.formatDateDased(date));
        }

        return datesFormatted;
    }

    formatDateDased(date) {
        return date.toISOString().slice(0, 10);
    }
}

const frameOptions = [
    3000,
    6000,
    9000,
    12000,
    15000,
    18000,
    24000,
    30000,
    60000,
    120000,
    240000,
    600000,
    3600000,
    3600000000
];

const emitents = ["GAZP"
    , "VTBR"
    , "MRKP"
    , "CHMF"
    , "SIBN"
    , "ROSN"
    , "SBER"
    , "FLOT"
    , "ENRU"
    , "BANEP"
    , "TATNP"
    , "PHOR"
    , "TRMK"
    , "MAGN"
    , "AFKS"
    , "AFLT"
    , "ALRS"
    , "AQUA"
    , "CBOM"
    , "DSKY"
    , "FEES"
    , "FIVE"
    , "GMKN"
    , "HYDR"
    , "IRAO"
    , "LKOH"
    , "LNTA"
    , "LSRG"
    , "MGNT"
    , "MOEX"
    , "MTSS"
    , "NLMK"
    , "NVTK"
    , "PIKK"
    , "PLZL"
    , "POLY"
    , "RTKM"
    , "RUAL"
    , "SBERP"
    , "SNGS"
    , "SNGSP"
    , "TATN"
    , "TCSG"
    , "TRNFP"
    , "UPRO"
    , "YNDX"
    , "BANE"
    , "VSMO"
    , "MSRS"
    , "RASP"
    , "MTLR"]


export default ConfigPanel;