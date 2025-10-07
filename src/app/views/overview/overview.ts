import {AfterViewInit, Component, inject, OnInit, ViewChild, ChangeDetectorRef } from '@angular/core';
import {OverviewService, PortfolioResponse} from "./overview-service";
import {
    ChartComponent,
    ApexAxisChartSeries,
    ApexChart,
    ApexXAxis,
    ApexTitleSubtitle,
    NgApexchartsModule
} from "ng-apexcharts";
import {AsyncPipe, NgClass} from "@angular/common";

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
        AsyncPipe,
        NgClass
    ],
    styleUrl: 'overview.scss',
    templateUrl: './overview.html',
})
export class OverviewComponent implements OnInit {
    private overviewService = inject(OverviewService);
    private changeDetectorRef = inject(ChangeDetectorRef);

    portfolioResponse$ = this.overviewService.getOverview('1', 'PLN');
    cryptoSummaryResponse$ = this.overviewService
        .getSummary('1', 'CRYPTO', 'PLN');
    stockSummaryResponse$ = this.overviewService
        .getSummary('1', 'STOCK', 'PLN');

    @ViewChild("cryptoChart") cryptoChart!: ChartComponent;
    @ViewChild("stockChart") stockChart!: ChartComponent;

    public cryptoChartOptions: Partial<ChartOptions> | any;
    public stockChartOptions: Partial<ChartOptions> | any;

    ngOnInit() {
        this.cryptoSummaryResponse$.subscribe(portfolioTimeline => {
            this.cryptoChartOptions = {
                series: [{
                        name: 'Crypto',
                        data: portfolioTimeline.portfolioTimelineItems.map(item => ({
                            x: new Date(item.date).getTime(),
                            y: item.currentValue.toFixed(2)
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
                    width: 1200,
                    type: 'area',
                    height: 300,
                    zoom: {
                        enabled: false
                    },
                    toolbar: {
                        show: false
                    }
                },
                tooltip: {
                    y: {
                        formatter: function(value: number) {
                            return value.toFixed(2) + " PLN"
                        }
                    }
                },
                stroke: {
                    show: true,
                    width: 2
                },
                dataLabels: {
                    enabled: false
                },
                title: {
                    text: 'Crypto Portfolio'
                }
            }

            this.changeDetectorRef.detectChanges();
        });

        this.stockSummaryResponse$.subscribe(portfolioTimeline => {
            this.stockChartOptions = {
                series: [{
                    name: 'Stock',
                    data: portfolioTimeline.portfolioTimelineItems.map(item => ({
                        x: new Date(item.date).getTime(),
                        y: item.currentValue.toFixed(2)
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
                    width: 1200,
                    type: 'area',
                    height: 300,
                    zoom: {
                        enabled: false
                    },
                    toolbar: {
                        show: false
                    }
                },
                tooltip: {
                    y: {
                        formatter: function(value: number) {
                            return value.toFixed(2) + " PLN"
                        }
                    }
                },
                stroke: {
                    show: true,
                    width: 2
                },
                dataLabels: {
                    enabled: false
                },
                title: {
                    text: 'Stock Portfolio'
                }
            }

            this.changeDetectorRef.detectChanges();
        });
    }

    getChangeClass(change: number) {
        if (change > 0) {
            return 'portfolio_change_value_green'
        }
        else if (change < 0) {
            return 'portfolio_change_value_red'
        }
        return '';
    }

    formatChange(change: number) {
        const prefix = change > 0 ? '+' : '';
        return `${prefix}${change.toFixed(2)}%`;
    }


}
