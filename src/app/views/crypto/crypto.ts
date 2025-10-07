import {ChangeDetectorRef, Component, inject, OnInit, ViewChild} from '@angular/core';
import {ChartComponent} from "ng-apexcharts";
import {CryptoService} from "./crypto-service";
import {ChartOptions} from "../overview/overview";

@Component({
    selector: 'app-crypto',
    imports: [
        ChartComponent
    ],
    templateUrl: './crypto.html',
})
export class CryptoComponent implements OnInit {
    private cryptoService = inject(CryptoService);
    private changeDetectorRef = inject(ChangeDetectorRef);

    @ViewChild("btcChart") btcChart!: ChartComponent;
    @ViewChild("ethChart") ethChart!: ChartComponent;
    @ViewChild("dotChart") dotChart!: ChartComponent;

    public btcChartOptions: Partial<ChartOptions> | any;
    public ethChartOptions: Partial<ChartOptions> | any;
    public dotChartOptions: Partial<ChartOptions> | any;

    btcTimelineResponse$ = this.cryptoService.getTimelineOverview('1', 'BTC', 'PLN');
    ethTimelineResponse$ = this.cryptoService.getTimelineOverview('1', 'ETH', 'PLN');
    dotTimelineResponse$ = this.cryptoService.getTimelineOverview('1', 'DOT', 'PLN');

    ngOnInit() {
        this.btcTimelineResponse$.subscribe(btcResponse => {
            const btcSeries = {
                name: 'BTC',
                data: btcResponse.portfolioTimelineItems.map(item => ({
                    x: new Date(item.date).getTime(),
                    y: item.currentValue
                }))
            };
            this.btcChartOptions = this.createLineChartOptions('BTC Portfolio', [btcSeries]);
            this.changeDetectorRef.detectChanges();
        });

        this.ethTimelineResponse$.subscribe(ethResponse => {
            const ethSeries = {
                name: 'ETH',
                data: ethResponse.portfolioTimelineItems.map(item => ({
                    x: new Date(item.date).getTime(),
                    y: item.currentValue
                }))
            };
            this.ethChartOptions = this.createLineChartOptions('ETH Portfolio', [ethSeries]);
            this.changeDetectorRef.detectChanges();
        });


        this.dotTimelineResponse$.subscribe(dotResponse => {
            const dotSeries = {
                name: 'DOT',
                data: dotResponse.portfolioTimelineItems.map(item => ({
                    x: new Date(item.date).getTime(),
                    y: item.currentValue
                }))
            };
            this.dotChartOptions = this.createLineChartOptions('DOT Portfolio', [dotSeries]);
            this.changeDetectorRef.detectChanges();
        });
    }

    private createLineChartOptions(title: string, series: any[]): any {
        return {
            series,
            xaxis: {
                type: 'datetime'
            },
            yaxis: {
                title: {text: 'PLN'},
                min: 0
            },
            chart: {
                width: 1600,
                type: 'area',
                height: 300,
                zoom: {enabled: false},
                toolbar: {show: false}
            },
            tooltip: {
                y: {
                    formatter: (value: string) => `${value} PLN`
                }
            },
            stroke: {show: true, width: 2},
            dataLabels: {enabled: false},
            title: {text: title}
        }
    }

}
