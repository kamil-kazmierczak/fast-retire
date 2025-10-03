import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class OverviewService {
    getOverview(): OverviewResponse {
        return {
            userId: '1',
            date: '2025-05-05',
            crypto: {
                value: 20,
                currency: 'USD'
            },
            stocks: {
                value: 30,
                currency: 'PLN'
            },
            bonds: {
                value: 40,
                currency: 'PLN'
            },
            ike: {
                value: 40,
                currency: 'PLN'
            },
            gold: {
                value: 40,
                currency: 'PLN'
            },
            cash: {
                value: 40,
                currency: 'PLN'
            }
        }
    }

}

export interface AmountCcy {
    value: number;
    currency: string;
}

export interface OverviewResponse {
    userId: string;
    date: string;
    crypto: AmountCcy;
    stocks: AmountCcy;
    bonds: AmountCcy;
    ike: AmountCcy;
    gold: AmountCcy;
    cash: AmountCcy;
}
