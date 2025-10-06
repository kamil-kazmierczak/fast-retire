import {AfterViewInit, Component, inject, ViewChild} from '@angular/core';
import {CryptoComponent} from "../crypto/crypto";
import {StocksComponent} from "../stocks/stocks";
import {BondsComponent} from "../bonds/bonds";
import {IkeComponent} from "../ike/ike";
import {GoldComponent} from "../gold/gold";
import {CashComponent} from "../cash/cash";
import {OverviewService} from "./overview-service";
import {
    ChartComponent,
    ApexAxisChartSeries,
    ApexChart,
    ApexXAxis,
    ApexTitleSubtitle,
    NgApexchartsModule
} from "ng-apexcharts";

export type ChartOptions = {
    series: ApexAxisChartSeries;
    chart: ApexChart;
    xaxis: ApexXAxis;
    title: ApexTitleSubtitle;
}

@Component({
    selector: 'app-overview',
    imports: [
        NgApexchartsModule
    ],
    templateUrl: './overview.html',
})
export class OverviewComponent {

    @ViewChild(ChartComponent) chart!: ChartComponent;
    public chartOptions!: Partial<ChartOptions> | any;

    constructor() {
        this.chartOptions = {
            series: [{
                data: [{
                    x: 'Crypto',
                    y: 30
                }, {
                    x: 'Stocks',
                    y: 20
                }],
            }],
            chart: {
                width: 580,
                type: "pie"
            },
            labels: ["Crypto", "Stocks"],
            responsive: [
                {
                    breakpoint: 480,
                    options: {
                        chart: {
                            width: 200
                        },
                        legend: {
                            position: "bottom"
                        }
                    }
                }
            ]
        }
    }

    private overviewService = inject(OverviewService);

    overview = this.overviewService.getOverview();

}
