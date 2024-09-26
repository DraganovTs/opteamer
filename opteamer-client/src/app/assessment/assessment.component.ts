import { Component, OnInit } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';
import { OperationProviderService } from '../services/operationproviders.service';
import { TeamMemebrService } from '../services/teammember.service';
import { AssessmentService } from '../services/assessment.service';
import { PatientService } from '../services/patient.service';
import { PreOpAssessmentService } from '../services/preopassessments.service';
import { Observable, combineLatest } from 'rxjs';

@Component({
  selector: 'app-assessment',
  templateUrl: './assessment.component.html',
  styleUrl: '../app.component.css'
})
export class AssessmentComponent implements OnInit {


  AssessmentForm: FormGroup;
  editAssessment: any;
  modalTitle: string;

  constructor(private assessmentService: AssessmentService, private teamMemberService: TeamMemebrService,
    private preOpAssessmentService: PreOpAssessmentService, private patientService: PatientService) {
  }

  assessments$ = this.assessmentService.data$;
  teamMembers$ = this.teamMemberService.data$;
  preOpAssessments$ = this.preOpAssessmentService.data$;
  patients$ = this.patientService.data$;
  combined$: Observable<[any[], any[], any[]]>


  ngOnInit(): void {
    this.reloadAssessments();
    this.teamMemberService.refreshData();
    this.preOpAssessmentService.refreshData();
    this.patientService.refreshData();
    this.AssessmentForm = new FormGroup<any>({
      'teamMember': new FormControl(null, [Validators.required]),
      'patient': new FormControl(null, [Validators.required]),
      'preOperativeAssessments': new FormControl(null, [Validators.required]),
      'startDate': new FormControl(null, [Validators.required])
    })

    this.combined$ = combineLatest([this.preOpAssessments$, this.teamMembers$, this.patients$]);

  }

  reloadAssessments() {
    this.assessmentService.refreshData();
  }

  openModal(assessment: any) {

    this.AssessmentForm.reset();

    this.modalTitle = 'create';

    if (assessment) {
      this.AssessmentForm.patchValue({
        teamMember: assessment.teamMemberDTO.id,
        patient: assessment.patientDTO.id,
        preOperativeAssessments: assessment.preOperativeAssessmentDTO.name,
        startDate: assessment.startDate
      });

      this.modalTitle = 'edit';
      this.editAssessment = assessment;
    }


  }

  onSubmit() {

    let teamMember;
    this.teamMembers$.subscribe(data=>{
      let id:any[] = this.AssessmentForm.value.teamMember;
      teamMember = data.filter(obj => String(id) === String(obj.id))
    });

    let patient;
    this.patients$.subscribe(data=>{
      let id:any[] = this.AssessmentForm.value.patient;
      patient = data.filter(obj => String(id) === String(obj.id))
    });

    let preOpAs;
    this.preOpAssessments$.subscribe(data=>{
      let id:any[] = this.AssessmentForm.value.preOperativeAssessments;
      preOpAs = data.filter(obj => String(id) === String(obj.name))
    });

    


    let bodyObj = {
      teamMemberId: this.AssessmentForm.value.teamMember,
      preOpAssessmentId: this.AssessmentForm.value.preOperativeAssessments,
      patientId: this.AssessmentForm.value.patient,
      startDate: this.AssessmentForm.value.startDate 
  }

      console.log(bodyObj)

    if (this.editAssessment) {
      this.assessmentService.putAssessment(this.editAssessment.teamMemberDTO.id, 
        this.editAssessment.preOperativeAssessmentDTO.name, this.editAssessment.patientDTO.id, bodyObj)
        .subscribe({
          next: this.handlePutResponse.bind(this),
          error: this.handleError.bind(this)
        })
    } else {
      this.assessmentService.postAssessment(bodyObj).subscribe({
        next: this.handlePutResponse.bind(this),
        error: this.handleError.bind(this)
      })
    }

    setTimeout(() => {
      this.reloadAssessments();
    }, 500);
  }

  onDeleteAssessment(teamMemberId: string, preOpAssessmentId: string, patientId: string) {
    this.assessmentService.deleteAssessment(teamMemberId, preOpAssessmentId, patientId).subscribe({
      next: this.handleDeleteResponse.bind(this),
      error: this.handleError.bind(this)
    });
    setTimeout(() => {
      this.reloadAssessments();
    }, 500);
  }


  handlePutResponse() { }
  handlePostResponse() { }
  handleDeleteResponse() { }

  handleError() { }


}
