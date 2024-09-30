import { Component, OnInit } from '@angular/core';
import { combineLatest, Observable } from 'rxjs';
import { OperationService } from '../services/operation.service';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { OperationTypeService } from '../services/operationtype.service';
import { OperationRoomService } from '../services/operationroom.service';
import { PatientService } from '../services/patient.service';
import { TeamMemebrService } from '../services/teammember.service';

@Component({
  selector: 'app-operations',
  templateUrl: './operations.component.html',
  styleUrl: '../app.component.css'
})
export class OperationsComponent implements OnInit {

  operationsForm: FormGroup;
  editOperation: any;
  modalTitle: string;


  constructor(private operationService: OperationService, private operationTypeService: OperationTypeService,
    private operationRoomService: OperationRoomService, private patientService: PatientService, private teamMemberService: TeamMemebrService) { }

  operations$ = this.operationService.data$;
  operationTypes$ = this.operationTypeService.data$;
  operationRooms$ = this.operationRoomService.data$;
  patients$ = this.patientService.data$;
  teamMembers$ = this.teamMemberService.data$;
  combined$: Observable<[any[], any[], any[], any[]]>

  ngOnInit(): void {
    this.reloadOperations();

    //init child objects
    this.operationTypeService.refreshData();
    this.operationRoomService.refreshData();
    this.patientService.refreshData();
    this.teamMemberService.refreshData();

    //init form
    this.operationsForm = new FormGroup<any>({
      'operationType': new FormControl(null, [Validators.required]),
      'operationRoom': new FormControl(null, [Validators.required]),
      'patient': new FormControl(null, [Validators.required]),
      'state': new FormControl(null, [Validators.required]),
      'startDate': new FormControl(null, [Validators.required]),
      'teamMembers': new FormControl(null, [Validators.required])

    });

    this.combined$ = combineLatest([this.operationTypes$, this.operationRooms$, this.patients$, this.teamMembers$]);
  }

  reloadOperations() {
    this.operations$ = this.operationService.loadAllOperations();
  }


  onSubmit() {

    // let ops;
    // this.operationProviders$.subscribe(data=>{
    //   let ids:any[] = this.operationTypeForm.value.operationProviders;
    //   ops = data.filter(obj => ids.includes(obj.type))
    // });

    // let assets;
    // this.assets$.subscribe(data=>{
    //   let ids:any[] = this.operationTypeForm.value.assets;
    //   assets = data.filter(obj => ids.includes(obj.type))
    // });

    // let preOpAs;
    // this.preOpAssisstments$.subscribe(data=>{
    //   let ids:any[] = this.operationTypeForm.value.preOperatioveAssessments;
    //   assets = data.filter(obj => ids.includes(obj.name))
    // });

    // let bodyObj = {
    //   name:this.operationTypeForm.value.name,
    //   roomType: this.operationTypeForm.value.roomType,
    //   durationHours: this.operationTypeForm.value.durationHours,
    //   assetsDTOS: assets,
    //   operationProvidersDTO: ops,
    //   preOperativeAssessmetsDTO: preOpAs
    // };

    // console.log(bodyObj)

    // if (this.editOperatioType) {
    //   this.operationTypeService.putOperationType(this.editOperatioType.name, bodyObj).subscribe({
    //     next: this.handlePutResponse.bind(this),
    //     error: this.handleError.bind(this)
    //   })
    // } else {
    //   this.operationTypeService.postOperationType(bodyObj).subscribe({
    //     next: this.handlePutResponse.bind(this),
    //     error: this.handleError.bind(this)
    //   })
    // }

    // setTimeout(() => {
    //   this.reloadOperationTypes();
    // }, 500);

  }

  openModal(operation: any) {
      this.editOperation = operation;

    //   let name = '';
    //   let roomType;
    //   let assets;
    //   let op;
    //   let prOpA;
    //   this.modalTitle = 'create';

    //   if (operationTypeDTO) {
    //     name = operationTypeDTO.name;
    //     roomType = operationTypeDTO.roomType;
    //     this.modalTitle = 'edit';

    //     const assetsDTOS = operationTypeDTO.assetsDTOS || [];
    //     const operationProvidersDTO = operationTypeDTO.operationProvidersDTO || [];
    //     const preOperatioveAssessmentsDTO = operationTypeDTO.preOperatioveAssessmentsDTO || [];

    //     this.operationTypeForm.patchValue({
    //       'name': name,
    //       'roomType': operationTypeDTO.roomType,
    //       'durationHours': operationTypeDTO.durationHours,
    //       'assets': assetsDTOS.map((obj:any) => obj.id),
    //       'operationProviders': operationProvidersDTO.map((obj:any) => obj.type),
    //       'preOperatioveAssessments': preOperatioveAssessmentsDTO.map((obj:any) => obj.name)
    //     });
    // }
  }




  onDeleteOperations(id: string) {

    this.operationTypeService.deleteOperationType(id).subscribe({
      next: this.handleDeleteResponse.bind(this),
      error: this.handleError.bind(this)
    })

    setTimeout(() => {
      this.reloadOperations();
    }, 500);
  }


  handlePutResponse() { }
  handlePostResponse() { }
  handleDeleteResponse() { }

  handleError() { }

}
