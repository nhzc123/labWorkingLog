hFig = figure(1);
set(gcf, 'PaperPositionMode','auto');
set(hFig, 'Position', [0 0 1200 400])
ftsz = 20;
subplot(1,2,1);
xAxis = [0 1 2 3 4 5];
yAxis = [0.015 0.1697 0.35 0.42 0.52 0.50];
plot(xAxis, yAxis, 'x-', 'LineWidth', 3, 'MarkerSize', 10,'Color','r');
hold on;
xlabel('switching time','fontsize',ftsz);
ylabel('average QoE', 'fontsize', ftsz);
grid on;

subplot(1,2,2);
money=[273138,343480,291466,81286,64655,28979];%��������
name={'zero','one' , ' two' ,  'three',' four' ,' five'};%�����ǩ
%money = [302293,302253,162234,125732,60742,23794,28090,11870,189274];
%name = {'0','1','2','3','4','5','6','7','> 7'};
explode=[1 0 0 1 0 1 ];%����ͻ���Ĳ���
bili=money/sum(money);%�������
baifenbi=round(bili*10000)/100;%����ٷֱ�
baifenbi=num2str(baifenbi');%ת��Ϊ�ַ���
baifenbi=cellstr(baifenbi);%ת��Ϊ�ַ�������
%��ÿ���������2���ո�
for i=1:length(name)
    name(i)={[name{i},blanks(2)]};
end
bfh=cellstr(repmat('%',length(money),1));%�����ٷֺ��ַ�������
c=strcat(name,baifenbi',bfh');
pie(money,explode,c)
set(gca,'fontsize',100);
%title('(b) switching time distribution','fontsize',ftsz,