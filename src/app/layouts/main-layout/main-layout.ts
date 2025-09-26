import { Component } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import { MenuModule } from 'primeng/menu';
import { MenuItem } from 'primeng/api';

@Component({
  selector: 'app-main-layout',
  imports: [RouterOutlet, MenuModule],
  templateUrl: './main-layout.html',
  styleUrl: './main-layout.css'
})
export class MainLayoutComponent {
  items: MenuItem[] = [
    { label: 'Home', icon: 'pi pi-home', routerLink: '/' },
    { label: 'Products', icon: 'pi pi-box', routerLink: '/products' },
  ];

}
