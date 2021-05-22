import axios from 'axios';

class TradeService {
    //http://localhost:6007/api/v1/aggregation-daily/VTBR/2021-05-18?frame-size=6300
    apiBase = "http://localhost:6007/api/v1";

    loadTradeAggregates(emCode, daystring, frameSize = 6300) {
        return axios.get(this.apiBase + `/aggregation-daily/${emCode}/${daystring}?frame-size=${frameSize}`)
            .then(res => {
                console.log("Got response: " + res);
                this.parseDates(res.data);
                return res.data;
            })
            .catch(e => {
                console.log(e);
                throw e;
            });

    // .then(res => {
    //         const tradeAgg = res.data;
    //         console.log("loaded " + res.data.tradeFrames.length + " frames fpr " + res.emCode );
    //     });

    }

    parseDates(tradeAgg) {
        tradeAgg.start = new Date(tradeAgg.start);
        tradeAgg.end = new Date(tradeAgg.end);

        tradeAgg.tradeFrames.forEach(tf => {
            tf.tradetimeStart = new Date(tf.tradetimeStart);
            tf.tradetimeEnd = new Date(tf.tradetimeEnd);
        })
    }
}

export default TradeService