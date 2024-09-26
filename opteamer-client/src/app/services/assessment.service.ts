import { HttpClient } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { BehaviorSubject, Observable, map } from "rxjs";


@Injectable({
    providedIn: 'root'
})
export class AssessmentService {

    private readonly serverUrl: string = 'http://localhost:8080'
    private dataSubject = new BehaviorSubject<any[]>([]);
    data$: Observable<any[]> = this.dataSubject.asObservable();

    constructor(private httpClient: HttpClient) { }

    loadAllAssessments(): Observable<any> {
        return this.httpClient.get<any>(`${this.serverUrl}/api/assessments`)
            .pipe(
                map(response => {
                    this.dataSubject.next(response)
                    return response
                })
            )
    }

    refreshData() {
        this.loadAllAssessments().subscribe();
    }

    postAssessment(body: any): Observable<any> {
        console.log(body);
        return this.httpClient.post<any>(`${this.serverUrl}/api/assessments`, body);

    }

    putAssessment(teamMemberId: string, preOpAName: string, patientId: string, body: any): Observable<any> {
        return this.httpClient.put<any>(`${this.serverUrl}/api/assessments/${teamMemberId}/${patientId}/${preOpAName}`, body);
    }

    deleteAssessment(teamMemberId: string, patientId: string, preOpAName: string,) {
        return this.httpClient.delete<any>(`${this.serverUrl}/api/assessments/${teamMemberId}/${patientId}/${preOpAName}`)
    }

}