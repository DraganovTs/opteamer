import { HttpClient } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { BehaviorSubject, Observable , map} from "rxjs";


@Injectable({
    providedIn: 'root'
})
export class OperationProviderService {

    private readonly serverUrl: string = 'http://localhost:8080' 
    private dataSubject = new BehaviorSubject<any[]>([]);
    data$: Observable<any[]> = this.dataSubject.asObservable();

    constructor(private httpClient: HttpClient){}

    loadAllOperationProviders(): Observable<any> {
        return this.httpClient.get<any>(`${this.serverUrl}/api/operationProviders`)
        .pipe(
            map( response => {
                const sortedData = response.sort( (a: { id: number; },b: { id: number; }) => a.id - b.id);
                this.dataSubject.next(sortedData)
                return response
            })
        )
    }

    refreshData(){
        this.loadAllOperationProviders().subscribe();
    }

}