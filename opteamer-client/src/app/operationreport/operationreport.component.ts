import { Component, OnInit } from '@angular/core';
import { FormGroup } from '@angular/forms';
import { Observable } from 'rxjs';
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
    // this.reloadRoomInventories();
    // this.assetService.refreshData();
    // this.operationRoomService.refreshData();
    // this.RoomInventoryForm = new FormGroup<any>({
    //   'assets': new FormControl(null, [Validators.required]),
    //   'operationRoom': new FormControl(null, [Validators.required]),
    //   'count': new FormControl(null, [Validators.required]),
    // })

    // this.combined$ = combineLatest([ this.operationRooms$,this.assets$]);

  }

  reloadRoomInventories() {
    this.operationReportService.refreshData();
  }

  openModal(roomInventory: any) {

    // this.RoomInventoryForm.reset();

    // this.modalTitle = 'create';

    // if (roomInventory) {
    //   this.RoomInventoryForm.patchValue({
    //     assets: roomInventory.assetDTO.name,
    //     operationRoom: roomInventory.operationRoomDTO.type,
    //     count: roomInventory.count,
    //   });

    //   this.modalTitle = 'edit';
    //   this.editRoomInventory = roomInventory;
    // }


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

  onDeleteRoomInventory(assetId: string, roomId: string) {
    // this.roomInventoryService.deleteRoomInventory(assetId, roomId).subscribe({
    //   next: this.handleDeleteResponse.bind(this),
    //   error: this.handleError.bind(this)
    // });
    // setTimeout(() => {
    //   this.reloadRoomInventories();
    // }, 500);
  }


  handlePutResponse() { }
  handlePostResponse() { }
  handleDeleteResponse() { }

  handleError() { }


}
