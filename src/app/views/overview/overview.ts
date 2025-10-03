import {Component, inject} from '@angular/core';
import {CryptoComponent} from "../crypto/crypto";
import {StocksComponent} from "../stocks/stocks";
import {BondsComponent} from "../bonds/bonds";
import {IkeComponent} from "../ike/ike";
import {GoldComponent} from "../gold/gold";
import {CashComponent} from "../cash/cash";
import {OverviewService} from "./overview-service";

@Component({
  selector: 'app-overview',
    imports: [
        CryptoComponent,
        StocksComponent,
        BondsComponent,
        IkeComponent,
        GoldComponent,
        CashComponent,
    ],
  templateUrl: './overview.html',
})
export class OverviewComponent {
    private overviewService = inject(OverviewService);

    overview = this.overviewService.getOverview();

}
