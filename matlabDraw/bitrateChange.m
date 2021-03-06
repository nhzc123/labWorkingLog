hFig = figure(1);
set(gcf, 'PaperPositionMode','auto');
set(hFig, 'Position', [0 0 600 400])
ftsz = 20;
CDFPoint = 100;
startLine = 0;
endLine = 1;
[userMartix] = textread('/Users/user/Downloads/graduateData/0414/changefu600', '%f');
[xAxis, yAxis] = funcCDF(CDFPoint, startLine, endLine, userMartix);
plot(xAxis, yAxis, '-', 'LineWidth', 3, 'MarkerSize', 8,'Color','r');
hold on;
[user2Martix] = textread('/Users/user/Downloads/graduateData/0414/change600', '%f');
[x2Axis, y2Axis] = funcCDF(CDFPoint, startLine, endLine, user2Martix);
plot(x2Axis, y2Axis, '-', 'LineWidth', 3, 'MarkerSize', 8,'Color','b');
hold on;
[userMartix] = textread('/Users/user/Downloads/graduateData/0414/changefu1000', '%f');
[xAxis, yAxis] = funcCDF(CDFPoint, startLine, endLine, userMartix);
plot(xAxis, yAxis, '-', 'LineWidth', 3, 'MarkerSize', 8,'Color','k');
hold on;
[userMartix] = textread('/Users/user/Downloads/graduateData/0414/change1000', '%f');
[xAxis, yAxis] = funcCDF(CDFPoint, startLine, endLine, userMartix);
plot(xAxis, yAxis, '-', 'LineWidth', 3, 'MarkerSize', 8,'Color','g');
hold on;
[userMartix] = textread('/Users/user/Downloa
plot(xAxis, yAxis, '-', 'LineWidth', 3, 'MarkerSize', 8,'Color','c');
hold on;
[userMartix] = textread('/Users/user/Downloads/graduateData/0414/change1600', '%f');
[xAxis, yAxis] = funcCDF(CDFPoint, startLine, endLine, userMartix);
plot(xAxis, yAxis, '-', 'LineWidth', 3, 'MarkerSize', 8,'Color','m');
hold on;
grid on;
xlabel('watching percent ads/graduateData/0414/changefu1600', '%f');
[xAxis, yAxis] = funcCDF(CDFPoint, startLine, endLine, userMartix);fter switching(%)','fontsize',ftsz);
ylabel('cumulative distribution function', 'fontsize', ftsz);
hleg = legend('median to low','low to median','high to median','median to high','high to low','low to high','location','southeast');
set(hleg,'FontSize',15);