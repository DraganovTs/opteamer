import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { TeamMemebrService } from '../services/teammember.service';
import { OperationProviderService } from '../services/operationproviders.service';

@Component({
  selector: 'app-teammember',
  templateUrl: './teammember.component.html',
  styleUrl: '../app.component.css'
})
export class TeammemberComponent implements OnInit {


  teamMemberForm: FormGroup;
  editTeamMember: any;
  modalTitle: string;

  constructor(private teamMemberService: TeamMemebrService, private operationProviderService: OperationProviderService) {
  }

  teamMembers$ = this.teamMemberService.data$;
  operationProviders$ = this.operationProviderService.data$;

  ngOnInit(): void {
    this.reloadTeamMembers();
    this.operationProviderService.refreshData();
    this.teamMemberForm = new FormGroup<any>({
      'name': new FormControl(null, [Validators.required]),
      'op': new FormControl(null, [Validators.required])
    })
  }

  reloadTeamMembers() {
    this.teamMemberService.refreshData();
  }

  openModal(teamMember: any) {

    this.editTeamMember = teamMember;

    let name = '';
    let opType;
    this.modalTitle = 'create';

    if (teamMember) {
      name = teamMember.name;
      opType = teamMember.operationProviderDTO.type;
      this.modalTitle = 'edit';
    }
    this.teamMemberForm.patchValue({
      'name': name,
      'op': opType
    })
  }

  onSubmit() {

    if (this.editTeamMember) {
      this.teamMemberService.putTeamMember(this.editTeamMember.id, { name: this.editTeamMember.value.name, operationProviderDTO: {type: this.teamMemberForm.value.op} }).subscribe({
        next: this.handlePutResponse.bind(this),
        error: this.handleError.bind(this)
      })
    } else {
      this.teamMemberService.postTeamMember({ name: this.teamMemberForm.value.name, operationProviderDTO: {type: this.teamMemberForm.value.op} }).subscribe({
        next: this.handlePutResponse.bind(this),
        error: this.handleError.bind(this)
      })
    }

    setTimeout(() => {
      this.reloadTeamMembers();
    }, 500);
  }

  onDelete(id: string) {
    this.teamMemberService.deleteTeamMember(id).subscribe({
      next: this.handleDeleteResponse.bind(this),
      error: this.handleError.bind(this)
    });
    setTimeout(() => {
      this.reloadTeamMembers();
    }, 500);
  }


  handlePutResponse() { }
  handlePostResponse() { }
  handleDeleteResponse() { }

  handleError() { }

}
