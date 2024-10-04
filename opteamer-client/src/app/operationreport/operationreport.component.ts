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

    this.OperationReportForm.reset();

    this.modalTitle = 'create';

    if (operationreport) {
      this.OperationReportForm.patchValue({
        teamMembers: operationreport.teamMemberDTO.name,
        operations: operationreport.operationDTO.type,
        report: operationreport.report,
      });

      this.modalTitle = 'edit';
      this.editOperationReport = operationreport;
    }


  }

  onSubmit() {

    let teamMembers: any[] = [];
    this.teamMembers$.subscribe(data => {
      let ids: any[] = this.OperationReportForm.value.teamMembers || [];
      teamMembers = data.filter(obj => ids.includes(obj.id));
    });

    let operations;
    this.operations$.subscribe(data => {
      let id: any = this.OperationReportForm.value.operation;
      operations = data.find(obj => String(id) === String(obj.id));
    });

    let bodyObj = {
      teamMemberId: this.OperationReportForm.value.teamMember,
      operationId: this.OperationReportForm.value.operation,
      teamMemberDTO: teamMembers,
      operationDTO: operations,
      report: this.OperationReportForm.value.report
    };


    console.log(bodyObj)

    if (this.editOperationReport) {
      this.operationReportService.putOperationReport(this.editOperationReport.teamMemberDTO.id,
        this.editOperationReport.operation.id, bodyObj)
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

  onDeleteOperationReport(assetId: string, roomId: string) {
    this.operationReportService.deleteOperationReport(assetId, roomId).subscribe({
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
