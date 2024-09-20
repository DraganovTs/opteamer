import { Component, OnInit } from '@angular/core';
import { Observable } from 'rxjs';
import { PatientService } from '../services/patient.service';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import {Modal} from 'bootstrap'

@Component({
  selector: 'app-patients',
  templateUrl: './patients.component.html',
  styleUrl: '../app.component.css'
})
export class PatientsComponent implements OnInit {


  patientForm: FormGroup;
  editPatient: any;
  modalTitle: string;


  constructor(private patientService: PatientService) { }

  patients$ = this.patientService.data$;

  ngOnInit(): void {
    this.reloadPatients();
    this.patientForm = new FormGroup<any>({
      'name': new FormControl(null, [Validators.required]),
      'nin': new FormControl(null, [Validators.required])
    })
  }

  reloadPatients() {
    this.patientService.refreshData();
  }

  openModal(patient: any) {

    this.editPatient = patient;

    let name = '';
    let nin = '';
    this.modalTitle = 'create';

    if (patient) {
      console.log(patient.id)
      name = patient.name;
      nin = patient.nin;
      this.modalTitle = 'edit';
    }
    this.patientForm.patchValue({
      'name': name,
      'nin': nin
    })
  }

  onSubmit(){

    if(this.editPatient){
      this.patientService.putPatient(this.editPatient.id,{name: this.patientForm.value.name, nin: this.patientForm.value.nin}).subscribe({
      next:  this.handlePutResponse.bind(this),
      error:  this.handleError.bind(this)
    })
    } else {
      this.patientService.postPatient({name: this.patientForm.value.name, nin: this.patientForm.value.nin}).subscribe({
        next:  this.handlePutResponse.bind(this),
        error:  this.handleError.bind(this)
      })
    }

    console.log('reload1')
    setTimeout(()=> {
      this.reloadPatients();
    }, 500);
    console.log('reload2')
  }

  onDelete(id: string){
    this.patientService.deletePatient(id).subscribe({
      next:  this.handleDeleteResponse.bind(this),
      error:  this.handleError.bind(this)
    });
    console.log('reload1')
    setTimeout(()=> {
      this.reloadPatients();
    }, 500);
    console.log('reload2')
  }


  handlePutResponse(){}
  handlePostResponse(){} 
   handleDeleteResponse(){}

  handleError(){}

}
