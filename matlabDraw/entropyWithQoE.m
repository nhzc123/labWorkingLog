hFig = figure(1);
set(gcf, 'PaperPositionMode','auto');
set(hFig, 'Position', [0 0 600 400])
ftsz = 20;
CDFPoint = 100;
startLine = 0;
endLine = 1;
[userMartix] = textread('/Users/user/Downloads/graduateData/0411/entropyWithQoE3', '%f');
[xAxis, yAxis] = funcCDF(CDFPoint, startLine, endLine, userMartix);
plot(xAxis, yAxis, '--', 'LineWidth', 3, 'MarkerSize', 5,'Color','r');
hold on;
xlabel('watching percent(%)','fontsize',ftsz);
ylabel('cumulative distribution function', 'fontsize', ftsz);
[user2Martix] = textread('/Users/user/Downloads/graduateData/0411/entropyWithQoE4', '%f');
[x2Axis, y2Axis] = funcCDF(CDFPoint, startLine, endLine, user2Martix);
plot(x2Axis, y2Axis, '-', 'LineWidth', 3, 'MarkerSize', 5,'Color','b');
hleg = legend('entropy<=0.5','entropy>0.5','location','northwest');
set(hleg,'FontSize',15);
hold on;
grid on;