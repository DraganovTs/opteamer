import { HttpClient } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { Observable , map} from "rxjs";


@Injectable({
    providedIn: 'root'
})
export class OperationService {

    constructor( private httpClient: HttpClient){}


    loadAllOperations(): Observable<any> {
        return this.httpClient.get<any>('http://localhost:8080/api/operations')
        .pipe(
            map( response => {
                console.log(response);
            })
        )
    }

}