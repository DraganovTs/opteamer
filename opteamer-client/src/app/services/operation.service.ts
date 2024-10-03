import { HttpClient } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { BehaviorSubject, Observable , map} from "rxjs";


@Injectable({
    providedIn: 'root'
})
export class OperationService {

    constructor( private httpClient: HttpClient){}
    private readonly serverUrl: string = 'http://localhost:8080' 
    private dataSubject = new BehaviorSubject<any[]>([]);
    data$: Observable<any[]> = this.dataSubject.asObservable();


    loadAllOperations(): Observable<any> {
        return this.httpClient.get<any>(`${this.serverUrl}/api/operations`)
        .pipe(
            map( response => {
                const sortedData = response.sort( (a: { id: number; },b: { id: number; }) => a.id - b.id);
                this.dataSubject.next(sortedData)
                return response
            })
        )
    }

    refreshData() {
        this.loadAllOperations().subscribe();
    }

    postOperations(body: any): Observable<any> {
        console.log(body);
        return this.httpClient.post<any>(`${this.serverUrl}/api/operations`,body);
       
    }

    putOperations(id: string, body: any): Observable<any> {
        return this.httpClient.put<any>(`${this.serverUrl}/api/operations/${id}`,body);
    }

    deleteOperations(id: string){
        return this.httpClient.delete<any>(`${this.serverUrl}/api/operations/${id}`)
    }

}