import {Component, OnInit} from '@angular/core';
import {RouterOutlet} from '@angular/router';
import {PanelMenu} from 'primeng/panelmenu';
import {MenuItem} from 'primeng/api';

@Component({
    selector: 'app-main-layout',
    imports: [RouterOutlet, PanelMenu],
    templateUrl: './main-layout.html',
    styleUrl: './main-layout.css'
})
export class MainLayoutComponent implements OnInit {
    items: MenuItem[] = [];

    ngOnInit() {
        this.items = [
            {label: 'Home', icon: 'pi pi-home', routerLink: '/'},
            {label: 'Products', icon: 'pi pi-box', routerLink: '/products'},
            {label: 'Prices', icon: 'pi pi-box', routerLink: '/prices'},
        ];
    }

}
