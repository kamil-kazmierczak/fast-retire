import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {environment} from "../../../environments/environment";

@Injectable({
  providedIn: 'root'
})
export class OverviewService {
    constructor(private http: HttpClient) {}

    getOverview(userId: string, currency: string): Observable<PortfolioResponse> {
        return this.http.get<PortfolioResponse>(
            `${environment.apiUrl}/portfolio/current/${userId}/${currency}`
        );
    }

    getSummary(userId: string, assetType: string, currency: string): Observable<PortfolioTimelineResponse> {
        return this.http.get<PortfolioTimelineResponse>(
            `${environment.apiUrl}/portfolio/summary/${assetType}/${userId}/${currency}`
        )
    }

}

export interface PortfolioResponse {
    portfolioCurrency: string;
    portfolioBalance: number;
    dayBeforePortfolioBalance: number;
    weekBeforePortfolioBalance: number;
    monthBeforePortfolioBalance: number;
    portfolioItems: PortfolioItem[];
}

export interface PortfolioItem {
    assetName: string;
    assetType: string;
    amount: number;
    currentValue: number;
    dailyPercentageChange: number;
    weeklyPercentageChange: number;
    monthlyPercentageChange: number;
    dailyValueChange: number;
    weeklyValueChange: number;
    monthlyValueChange: number;
    currency: string;
}

export interface PortfolioTimelineResponse {
    portfolioTimelineItems: PortfolioTimelineItem[];
}

export interface PortfolioTimelineItem {
    assetName: string;
    assetType: string;
    currentValue: number;
    currency: string;
    date: string;
}
