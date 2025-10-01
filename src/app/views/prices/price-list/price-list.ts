import { Component, inject } from '@angular/core';
import {PriceService} from '../price.service';
import {PriceItem} from './price-item/price-item';

@Component({
  selector: 'app-price-list',
  imports: [PriceItem],
  templateUrl: './price-list.html',
  styleUrl: './price-list.css'
})
export class PriceList {
    private priceService = inject(PriceService);

    prices = this.priceService.getPrices();


}
