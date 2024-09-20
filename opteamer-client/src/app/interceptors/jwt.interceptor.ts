import { HttpEvent, HttpHandler, HttpInterceptor, HttpRequest } from "@angular/common/http";
import { Observable } from "rxjs";
import { AuthService } from "../services/authService";
import { Injectable } from "@angular/core";


@Injectable({
    providedIn: 'root'
})
export class JwtInterceptor implements HttpInterceptor {


    constructor(private authService: AuthService) { }

    intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
        const token = this.authService.getToken();
        console.log('Token in interceptor:', token); // Add logging
    
        const modifiedRequest = req.clone({
            setHeaders: {
                Authorization: `Bearer ${token}`
            }
        });
    
        return next.handle(modifiedRequest);
    }
    
}