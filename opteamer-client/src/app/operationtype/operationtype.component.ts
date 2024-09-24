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
    this.editOperatioType = operationType;

    let name = '';
    let roomType;
    let assets;
    let op;
    let prOpA;
    this.modalTitle = 'create';

    if (operationType) {
      name = operationType.name;
      roomType = operationType.roomType;
      this.modalTitle = 'edit';
    
       this.operationTypeForm.patchValue({
      'name': name,
      'roomType': operationType.roomType,
      'durationHours': operationType.durationHours,
      'assets': operationType.assets.map((obj:any) => obj.id),
      'operationProviders': operationType.operationProviders.map((obj:any) => obj.type),
      'preOperatioveAssessments': operationType.preopassessments.map((obj:any) => obj.name)
    })
    
    }
   
  }

  onSubmit() {

    let ops;
    this.operationProviders$.subscribe(data=>{
      let ids:any[] = this.operationTypeForm.value.operationProviders;
      ops = data.filter(obj => ids.includes(obj.type))
    });

    let assets;
    this.assets$.subscribe(data=>{
      let ids:any[] = this.operationTypeForm.value.assets;
      assets = data.filter(obj => ids.includes(obj.type))
    });

    let preOpAs;
    this.preOpAssisstments$.subscribe(data=>{
      let ids:any[] = this.operationTypeForm.value.preOperatioveAssessments;
      assets = data.filter(obj => ids.includes(obj.name))
    });
    
    let bodyObj = {
      name:this.operationTypeForm.value.name,
      roomType: this.operationTypeForm.value.roomType,
      durationHours: this.operationTypeForm.value.durationHours,
      assets: assets,
      operationProviders: ops,
      preOperativeAssessmets: preOpAs
    };
  
    if (this.editOperatioType) {
      this.operationTypeService.putOperationType(this.editOperatioType.name, bodyObj).subscribe({
        next: this.handlePutResponse.bind(this),
        error: this.handleError.bind(this)
      })
    } else {
      this.operationTypeService.postOperationType(bodyObj).subscribe({
        next: this.handlePutResponse.bind(this),
        error: this.handleError.bind(this)
      })
    }

    setTimeout(() => {
      this.reloadOperationTypes();
    }, 500);

  }

  onDeleteOperationType(id: string) {

    this.operationTypeService.deleteOperationType(id).subscribe({
      next: this.handleDeleteResponse.bind(this),
      error: this.handleError.bind(this)
    })

    setTimeout(() => {
      this.reloadOperationTypes();
    }, 500);
  }

  
  handlePutResponse() { }
  handlePostResponse() { }
  handleDeleteResponse() { }

  handleError() { }


}
