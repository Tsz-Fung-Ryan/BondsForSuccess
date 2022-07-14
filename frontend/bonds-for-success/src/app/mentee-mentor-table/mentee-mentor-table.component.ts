import { CdkDragDrop, moveItemInArray } from '@angular/cdk/drag-drop';
import { Component, ViewChild } from '@angular/core';
import { MatTable } from '@angular/material/table';

interface Mentor {
  name: string;
  email: string;
}

interface Mentee {
  name: string;
  email: string;
}

interface MenteeMentorData {
  mentee: Mentee;
  mentor: Mentor;
}

const MENTEE_MENTOR_DATA: MenteeMentorData[] = [
  {
    mentor: {
      name: 'mentor1',
      email: 'mentor1@mail.com',
    },
    mentee: {
      name: 'mentee1',
      email: 'mentee1@mail.com',
    },
  },
  {
    mentor: {
      name: 'mentor2',
      email: 'mentor2@mail.com',
    },
    mentee: {
      name: 'mentee2',
      email: 'mentee2@mail.com',
    },
  },
  {
    mentor: {
      name: 'mentor3',
      email: 'mentor3@mail.com',
    },
    mentee: {
      name: 'mentee3',
      email: 'mentee3@mail.com',
    },
  },
  {
    mentor: {
      name: 'mentor4',
      email: 'mentor4@mail.com',
    },
    mentee: {
      name: 'mentee4',
      email: 'mentee4@mail.com',
    },
  }
];

@Component({
  selector: 'mentee-mentor-table',
  templateUrl: './mentee-mentor-table.component.html',
  styleUrls: ['./mentee-mentor-table.component.scss']
})
export class MenteeMentorTableComponent {
  @ViewChild('menteeMentorTable') menteeMentorTable: MatTable<MenteeMentorData> | undefined;

  public displayedColumns = ['menteeName', 'menteeEmail', 'mentorName', 'mentorEmail'];
  public menteeMentorData = MENTEE_MENTOR_DATA;

  drop(event: CdkDragDrop<string[]>) {
    moveItemInArray(this.menteeMentorData, event.previousIndex, event.currentIndex);
    this.menteeMentorTable?.renderRows();
  }
}
