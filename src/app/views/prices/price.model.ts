export type Currency = 'USD' | 'PLN' | 'EUR';

export interface Price {
    id: string;
    name: string;
    value: number;
    currency: Currency;
}
