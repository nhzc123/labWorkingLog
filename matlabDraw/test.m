hFig = figure(1);
set(gcf, 'PaperPositionMode','auto');
set(hFig, 'Position', [0 0 1200 800])
ftsz = 20;

[xAxis] = textread(['/Users/user/code/py/meizhi/data/' 'test'], '%f');
[yAxis] = textread(['/Users/user/code/py/meizhi/data/' 'tianya100w_markov_attack_12306_10000000_1000' '.txt'], '%f');
%[xAxis] = textread('/Users/user/code/py/idCount', '%f');
%plot(xAxis, yAxis, '-.', 'LineWidth', 3, 'Color','r');
plot(xAxis(1:10:end),yAxis(1:10:end),'or');
hold on;
[yAxis] = textread(['/Users/user/code/py/meizhi/data/'  'tianya100w_pcfg_attack_12306_10000000_1000'  '.txt'], '%f');

%pic1 = plot(xAxis,yAxis, '--', 'LineWidth', 3,'MarkerSize', 5, 'Color','b');

plot(xAxis(1:10:end),yAxis(1:10:end),'.');
hold on;
xDraw = [0	50	100	150	200	250	300	350	400	450	500	550	620	650	700	750	805];
yDraw = [0	0.022	0.039	0.048	0.061	0.075	0.081	0.098	0.103	0.108	0.114	0.125	0.136	0.187	0.203	0.214	0.246];
plot(xDraw, yDraw, '--', 'LineWidth', 3,'MarkerSize', 5, 'Color','g');
xlabel('guess time','fontsize',ftsz);
ylabel('cumulative distribution function', 'fontsize', ftsz);
hleg = legend('markov','pcfg','lda-pi','location','northwest');
set(hleg,'fontsize',18);
set(gca,'ylim',[0 1])
set(gca,'xTickLabel',[0 1 2 3 4 5 6 7 8 9 10]);
title('(a). tianya100w attack 12306','fontsize',ftsz);
hold on;
grid on;