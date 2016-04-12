clear all;clc;clf
hFig = figure(1);
set(gcf, 'PaperPositionMode','auto');
set(hFig, 'Position', [0 0 600 400])
p=0:1:5;
wd=[446.67,573.11,637.79,699.25,735.86,742.20];
m=[15.2,21.5,25.0,28.0,30.0,35.6];
a={'1%','5%','10%','20%','50%','100%'};
fun=@(x,y) bar(x,y,0.5);
[ax,param1,param2] = plotyy(p,wd,p,m,fun,@plot);
set(ax(1),'xTick',0:1:5);
set(ax(2),'xTick',0:1:5);
set(ax(1),'xTickLabel',a);
set(ax(2),'xTickLabel',a);
set(param2,'LineStyle','--',...
'LineWidth',2,...
'Color','r',...
'Marker','o',...
'MarkerEdgeColor','r',...
'MarkerFaceColor','r',...
'MarkerSize',5)
hleg = legend([param1,param2],'watching time(s)','fraction of views(%)','Location','northwest');
set(hleg,'fontsize',20);
set(ax(1),'yTick',0:50:1000);
set(ax(2),'yTick',0:5:40);
xlabel('video rank','fontsize',20);
grid on;

