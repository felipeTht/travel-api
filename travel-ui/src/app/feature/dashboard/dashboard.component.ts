import { Component, OnInit } from '@angular/core';
import { ChartConfiguration, ChartData, ChartType } from 'chart.js';
import { MetricsService } from 'src/app/shared/service/metrics.service';

@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.scss'],
})
export class DashboardComponent implements OnInit {
  dataLoaded: boolean = false;
  totalRequestsProcessed: number = 0;
  pieChartOptions: ChartConfiguration['options'] = {
    responsive: true,
    plugins: {
      legend: {
        display: true,
        position: 'top',
      },
    },
  };
  pieChartData: ChartData<'pie', number[], string | string[]>;
  pieChartType: ChartType = 'pie';
  pieChartPlugins = [];

  barChartOptions: ChartConfiguration['options'] = {
    responsive: true,
    // We use these empty structures as placeholders for dynamic theming.
    scales: {
      x: {},
      y: {
        min: 0,
      },
    },
    plugins: {
      legend: {
        display: false,
      },
    },
  };

  barChartData: ChartData<'bar'>;
  constructor(private metricService: MetricsService) {}

  ngOnInit(): void {
    this.metricService.getMetrics().subscribe((resp) => {
      const { okStatusRespNumber, notFoundRespNumber, errorRespNumber } = resp;
      this.totalRequestsProcessed =
        okStatusRespNumber + notFoundRespNumber + errorRespNumber;
      this.pieChartData = {
        labels: ['Ok', '4xx', '5xx'],
        datasets: [
          {
            data: [okStatusRespNumber, notFoundRespNumber, errorRespNumber],
            backgroundColor: ['#23cd91', '#ffab13', '#fd0303'],
            hoverBackgroundColor: ['#1ba775', '#db9c2a', '#db3a3a'],
            borderColor: ['#23cd91', '#ffab13', '#fd0303'],
            hoverBorderColor: ['#1ba775', '#db9c2a', '#db3a3a'],
          },
        ],
      };
      const { avgResponseTime, minResponseTime, maxResponseTime } = resp;
      this.barChartData = {
        labels: ['Min', 'Max', 'Average'],
        datasets: [
          {
            data: [minResponseTime, maxResponseTime, avgResponseTime],
            label: 'Time in ms',
            backgroundColor: ['#3096da', '#ffab13', '#23cd91'],
            hoverBackgroundColor: ['#2f85bf', '#db9c2a', '#1ba775'],
          },
        ],
      };
      this.dataLoaded = true;
    });
  }
}
