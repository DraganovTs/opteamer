import { Component } from '@angular/core';
import { Observable } from 'rxjs';
import { OperationService } from '../services/operation.service';

@Component({
  selector: 'app-operations',
  templateUrl: './operations.component.html',
  styleUrl: '../app.component.css'
})
export class OperationsComponent {

  operations$: Observable<any>;

  constructor( private operationService: OperationService){}

  ngOnInit(): void{
    this.reloadOperations();
  }

  reloadOperations(){
    this.operations$ = this.operationService.loadAllOperations();
  }

}
