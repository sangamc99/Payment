import { HttpClient, HttpClientModule } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Order } from '../models/Order';

@Injectable({providedIn: 'root'})
export class OrderService {

    baseUrl = "http://localhost:8081/api/v1/order/create"

    constructor(private http : HttpClient) { }

    create(data : Order) : Observable<any> {
        return this.http.post(this.baseUrl , data);
    }
    
}