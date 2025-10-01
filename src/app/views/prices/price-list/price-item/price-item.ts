import {Component, Input} from '@angular/core';
import {Price} from '../../price.model';

@Component({
  selector: 'app-price-item',
  imports: [],
  templateUrl: './price-item.html',
  styleUrl: './price-item.css'
})
export class PriceItem {
    @Input() price!: Price;
}
