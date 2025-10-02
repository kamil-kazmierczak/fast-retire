import {Component, OnInit} from '@angular/core';
import {RouterLink, RouterOutlet} from '@angular/router';

@Component({
    selector: 'app-main-layout',
    imports: [RouterOutlet, RouterLink],
    templateUrl: './main-layout.html',
    styleUrl: './main-layout.css'
})
export class MainLayoutComponent implements OnInit {

    ngOnInit() {
    }
}
