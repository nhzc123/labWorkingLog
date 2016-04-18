hFig = figure(1);
set(gcf, 'PaperPositionMode','auto');
set(hFig, 'Position', [0 0 1600 400])
ftsz = 20;
CDFPoint = 100;
startLine = 50;
endLine = 2000;

subplot(1,2,1);
[userMartix] = textread('/Users/user/Downloads/graduateData/0411/weibullSeries', '%f');
[xAxis, yAxis] = funcCDF(CDFPoint, startLine, endLine, userMartix);
[fitresult,funF,xData,yData] = createFit(xAxis,yAxis');

h = plot(fitresult,xData,yData);
set(h,'markersize',10,'linewidth',5);
hl = legend('r = 0.1553, k = 0.9134','series','fontsize',25,'location', 'northwest');
set(hl,'fontsize',20);
xlabel('viewing time(s)','fontsize',ftsz);
ylabel('cumulative distribution function', 'fontsize', ftsz);
%set(gca, 'ytick', 0.3:0.05:1);
hold on;

hold on;
grid on;


subplot(1,2,2);
[userMartix] = textread('/Users/user/Downloads/graduateData/0411/weibullMovie', '%f');
[xAxis, yAxis] = funcCDF(CDFPoint, 200, 4000, userMartix);
[fitresult,funF,xData,yData] = createFit(xAxis,yAxis');

h = plot(fitresult,xData,yData);
set(h,'markersize',10,'linewidth',5);
hl = legend('r =186.8, k = 0.3022','movie','fontsize',25,'location', 'northwest');
set(hl,'fontsize',20);
xlabel('viewing time(s)','fontsize',ftsz);
ylabel('cumulative distribution function', 'fontsize', ftsz);
%set(gca, 'ytick', 0.3:0.05:1);
hold on;

hold on;
grid on;

