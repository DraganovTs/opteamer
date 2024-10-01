import { HttpClient } from "@angular/common/http";
import { Injectable, OnInit } from "@angular/core";
import { BehaviorSubject, Observable , map} from "rxjs";


@Injectable({
    providedIn: 'root'
})
export class OperationTypeService {

    private readonly serverUrl: string = 'http://localhost:8080' 
    private dataSubject = new BehaviorSubject<any[]>([]);
    data$: Observable<any[]> = this.dataSubject.asObservable();

    constructor(private httpClient: HttpClient){}

    loadAllOperationTypes(): Observable<any> {
        return this.httpClient.get<any>(`${this.serverUrl}/api/operationTypes`)
        .pipe(
            map( response => {
                const sortedData = response.sort( (a: { id: number; },b: { id: number; }) => a.id - b.id);
                this.dataSubject.next(sortedData)
                return response
            })
        )
    }

    refreshData(){
        this.loadAllOperationTypes().subscribe();
    }

    postOperationType(body: any): Observable<any> {
        console.log(body);
        return this.httpClient.post<any>(`${this.serverUrl}/api/operationTypes`,body);
       
    }

    putOperationType(id: string, body: any): Observable<any> {
        return this.httpClient.put<any>(`${this.serverUrl}/api/operationTypes/${id}`,body);
    }

    deleteOperationType(id: string){
        return this.httpClient.delete<any>(`${this.serverUrl}/api/operationTypes/${id}`)
    }

}