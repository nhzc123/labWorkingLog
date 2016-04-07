hFig = figure(1);
set(gcf, 'PaperPositionMode','auto');
set(hFig, 'Position', [0 0 800 600])
ftsz = 16;
CDFPoint = 100;
startLine = 0;
endLine = 10;
[startDelayMartix] = textread('/Users/user/Downloads/graduateData/startDelay', '%f');
[xAxis, yAxis] = funcCDF(CDFPoint, startLine, endLine, startDelayMartix);
plot(xAxis, yAxis, 'x-', 'LineWidth', 1, 'MarkerSize', 5,'Color','r');
hold on;
[startDelayOtt] = textread('/Users/user/Downloads/graduateData/startDelayOtt', '%f');
[xOttAxis, yOttAxis] = funcCDF(CDFPoint, startLine, endLine, startDelayOtt);
plot(xOttAxis, yOttAxis, 's-', 'LineWidth', 1, 'MarkerSize', 5,'Color','b');
hold on;
[startDelayIPad] = textread('/Users/user/Downloads/graduateData/startDelayIPad', '%f');
[xIPadAxis, yIPadAxis] = funcCDF(CDFPoint, startLine, endLine, startDelayIPad);
plot(xIPadAxis, yIPadAxis, 'd-', 'LineWidth', 1, 'MarkerSize', 5,'Color','k');
hold on;

xlabel('start delay(second)','fontsize',ftsz);
ylabel('cumulative distribution function', 'fontsize', ftsz);
hleg = legend('total','Ott','Ipad', 'Location','NorthWest');
set(hleg,'FontSize',20);
grid on;
print -deps2c startDelay.eps;

