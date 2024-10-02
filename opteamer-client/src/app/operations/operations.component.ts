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

    console.log(this.operationTypes$)

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
    let operationType;
    this.operationTypes$.subscribe(data => {
      let id: any = this.operationsForm.value.operationType;
      operationType = data.find(obj => String(id) === String(obj.name));
    });
  
    let operationRoom;
    this.operationRooms$.subscribe(data => {
      let id: any = this.operationsForm.value.operationRoom;
      operationRoom = data.find(obj => String(id) === String(obj.id));
    });
  
    let patient;
    this.patients$.subscribe(data => {
      let id: any = this.operationsForm.value.patient;
      patient = data.find(obj => String(id) === String(obj.id));
    });
  
    let teamMembers: any[] = [];
    this.teamMembers$.subscribe(data => {
      let ids: any[] = this.operationsForm.value.teamMembers || []; // Prevent null issue
      teamMembers = data.filter(obj => ids.includes(obj.id));
    });
  
    let bodyObj = {
      operationTypeDTO: operationType,
      operationRoomDTO: operationRoom,
      patientDTO: patient,
      state: this.operationsForm.value.state,
      startDate: this.operationsForm.value.startDate,
      teamMembersDTO: teamMembers
    };
  
    console.log(bodyObj);
  
    if (this.editOperation) {
      this.operationService.putOperations(this.editOperation.id, bodyObj).subscribe({
        next: this.handlePutResponse.bind(this),
        error: this.handleError.bind(this)
      });
    } else {
      this.operationService.postOperations(bodyObj).subscribe({
        next: this.handlePutResponse.bind(this),
        error: this.handleError.bind(this)
      });
    }
  
    setTimeout(() => {
      this.reloadOperations();
    }, 500);
  }
  

  openModal(operation: any) {

    this.editOperation = operation;
    this.operationsForm.reset();

    let operationType = '';
    let operationRoom = '';
    let patient = '';
    let state = '';
    let startDate = '';
    let teamMembers = [];
    this.modalTitle = 'create';

    if (operation) {
      // Ensure operation contains all the necessary properties
      operationType = operation.operationTypeDTO?.name || '';
      operationRoom = operation.operationRoomDTO?.id || '';
      patient = operation.patientDTO?.id || '';
      state = operation.state || '';
      startDate = operation.startDate || '';
      teamMembers = operation.teamMemberDTO?.map((member: any) => member.id) || [];

      this.modalTitle = 'edit';

      // Patch the form with the retrieved values
      this.operationsForm.patchValue({
        'operationType': operationType,
        'operationRoom': operationRoom,
        'patient': patient,
        'state': state,
        'startDate': startDate,
        'teamMembers': teamMembers
      });
    }

    console.log(operation)
  }




  onDeleteOperations(id: string) {
    this.operationService.deleteOperations(id).subscribe({
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
