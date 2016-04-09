hFig = figure(1);
set(gcf, 'PaperPositionMode','auto');
set(hFig, 'Position', [0 0 1200 800])
ftsz = 20;
CDFPoint = 100;
startLine = 0;
endLine = 10;
%startDelayFile = fopen('/Users/user/Downloads/graduateData/startDelayWithQoE');
%[startDelayM] = textscan(startDelayFile,'%n%n%n%n','delimiter','\t');
subplot(2,2,1);
[startDelayMartix] = textread('/Users/user/Downloads/graduateData/0407/startDelayTotal', '%f');
[xAxis, yAxis] = funcCDF(CDFPoint, startLine, endLine, startDelayMartix);
plot(xAxis, yAxis, 'x-', 'LineWidth', 3, 'MarkerSize', 5,'Color','k');
hold on;
[startDelayOtt] = textread('/Users/user/Downloads/graduateData/0407/startDelayOtt', '%f');
[xOttAxis, yOttAxis] = funcCDF(CDFPoint, startLine, endLine, startDelayOtt);
plot(xOttAxis, yOttAxis, 's-', 'LineWidth', 3, 'MarkerSize', 5,'Color','b');
hold on;
[startDelayIPad] = textread('/Users/user/Downloads/graduateData/0407/startDelayPad', '%f');
[xIPadAxis, yIPadAxis] = funcCDF(CDFPoint, startLine, endLine, startDelayIPad);
plot(xIPadAxis, yIPadAxis, 'd-', 'LineWidth', 3, 'MarkerSize', 5,'Color','r');
hold on;
[startDelayPhone] = textread('/Users/user/Downloads/graduateData/0407/startDelayPhone', '%f');
[xPhoneAxis, yPhoneAxis] = funcCDF(CDFPoint, startLine, endLine, startDelayPhone);
plot(xPhoneAxis, yPhoneAxis, '*-', 'LineWidth', 3, 'MarkerSize', 5,'Color','g');
hold on;


xlabel('start delay(second)','fontsize',ftsz);
ylabel('cumulative distribution function', 'fontsize', ftsz);
hleg = legend('total','Ott Stb','Pad','Phone', 'Location','NorthWest');
set(hleg,'FontSize',15);
grid on;

subplot(2,2,2);
[startDelayPhonePoint] = textscan(fopen('/Users/user/Downloads/graduateData/0407/startDelayPhonePoint'), '%f%f','delimiter','\t');
plot(startDelayPhonePoint{1,1}, startDelayPhonePoint{1,2},'*g');
xlabel('start delay(second)','fontsize',ftsz);
ylabel('Fraction of views(%)', 'fontsize', ftsz);
hleg = legend('Phone', 'Location','NorthEast');
set(hleg,'FontSize',15);
grid on;
hold on;
subplot(2,2,3);
[startDelayPadPoint] = textscan(fopen('/Users/user/Downloads/graduateData/0407/startDelayPadPoint'), '%f%f','delimiter','\t');
plot(startDelayPadPoint{1,1}, startDelayPadPoint{1,2},'dr');
xlabel('start delay(second)','fontsize',ftsz);
ylabel('Fraction of views(%)', 'fontsize', ftsz);
hleg = legend('Pad', 'Location','NorthEast');
set(hleg,'FontSize',15);
grid on;
hold on;
subplot(2,2,4);
[startDelayOttPoint] = textscan(fopen('/Users/user/Downloads/graduateData/0407/startDelayOttPoint'), '%f%f','delimiter','\t');
plot(startDelayOttPoint{1,1}, startDelayOttPoint{1,2},'sb');
xlabel('start delay(second)','fontsize',ftsz);
ylabel('Fraction of views(%)', 'fontsize', ftsz);
hleg = legend('Ott Stb', 'Location','NorthEast');
set(hleg,'FontSize',15);
grid on;

