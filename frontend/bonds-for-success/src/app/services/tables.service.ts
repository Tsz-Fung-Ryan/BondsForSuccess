import { Injectable } from '@angular/core';
import { FileSystemFileEntry, NgxFileDropEntry } from 'ngx-file-drop';
import { DefaultService, Match } from '../libs/api/generated-code/api';
import { lastValueFrom } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class TablesService {
  private DEFAULT_FILE_NAME_OF_CSV_FILE_TO_DOWNLOAD = 'Mentee_Mentor_Matches.csv';

  constructor(
    private defaultService: DefaultService,
  ) {}

  async uploadFiles(menteeFile: NgxFileDropEntry, mentorFile: NgxFileDropEntry): Promise<Match[]> {
    const menteeFileSystemEntry = menteeFile.fileEntry;
    const mentorFileSystemEntry = mentorFile.fileEntry;

    if (menteeFileSystemEntry.isFile && mentorFileSystemEntry.isFile) {
      const menteeFileSystemFileEntry = menteeFileSystemEntry as FileSystemFileEntry;
      const mentorFileSystemFileEntry = mentorFileSystemEntry as FileSystemFileEntry;
      const [ menteeBlobFile, mentorBlobFile ] = await Promise.all([this.getFileFromFileSystemFileEntry(menteeFileSystemFileEntry), this.getFileFromFileSystemFileEntry(mentorFileSystemFileEntry)]);

      return lastValueFrom(this.defaultService.createTablePost(menteeBlobFile, mentorBlobFile));
    }
    throw new Error('One of the passed in dropped files is not a file');
  }

  async downloadMatchedDataAsCsvFile(matches: Match[]): Promise<void> {
    const matchesBlobFile = await lastValueFrom(this.defaultService.downloadFilePost(matches));

    const aTag = document.createElement("a");
    document.body.appendChild(aTag);
    aTag.setAttribute('style', 'display: none');
    const url = window.URL.createObjectURL(matchesBlobFile);
    aTag.href = url;
    aTag.download = this.DEFAULT_FILE_NAME_OF_CSV_FILE_TO_DOWNLOAD;
    aTag.click();
    window.URL.revokeObjectURL(url);
  }

  async getFileFromFileSystemFileEntry(fileSystemFileEntry: FileSystemFileEntry): Promise<File> {
    return new Promise((resolve, _reject) => {
      fileSystemFileEntry.file((file: File) => {
        resolve(file);
      });
    });
  }
}
