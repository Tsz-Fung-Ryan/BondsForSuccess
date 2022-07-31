import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { MatTableModule } from '@angular/material/table';

import { MenteeMentorTableComponent } from './mentee-mentor-table.component';

describe('FileInputComponent', () => {
  let component: MenteeMentorTableComponent;
  let fixture: ComponentFixture<MenteeMentorTableComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [
        MenteeMentorTableComponent,
      ],
      imports: [
        HttpClientTestingModule,
        MatTableModule,
      ],
    }).compileComponents();

    fixture = TestBed.createComponent(MenteeMentorTableComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
