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

  constructor( private operationReportService: OperationReportService ,private teamMemberService: TeamMemebrService,
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

    this.combined$ = combineLatest([ this.teamMembers$,this.operations$]);

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

//     let assets;
//     this.assets$.subscribe(data=>{
//       let ids:any[] = this.RoomInventoryForm.value.assets;
//       assets = data.filter(obj => ids.includes(obj.type))
//     });

//     let operationRoom;
//     this.operationRooms$.subscribe(data => {
//       let ids: any[] = this.RoomInventoryForm.value.operationRoom;
//       operationRoom = data.filter(obj => ids.includes(obj.type))
//     });


//     if (!assets || !operationRoom) {
//       console.error("Asset or Operation Room selection is invalid.");
//       return;
//   }

// let bodyObj = {
//   count: this.RoomInventoryForm.value.count,  
//   assetDTO: assets[0], 
//   operationRoomDTO: operationRoom[0], 
// };


//     console.log(bodyObj)

//     if (this.editRoomInventory) {
//       this.roomInventoryService.putRoomInventory(this.editRoomInventory.assetDTO.id,
//         this.editRoomInventory.operationRoomDTO.id, bodyObj)
//         .subscribe({
//           next: this.handlePutResponse.bind(this),
//           error: this.handleError.bind(this)
//         })
//     } else {
//       this.roomInventoryService.postRoomInventory(bodyObj).subscribe({
//         next: this.handlePostResponse.bind(this),
//         error: this.handleError.bind(this)
//       })
//     }

//     setTimeout(() => {
//       this.reloadRoomInventories();
//     }, 500);
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
