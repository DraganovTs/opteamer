import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { combineLatest, Observable } from 'rxjs';
import { TeamMemebrService } from '../services/teammember.service';
import { OperationService } from '../services/operation.service';
import { OperationReportService } from '../services/operationreport.service';

@Component({
  selector: 'app-operationreport',
  templateUrl: './operationreport.component.html',
  styleUrl: '../app.component.css'
})
export class OperationreportComponent implements OnInit {

  OperationReportForm: FormGroup;
  editOperationReport: any;
  modalTitle: string;
  combined$: Observable<[any[], any[]]>

  constructor(private operationReportService: OperationReportService, private teamMemberService: TeamMemebrService,
    private operationService: OperationService) {
  }

  operationReports$ = this.operationReportService.data$;
  teamMembers$ = this.teamMemberService.data$;
  operations$ = this.operationService.data$;

  ngOnInit(): void {
    this.reloadOperationReport();
    this.teamMemberService.refreshData();
    this.operationService.refreshData();
    this.OperationReportForm = new FormGroup<any>({
      'teamMembers': new FormControl(null, [Validators.required]),
      'operations': new FormControl(null, [Validators.required]),
      'report': new FormControl(null, [Validators.required]),
    })

    this.combined$ = combineLatest([this.teamMembers$, this.operations$]);

  }

  reloadOperationReport() {
    this.operationReportService.refreshData();
  }

  openModal(operationreport: any) {
   
    this.editOperationReport = operationreport;

    this.OperationReportForm.reset();

    this.OperationReportForm.controls['teamMembers'].enable();
    this.OperationReportForm.controls['operations'].enable();

    let teamMemberId = '';
    let operationId = '';
    let report = '';

    this.modalTitle = 'create';

    if (operationreport) {
      report = operationreport.report;

      teamMemberId = operationreport.teamMemberDTO.id;
      operationId = operationreport.operationDTO.id;

      console.log(teamMemberId);
      console.log(operationId);

      this.modalTitle = 'edit';

      this.OperationReportForm.controls['teamMembers'].disable();
      this.OperationReportForm.controls['operations'].disable();
  
    }

    this.OperationReportForm.patchValue({
      teamMembers: teamMemberId,
      operations: operationId,
      report: report,
    });

  }

  onSubmit() {
    
    this.OperationReportForm.controls['teamMembers'].enable();
    this.OperationReportForm.controls['operations'].enable();

   

    let bodyObj = {
      teamMemberId: this.OperationReportForm.value.teamMembers,
      operationId: this.OperationReportForm.value.operations,
      report: this.OperationReportForm.value.report
    };


    console.log(bodyObj)

    if (this.editOperationReport) {
      this.operationReportService.putOperationReport(this.OperationReportForm.value.teamMembers,
        this.OperationReportForm.value.operations, bodyObj)
        .subscribe({
          next: this.handlePutResponse.bind(this),
          error: this.handleError.bind(this)
        })
    } else {
      this.operationReportService.postOperationReport(bodyObj).subscribe({
        next: this.handlePostResponse.bind(this),
        error: this.handleError.bind(this)
      })
    }

    setTimeout(() => {
      this.reloadOperationReport();
    }, 500);
  }

  onDeleteOperationReport(teamMemberId: string, operationId: string) {
    this.operationReportService.deleteOperationReport(teamMemberId, operationId).subscribe({
      next: this.handleDeleteResponse.bind(this),
      error: this.handleError.bind(this)
    });
    setTimeout(() => {
      this.reloadOperationReport();
    }, 500);
  }


  handlePutResponse() { }
  handlePostResponse() { }
  handleDeleteResponse() { }

  handleError() { }


}
