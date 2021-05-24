class TradeService {
    //http://localhost:6007/api/v1/aggregation-daily/VTBR/2021-05-18?frame-size=6300
    apiBase = "http://localhost:6007/api/v1";

    async loadTradeAggregates(emCode, daystring, frameSize = 6300) {

        return fetch(this.apiBase + `/aggregation-daily/${emCode}/${daystring}?frame-size=${frameSize}`)
            .then((response) => {
                if(response.ok) {
                    return response.json();
                } else {
                    throw new Error(`Loading for ${emCode} ${daystring} ${frameSize} failed with status ${response.status} ${response.statusText}`)
                }
            })
            .then(res => {
                this.parseDates(res);
                return res;
            })
            .catch(e => {
                console.log(e.toString());
                throw e;
            });
    }

    // loadTradeAggregates(emCode, daystring, frameSize = 6300) {
    //     return axios.get(this.apiBase + `/aggregation-daily/${emCode}/${daystring}?frame-size=${frameSize}`)
    //         .then(res => {
    //             console.log("Got response: " + res);
    //             this.parseDates(res.data);
    //             return res.data;
    //         })
    //         .catch(e => {
    //             console.log(e);
    //             throw e;
    //         });
    // }

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