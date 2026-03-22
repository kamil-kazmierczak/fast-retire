import {Routes} from '@angular/router';
import {MainLayoutComponent} from './layouts/main-layout/main-layout';
import {OverviewComponent} from './views/overview/overview';

export const routes: Routes = [
    {
        path: '',
        component: MainLayoutComponent,
        children: [
            {path: '', component: OverviewComponent}
        ],
    },
];
