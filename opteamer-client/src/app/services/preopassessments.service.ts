import { HttpClient } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { BehaviorSubject, Observable , map} from "rxjs";


@Injectable({
    providedIn: 'root'
})
export class PreOpAssessmentService {

    private readonly serverUrl: string = 'http://localhost:8080' 
    private dataSubject = new BehaviorSubject<any[]>([]);
    data$: Observable<any[]> = this.dataSubject.asObservable();

    constructor(private httpClient: HttpClient){}

    loadAllPreOpAssessment(): Observable<any> {
        return this.httpClient.get<any>(`${this.serverUrl}/api/preOperativeAssessments`)
        .pipe(
            map( response => {
                const sortedData = response.sort( (a: { name: any; }, b: { name: any; }) => a.name - b.name);
                this.dataSubject.next(sortedData)
                return response
            })
        )
    }

    refreshData(){
        this.loadAllPreOpAssessment().subscribe();
    }

}