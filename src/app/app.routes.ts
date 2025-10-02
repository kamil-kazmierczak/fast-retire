import {Routes} from '@angular/router';
import {MainLayoutComponent} from './layouts/main-layout/main-layout';
import {OverviewComponent} from './views/overview/overview';
import {CashComponent} from './views/cash/cash';
import {CryptoComponent} from "./views/crypto/crypto";
import {StocksComponent} from "./views/stocks/stocks";
import {IkeComponent} from "./views/ike/ike";
import {BondsComponent} from "./views/bonds/bonds";
import {PriceList} from "./views/prices/price-list/price-list";
import {GoldComponent} from "./views/gold/gold";

export const routes: Routes = [
    {
        path: '',
        component: MainLayoutComponent,
        children: [
            {path: '', component: OverviewComponent},
            {path: 'prices', component: PriceList},
            {path: 'crypto', component: CryptoComponent},
            {path: 'stocks', component: StocksComponent},
            {path: 'bonds', component: BondsComponent},
            {path: 'ike', component: IkeComponent},
            {path: 'gold', component: GoldComponent},
            {path: 'cash', component: CashComponent}
        ],
    },
];
