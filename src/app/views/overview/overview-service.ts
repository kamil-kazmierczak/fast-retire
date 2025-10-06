import { Injectable } from '@angular/core';
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

    getTimelineOverview(userId: string, assetName: string, currency: string): Observable<PortfolioTimelineResponse> {
        return this.http.get<PortfolioTimelineResponse>(
            `${environment.apiUrl}/portfolio/timeline/${assetName}/${userId}/${currency}`
        )
    }

}

export interface PortfolioResponse {
    portfolioItems: PortfolioItem[];
}

export interface PortfolioItem {
    assetName: string;
    assetType: string;
    amount: number;
    value: number;
    currency: string;
}

export interface PortfolioTimelineResponse {
    portfolioTimelineItems: PortfolioTimelineItem[];
}

export interface PortfolioTimelineItem {
    assetName: string;
    assetType: string;
    value: number;
    currency: string;
    date: string;
}
