import { HttpClient } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { BehaviorSubject, Observable, map } from "rxjs";


@Injectable({
    providedIn: 'root'
})
export class RoomInventoryService {

    private readonly serverUrl: string = 'http://localhost:8080'
    private dataSubject = new BehaviorSubject<any[]>([]);
    data$: Observable<any[]> = this.dataSubject.asObservable();

    constructor(private httpClient: HttpClient) { }

    loadAllRoomInventories(): Observable<any> {
        return this.httpClient.get<any>(`${this.serverUrl}/api/roomInventories`)
            .pipe(
                map(response => {
                    console.log(response)
                    this.dataSubject.next(response)
                    return response
                })
            )
    }

    refreshData() {
        this.loadAllRoomInventories().subscribe();
    }

    postRoomInventory(body: any): Observable<any> {
        console.log(body);
        return this.httpClient.post<any>(`${this.serverUrl}/api/roomInventories`, body);

    }

    putRoomInventory(assetId: string, roomId: string, body: any): Observable<any> {
        return this.httpClient.put<any>(`${this.serverUrl}/api/roomInventories/${assetId}/${roomId}`, body);
    }

    deleteRoomInventory(assetId: string, roomId: string) {
        return this.httpClient.delete<any>(`${this.serverUrl}/api/roomInventories/${assetId}/${roomId}`)
    }

}