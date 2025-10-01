import { Injectable } from '@angular/core';
import {Price} from './price.model';

@Injectable({
  providedIn: 'root'
})
export class PriceService {

    getPrices(): Price[] {
        return [
            {
                id: '1',
                name: 'BTC',
                value: 110000,
                currency: 'USD'
            },
            {
                id: '2',
                name: 'ETH',
                value: 4500,
                currency: 'USD'
            }
        ]
    }

}
