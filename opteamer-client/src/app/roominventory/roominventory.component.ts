import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { AssetService } from '../services/asset.service';
import { OperationRoomService } from '../services/operationroom.service';
import { RoomInventoryService } from '../services/roominventory.service';
import { combineLatest, Observable } from 'rxjs';

@Component({
  selector: 'app-roominventory',
  templateUrl: './roominventory.component.html',
  styleUrl: '../app.component.css'
})
export class RoominventoryComponent implements OnInit {

  RoomInventoryForm: FormGroup;
  editRoomInventory: any;
  modalTitle: string;
  combined$: Observable<[any[], any[]]>

  constructor(private roomInventoryService: RoomInventoryService, private assetService: AssetService,
    private operationRoomService: OperationRoomService) {
  }

  roomInventories$ = this.roomInventoryService.data$;
  assets$ = this.assetService.data$;
  operationRooms$ = this.operationRoomService.data$;

  ngOnInit(): void {
    this.reloadRoomInventories();
    this.assetService.refreshData();
    this.operationRoomService.refreshData();
    this.RoomInventoryForm = new FormGroup<any>({
      'assets': new FormControl(null, [Validators.required]),
      'operationRoom': new FormControl(null, [Validators.required]),
      'count': new FormControl(null, [Validators.required]),
    })

    this.combined$ = combineLatest([ this.operationRooms$,this.assets$]);

  }

  reloadRoomInventories() {
    this.roomInventoryService.refreshData();
  }

  openModal(roomInventory: any) {

    this.RoomInventoryForm.reset();

    this.modalTitle = 'create';

    if (roomInventory) {
      this.RoomInventoryForm.patchValue({
        assets: roomInventory.assetDTO.name,
        operationRoom: roomInventory.operationRoomDTO.type,
        count: roomInventory.count,
      });

      this.modalTitle = 'edit';
      this.editRoomInventory = roomInventory;
    }


  }

  onSubmit() {

    let assets;
    this.assets$.subscribe(data=>{
      let ids:any[] = this.RoomInventoryForm.value.assets;
      assets = data.filter(obj => ids.includes(obj.type))
    });

    let operationRoom;
    this.operationRooms$.subscribe(data => {
      let ids: any[] = this.RoomInventoryForm.value.operationRoom;
      operationRoom = data.filter(obj => ids.includes(obj.type))
    });

    let bodyObj = {
      roomInventoryId: this.RoomInventoryForm.value.roomInventory,
      operationRoomId: this.RoomInventoryForm.value.operationRoom,
      count: this.RoomInventoryForm.value.count
    }

    console.log(bodyObj)

    if (this.editRoomInventory) {
      this.roomInventoryService.putRoomInventory(this.editRoomInventory.assetDTO.id,
        this.editRoomInventory.operationRoomDTO.id, bodyObj)
        .subscribe({
          next: this.handlePutResponse.bind(this),
          error: this.handleError.bind(this)
        })
    } else {
      this.roomInventoryService.postRoomInventory(bodyObj).subscribe({
        next: this.handlePutResponse.bind(this),
        error: this.handleError.bind(this)
      })
    }

    setTimeout(() => {
      this.reloadRoomInventories();
    }, 500);
  }

  onDeleteRoomInventory(assetId: string, roomId: string) {
    this.roomInventoryService.deleteRoomInventory(assetId, roomId).subscribe({
      next: this.handleDeleteResponse.bind(this),
      error: this.handleError.bind(this)
    });
    setTimeout(() => {
      this.reloadRoomInventories();
    }, 500);
  }


  handlePutResponse() { }
  handlePostResponse() { }
  handleDeleteResponse() { }

  handleError() { }


}
