class Theme {
    static theme = Theme.defineTheme();

    mainLinesColor = "black";
    lineStrokeWidthSmall = 1;
    lineStrokeWidthMiddle = 3;

    priceLabelLength = 20;

    static defineTheme() {
        return new Theme();
    }
}


export default Theme;