import { Component } from '@angular/core';
import { Order } from './models/Order';
import { OrderService } from './services/order.service';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  title = 'frontend';

  constructor(private orderService : OrderService) {}


  onSubmit(f : any) {
    let order : Order = f.value;

    this.orderService.create(order).subscribe(response => {
      console.log(response);
    })
    

  }


}
