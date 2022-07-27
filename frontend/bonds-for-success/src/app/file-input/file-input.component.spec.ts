import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { MatGridListModule } from '@angular/material/grid-list';
import { MatSnackBar } from '@angular/material/snack-bar';
import { MockComponent } from 'ng-mocks';
import { NgxFileDropComponent, NgxFileDropEntry } from 'ngx-file-drop';
import { TablesService } from '../services/tables.service';

import { FileInputComponent } from './file-input.component';

const matSnackBarStub = {
  open: jest.fn(),
};

const tablesServiceStub = {
  uploadFiles: jest.fn(),
};

describe('FileInputComponent', () => {
  let component: FileInputComponent;
  let fixture: ComponentFixture<FileInputComponent>;
  let snackBar: MatSnackBar;
  let httpTestingController: HttpTestingController;

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
      providers: [
        { provide: MatSnackBar, useValue: matSnackBarStub },
        { provide: TablesService, useValue: tablesServiceStub },
      ],
    }).compileComponents();

    snackBar = TestBed.inject(MatSnackBar);
    httpTestingController = TestBed.inject(HttpTestingController);
    fixture = TestBed.createComponent(FileInputComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  afterEach(() => {
    jest.clearAllMocks();
    httpTestingController.verify();
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

  describe('uploadMenteeMentorFiles()', () => {
    it('should call the `uploadFiles()` function of the TablesService', async () => {
      const expectedMenteeFile: NgxFileDropEntry = {
        relativePath: 'mockMenteeFileRelativePath',
        fileEntry: {} as FileSystemEntry,
      };
      const expectedMentorFile = {
        relativePath: 'mockMentorFileRelativePath',
        fileEntry: {} as FileSystemEntry,
      };

      component.menteeFile = expectedMenteeFile;
      component.mentorFile = expectedMentorFile;

      // eslint-disable-next-line @typescript-eslint/ban-ts-comment
      // @ts-ignore private access
      jest.spyOn(component.tablesService, 'uploadFiles');

      await component.uploadMenteeMentorFiles();

      // eslint-disable-next-line @typescript-eslint/ban-ts-comment
      // @ts-ignore private access
      expect(component.tablesService.uploadFiles).toHaveBeenCalledWith(expectedMenteeFile, expectedMentorFile);
    });

    it('should open a snackbar with an error message if the mentee/mentor files are not defined', async () => {
      const expectedErrorText = 'Please select a mentee and mentor file';
      const expectedSnackBarConfig = {
        duration: 4000,
        verticalPosition: 'top',
      };

      component.menteeFile = undefined;
      
      jest.spyOn(snackBar, 'open');

      await component.uploadMenteeMentorFiles();

      expect(snackBar.open).toHaveBeenCalledWith(expectedErrorText, undefined, expectedSnackBarConfig);
    });
  });
});
