import {Routes} from '@angular/router';
import {MainLayoutComponent} from './layouts/main-layout/main-layout';
import {HomeComponent} from './views/home/home';
import {ProductsComponent} from './views/products/products';
import {PriceList} from './views/prices/price-list/price-list';

export const routes: Routes = [
    {
        path: '',
        component: MainLayoutComponent,
        children: [
            {path: '', component: HomeComponent},
            {path: 'products', component: ProductsComponent},
            {path: 'prices', component: PriceList}
        ],
    },
];
