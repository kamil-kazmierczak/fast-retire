import {AfterViewInit, Component, inject, OnInit, ViewChild} from '@angular/core';
import {OverviewService, PortfolioResponse} from "./overview-service";
import {
    ChartComponent,
    ApexAxisChartSeries,
    ApexChart,
    ApexXAxis,
    ApexTitleSubtitle,
    NgApexchartsModule
} from "ng-apexcharts";
import {AsyncPipe} from "@angular/common";

export type ChartOptions = {
    series: ApexAxisChartSeries;
    chart: ApexChart;
    xaxis: ApexXAxis;
    title: ApexTitleSubtitle;
}

@Component({
    selector: 'app-overview',
    imports: [
        NgApexchartsModule,
        AsyncPipe
    ],
    templateUrl: './overview.html',
})
export class OverviewComponent implements OnInit {
    private overviewService = inject(OverviewService);
    portfolioResponse$ = this.overviewService.getOverview('1', 'PLN');
    portfolioTimelineResponse$ = this.overviewService.getTimelineOverview('1', 'BTC', 'PLN');

    @ViewChild("pieChart") pie!: ChartComponent;
    @ViewChild("lineChart") line!: ChartComponent;

    public pieChartOptions: Partial<ChartOptions> | any;
    public lineChartOptions: Partial<ChartOptions> | any;

    ngOnInit() {
        this.portfolioResponse$.subscribe(portfolio => {
            this.pieChartOptions = {
                series: [
                    {
                        data: portfolio.portfolioItems.map(item => ({
                            x: item.assetName,
                            y: item.value
                        }))
                    }
                ],
                chart: {
                    width: 320,
                    type: "pie"
                },
                responsive: [
                    {
                        breakpoint: 280,
                        options: {
                            chart: {
                                width: 200
                            },
                        }
                    }
                ]
            }
        })

        this.portfolioTimelineResponse$.subscribe(portfolioTimeline => {
            this.lineChartOptions = {
                series: [{
                        name: 'BTC',
                        data: portfolioTimeline.portfolioTimelineItems.map(item => ({
                            x: new Date(item.date).getTime(),
                            y: item.value
                        }))
                    }],
                xaxis: {
                   type: 'datetime'
                },
                yaxis: {
                    title: {
                        text: 'PLN'
                    },
                    min: 0
                },
                chart: {
                    width: 1600,
                    type: 'area',
                    // stacked: false,
                    height: 300,
                    stroke: {
                        width: 0.5
                    },
                    zoom: {
                        enabled: false
                    },
                    toolbar: {
                        show: false
                    }
                },
                dataLabels: {
                    enabled: false
                },
                title: {
                    text: 'BTC Portfolio'
                }
            }

        });
    }


}
