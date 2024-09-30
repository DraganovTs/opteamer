import { Component } from '@angular/core';
import { OperationRoomService } from '../services/operationroom.service';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { Observable } from 'rxjs';

@Component({
  selector: 'app-operationroom',
  templateUrl: './operationroom.component.html',
  styleUrl: '../app.component.css'
})
export class OperationroomComponent {

  OperationRoomForm: FormGroup;
  editOperationRoom: any;
  modalTitle: string;



  constructor(private operationRoomService: OperationRoomService) { }

  operationRooms$ = this.operationRoomService.data$;

  
  ngOnInit(): void {
    this.reloadOperationRooms();
    this.OperationRoomForm = new FormGroup<any>({
      'roomNr': new FormControl(null, [Validators.required]),
      'buildingBlock': new FormControl(null, [Validators.required]),
      'floor': new FormControl(null, [Validators.required]),
      'type': new FormControl(null, [Validators.required]),
      'state': new FormControl(null, [Validators.required])
    })
  }

  reloadOperationRooms() {
    this.operationRoomService.refreshData();
  }

  openModal(operationRoom: any) {

    this.editOperationRoom = operationRoom;

    let roomNr = '';
    let buildingBlock = '';
    let floor = '';
    let type = '';
    let state = '';
    this.modalTitle = 'create';

    if (operationRoom) {
      roomNr = operationRoom.roomNr;
      buildingBlock = operationRoom.buildingBlock;
      floor = operationRoom.floor;
      type = operationRoom.type;
      state = operationRoom.state;
      this.modalTitle = 'edit';
    }
    this.OperationRoomForm.patchValue({
      'roomNr': roomNr,
      'buildingBlock': buildingBlock,
      'floor': floor,
      'type': type,
      'state': state

    })
  }

  onSubmit() {
    let obj = {
      'roomNr': this.OperationRoomForm.value.roomNr,
      'buildingBlock': this.OperationRoomForm.value.buildingBlock,
      'floor': this.OperationRoomForm.value.floor,
      'type': this.OperationRoomForm.value.type,
      'state': this.OperationRoomForm.value.state
    }
    

    if (this.editOperationRoom) {
      this.operationRoomService.putOperationRoom(this.editOperationRoom.id, obj).subscribe({
        next: this.handlePutResponse.bind(this),
        error: this.handleError.bind(this)
      })
    } else {
      this.operationRoomService.postOperationRoom(obj).subscribe({
        next: this.handlePutResponse.bind(this),
        error: this.handleError.bind(this)
      })
    }

    setTimeout(() => {
      this.reloadOperationRooms();
    }, 500);
  }

  onDeleteOperationRoom(id: string) {
    this.operationRoomService.deleteOperationRoom(id).subscribe({
      next: this.handleDeleteResponse.bind(this),
      error: this.handleError.bind(this)
    });
    setTimeout(() => {
      this.reloadOperationRooms();
    }, 500);
  }


  handlePutResponse() { }
  handlePostResponse() { }
  handleDeleteResponse() { }

  handleError() { }


}
