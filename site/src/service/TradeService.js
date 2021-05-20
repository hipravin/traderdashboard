import axios from 'axios';

class TradeService {
    //http://localhost:6007/api/v1/aggregation-daily/VTBR/2021-05-18?frame-size=6300
    apiBase = "http://localhost:6007/api/v1";

    loadTradeAggregates(emCode, daystring, frameSize = 6300) {
        return axios.get(this.apiBase + `/aggregation-daily/${emCode}/${daystring}?frame-size=${frameSize}`)
            .then(res => {
                console.log("Got response: " + res);
                return res.data;
            })
            .catch(e => {
                console.log(e);
            });

    // .then(res => {
    //         const tradeAgg = res.data;
    //         console.log("loaded " + res.data.tradeFrames.length + " frames fpr " + res.emCode );
    //     });

    }
}

export default TradeService