function [fitresult, gof,xData,yData] = createFit(xAxis, yAxis)
%CREATEFIT(XAXIS,YAXIS)
%  Create a fit.
%
%  Data for 'untitled fit 1' fit:
%      X Input : xAxis
%      Y Output: yAxis
%  Output:
%      fitresult : a fit object representing the fit.
%      gof : structure with goodness-of fit info.
%
%  See also FIT, CFIT, SFIT.

%  Auto-generated by MATLAB on 11-Apr-2016 21:27:25


%% Fit: 'untitled fit 1'.
[xData, yData] = prepareCurveData( xAxis, yAxis );

% Set up fittype and options.
ft = fittype( '1-exp(-(x/a)^m)', 'independent', 'x', 'dependent', 'y' );
opts = fitoptions( 'Method', 'NonlinearLeastSquares' );
opts.Display = 'Off';
opts.StartPoint = [0.743132468124916 0.392227019534168];

% Fit model to data.
[fitresult, gof] = fit( xData, yData, ft, opts );

% Plot fit with data.
%figure( 'Name', 'untitled fit 1' );
%h = plot( fitresult);
%hold on;
%plot( xData, yData);

%legend('weibull fit line', 'movie', 'Location', 'NorthWest' );
% Label axes
%xlabel( 'viewing time(s)' );
%ylabel( 'cumulative distribution function' );
grid on


