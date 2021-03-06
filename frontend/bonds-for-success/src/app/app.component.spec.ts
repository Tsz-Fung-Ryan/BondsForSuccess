import { TestBed } from '@angular/core/testing';
import { MatTabsModule } from '@angular/material/tabs';
import { RouterTestingModule } from '@angular/router/testing';
import { MockComponent, MockModule } from 'ng-mocks';
import { AppComponent } from './app.component';
import { FileInputComponent } from './file-input/file-input.component';
import { MenteeMentorTableComponent } from './mentee-mentor-table/mentee-mentor-table.component';

describe('AppComponent', () => {
  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [
        RouterTestingModule,
        MockModule(MatTabsModule),
      ],
      declarations: [
        AppComponent,
        MockComponent(FileInputComponent),
        MockComponent(MenteeMentorTableComponent),
      ],
    }).compileComponents();
  });

  it('should create the app', () => {
    const fixture = TestBed.createComponent(AppComponent);
    const app = fixture.componentInstance;
    expect(app).toBeTruthy();
  });
});
