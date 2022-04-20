import { Component } from '@angular/core';
import { key } from 'src/assets/secrets/data';
import { Order } from './models/Order';
import { OrderService } from './services/order.service';
// import Razorpay from 'razorpay';

declare var Razorpay: any;

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  title = 'frontend';

  options = {
    "key": "",
    "amount" : "", 
    "name": "My Payment App",
    "description": "Test Transaction",
    "image": "assets/images/logo.png",
    "order_id":"",
    "handler": function (response: any){
        var event = new CustomEvent("payment.success", 
            {
                detail: response,
                bubbles: true,
                cancelable: true
            }
        );    
        window.dispatchEvent(event);
    }
    ,
    "prefill": {
    "name": "",
    "email": "",
    "contact": ""
    },
    "notes": {
    "address": ""
    },
    "theme": {
    "color": "#3399cc"
    }
    };
  error: string ='';

  constructor(private orderService : OrderService) {}


  onSubmit(f : any) {
    let order : Order = f.value;

    this.orderService.create(order).subscribe(response => {
      console.log(response);
      this.options.key = key;
      this.options.amount = response.price;
      console.log(response.price);
      this.options.prefill.name = response.name;

      var rzp = new Razorpay(this.options);
      rzp.open();

      rzp.on('payment.failed' , function(response: any) {
        console.log(response.error.code);
        console.log(response.error.description);
        console.log(response.error.source);
        console.log(response.error.step);
        console.log(response.error.reason);
        console.log(response.error.metadata.order_id);
        console.log(response.error.metadata.payment_id);
      });    
      },
        err => {
          this.error = err.error.message;
        }
      );
    }
    

  }

