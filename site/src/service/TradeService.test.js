import TradeService from './TradeService';
import axios from "axios";

test('receive data from server', () => {
    const tradeService = new TradeService();
    axios.defaults.adapter = require('axios/lib/adapters/http');

    return tradeService.loadTradeAggregates("VSMO", "2021-05-18", 63000)
        .then(tradeAgg => {
            expect(tradeAgg.emCode).toBe("VSMO");
            expect(tradeAgg.tradeFrames.length).toBe(809);
        })
});
