class ScreenUtils {
    static defineScreenProps() {
        const width = document.documentElement.clientWidth * 0.9;
        const height = document.documentElement.clientHeight * 0.95;
        const padding = document.documentElement.clientHeight * 0.05;

        return {
            "width": width,
            "height": height,
            "middleX": width * 0.5,
            "middleY": height * 0.5,
            "padding": padding,
            "valueLabelTreshold": 5
        };
    }

    static priceToX(price, priceGrid, screenProps) {
        const padding = screenProps.padding;
        const deltaPrice = priceGrid.maxPrice - priceGrid.minPrice;
        const paddingWidth = screenProps.width - 2 * padding;
        return padding + paddingWidth * ((price - priceGrid.minPrice) / deltaPrice);
    }

    static valueToRadius(value, screenProps) {
        return Math.sqrt(value) / 150 + 1;
    }
}

export default ScreenUtils;