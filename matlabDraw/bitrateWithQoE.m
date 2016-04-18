hFig = figure(1);
set(gcf, 'PaperPositionMode','auto');
set(hFig, 'Position', [0 0 600 400])
ftsz = 20;
CDFPoint = 100;
startLine = 0;
endLine = 1;
[userMartix] = textread('/Users/user/Downloads/graduateData/0414/s3', '%f');
[xAxis, yAxis] = funcCDF(CDFPoint, startLine, endLine, userMartix);
plot(xAxis, yAxis, '--', 'LineWidth', 3, 'MarkerSize', 5,'Color','r');
hold on;
xlabel('watching percent(%)','fontsize',ftsz);
ylabel('cumulative distribution function', 'fontsize', ftsz);
[user2Martix] = textread('/Users/user/Downloads/graduateData/0414/s4', '%f');
[x2Axis, y2Axis] = funcCDF(CDFPoint, startLine, endLine, user2Martix);
plot(x2Axis, y2Axis, '-', 'LineWidth', 3, 'MarkerSize', 5,'Color','b');

hold on;
[userMartix] = textread('/Users/user/Downloads/graduateData/0414/s5', '%f');
[xAxis, yAxis] = funcCDF(CDFPoint, startLine, endLine, userMartix);
plot(xAxis, yAxis, '--', 'LineWidth', 3, 'MarkerSize', 5,'Color','k');
hold on;

hleg = legend('700k','1300k','2300k','location','southeast');
set(hleg,'FontSize',15);
grid on;