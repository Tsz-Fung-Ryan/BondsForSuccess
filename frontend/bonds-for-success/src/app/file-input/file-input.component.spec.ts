import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { MatGridListModule } from '@angular/material/grid-list';
import { MockComponent } from 'ng-mocks';
import { NgxFileDropComponent, NgxFileDropEntry } from 'ngx-file-drop';

import { FileInputComponent } from './file-input.component';

describe('FileInputComponent', () => {
  let component: FileInputComponent;
  let fixture: ComponentFixture<FileInputComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [
        FileInputComponent,
        MockComponent(NgxFileDropComponent),
      ],
      imports: [
        MatGridListModule,
        HttpClientTestingModule,
      ],
    }).compileComponents();

    fixture = TestBed.createComponent(FileInputComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  describe('handleFileDropped()', () => {
    it('should set the menteeFile component property to the first passed in file if the fileToOverwrite parameter matches `menteeFile`', () => {
      const mockFile = {};
      const expectedOutput = {
        fileEntry: {
          isFile: true,
          file: jest.fn().mockImplementation((callback: FileCallback) => {
            callback(mockFile as File);
          }),
        } as unknown as FileSystemFileEntry,
      } as unknown as NgxFileDropEntry;
      const mockFiles = [
        expectedOutput,
      ];
      const mockFileToOverwrite = component.menteeFileIdentifier;

      component.handleFileDropped(mockFiles, mockFileToOverwrite);

      expect(component.menteeFile).toEqual(expectedOutput);
    });
  });
});
