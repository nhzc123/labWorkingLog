hFig = figure(1);
set(gcf, 'PaperPositionMode','auto');
set(hFig, 'Position', [0 0 600 400])
ftsz = 20;
CDFPoint = 100;
startLine = 200;
endLine = 2500;
[userMartix] = textread('/Users/user/Downloads/graduateData/0411/weibullSeries', '%f');
[xAxis, yAxis] = funcCDF(CDFPoint, startLine, endLine, userMartix);
plot(xAxis, yAxis, 'x-', 'LineWidth', 3, 'MarkerSize', 5,'Color','r');
xlabel('entropy','fontsize',ftsz);
ylabel('cumulative distribution function', 'fontsize', ftsz);
hold on;
grid on;
funF = createFit(xAxis,yAxis);


