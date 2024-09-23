import { Component } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { OperationTypeService } from '../services/operationtype.service';
import { OperationProviderService } from '../services/operationproviders.service';
import { AssetService } from '../services/asset.service';
import { PreOpAssessmentService } from '../services/preopassessments.service';
import { combineLatest, Observable } from 'rxjs';

@Component({
  selector: 'app-operationtype',
  templateUrl: './operationtype.component.html',
  styleUrl: '../app.component.css'
})
export class OperationtypeComponent {


  operationTypeForm: FormGroup;
  editOperatioType: any;
  modalTitle: string;

  constructor(private operationTypeService: OperationTypeService, private operationProviderService: OperationProviderService,
    private assetService: AssetService, private preOpAssessmentService: PreOpAssessmentService) { }

  operationTypes$ = this.operationTypeService.data$;
  operationProviders$ = this.operationProviderService.data$;
  assets$ = this.assetService.data$;
  preOpAssisstments$ = this.preOpAssessmentService.data$;
  combined$: Observable<[any[],any[],any[]]>

  ngOnInit() {
    this.reloadOperationTypes();

    //init child objects
    this.operationProviderService.refreshData();
    this.assetService.refreshData();
    this.preOpAssessmentService.refreshData();

    //init form
    this.operationTypeForm = new FormGroup<any>({
      'name': new FormControl(null, [Validators.required]),
      'roomType': new FormControl(null, [Validators.required]),
      'durationHours': new FormControl(null, [Validators.required]),
      'assets': new FormControl(null, [Validators.required]),
      'operationProviders': new FormControl(null, [Validators.required]),
      'preOperatioveAssessments': new FormControl(null, [Validators.required])

    });

    this.combined$ = combineLatest([this.operationProviders$,this.assets$,this.preOpAssisstments$]);
  }

  reloadOperationTypes() {
    this.operationTypeService.refreshData();
  }

  openModal(operationType: any) {

  }

  onSubmit() {

  }

  onDeleteOperationType(id: string) {

  }

  
  handlePutResponse() { }
  handlePostResponse() { }
  handleDeleteResponse() { }

  handleError() { }


}
