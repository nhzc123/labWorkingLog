hFig = figure(1);
set(gcf, 'PaperPositionMode','auto');
set(hFig, 'Position', [0 0 600 300])
ftsz = 20;
CDFPoint = 100;
startLine = 0;
endLine = 10;
[videoRankScore] = textscan(fopen('/Users/user/Downloads/graduateData/0408/videoRankPoint'), '%f%f','delimiter', '\t');
plot(videoRankScore{1,1},videoRankScore{1,2},'lineWidth',3,'Color','r');
xlabel('video Rank','fontsize',ftsz);
ylabel('the percent of rank(%)', 'fontsize', ftsz);
%hleg = legend('Pad');
grid on;
hold on;
paramEsts = gpfit(videoRankScore{1,1});
line(videoRankScore{1,1},gppdf(videoRankScore{1,1},paramEsts(1),paramEsts(2)));


