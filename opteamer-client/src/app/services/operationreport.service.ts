import { HttpClient } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { BehaviorSubject, Observable, map } from "rxjs";


@Injectable({
    providedIn: 'root'
})
export class OperationReportService {

    private readonly serverUrl: string = 'http://localhost:8080'
    private dataSubject = new BehaviorSubject<any[]>([]);
    data$: Observable<any[]> = this.dataSubject.asObservable();

    constructor(private httpClient: HttpClient) { }

    loadAllOperationReports(): Observable<any> {
        return this.httpClient.get<any>(`${this.serverUrl}/api/operationReport`)
            .pipe(
                map(response => {
                    console.log(response)
                    this.dataSubject.next(response)
                    return response
                })
            )
    }

    refreshData() {
        this.loadAllOperationReports().subscribe();
    }

    postOperationReport(body: any): Observable<any> {
        console.log(body);
        return this.httpClient.post<any>(`${this.serverUrl}/api/operationReport`, body);

    }

    putOperationReport(teamMemberId: string, operationId: string, body: any): Observable<any> {
        return this.httpClient.put<any>(`${this.serverUrl}/api/operationReport/${teamMemberId}/${operationId}`, body);
    }

    deleteOperationReport(teamMemberId: string, operationId: string) {
        return this.httpClient.delete<any>(`${this.serverUrl}/api/operationReport/${teamMemberId}/${operationId}`)
    }

}